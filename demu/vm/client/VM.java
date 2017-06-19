package vm.client;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.RandomAccessFile;
import java.io.IOException;

import vm.client.view.VMView;
import vm.client.view.ColorMap;
import vm.client.register.GDTR;
import vm.client.register.IDTR;
import vm.client.register.EFlags;
import vm.client.register.FPUDataRegister;
import vm.client.register.FPUControlRegister;
import vm.client.register.FPUStatusRegister;

import vm.client.register.RegisterIndex;
import vm.client.gdt.GDT;
import vm.client.idt.IDT;
import vm.client.idt.InterruptDescriptor;
import vm.client.pic.PIC;

import vm.client.instruction.Instruction;
import vm.client.instruction.InstructionMap;
import vm.client.instruction.NotImplementException;

import vm.client.device.FDC;
import vm.client.device.HDD;
import vm.client.device.Serial;

import vm.client.modrm.ModRM;
import vm.client.util.Paging;
import static vm.client.register.RegisterIndex.*;

import vm.client.exception.PageFaultException;

public class VM{
    private long[] registers;
    
    private byte[] memory;
    
    private int cr0;
    
    private int cr2;
    
    private int cr3;
    
    private int cr4;

    private boolean isProtect;
    
    private boolean is32Flag;
    
    private boolean isOperandPrefix;
    
    private boolean isAddressPrefix;
    
    private boolean usePaging;
    
    private int sindex;
    
    private VMView view;
    
    private EFlags eflags;
    
    private GDT gdt;
    
    private GDTR gdtr;
    
    private IDT idt;
    
    private IDTR idtr;
    
    private int intIndex;
    
    private int keycode;
    
    private int inputKey;
    
    private int tr;
    
    private PIC pic;
    
    private FDC fdc;

    private HDD hdd;
    
    private Serial serial;
    
    private FPUDataRegister fpudr;
    
    private FPUControlRegister fpucr;
    
    private FPUStatusRegister fpusr;

    private double[] st;
    
    private InstructionMap instMap;
    
    private boolean isDebug;
    
    private RandomAccessFile disk;
    private RandomAccessFile disk2 = null;
    
    private int disk2Offset;
    private int disk2Count;
    private int disk2Index;
    
    private int cursorXY;
    private int cursorIndex = 0;
    
    private List<Long> callStack;
    
    private int ldtr;
    
    public VM(int memorySize, VMView view){
        registers = new long[RegisterIndex.SIZE];
        memory = new byte[memorySize];
        this.view = view;
        view.setVM(this);
        eflags = new EFlags();
        gdt = new GDT();
        gdtr = new GDTR();
        idt = new IDT();
        idtr = new IDTR();
        ldtr = 0;

        instMap = new InstructionMap();
        instMap.init();
        
        pic = new PIC();
        fdc = new FDC();
        hdd = new HDD();
        serial = new Serial();

        fpucr = new FPUControlRegister();
        fpudr = new FPUDataRegister();
        fpusr = new FPUStatusRegister();
        st = new double[8];
        
        eflags.set(0x02);
        cr0 = 0x10;
        is32Flag = false;
        isOperandPrefix = false;
        isAddressPrefix = false;
        usePaging = false;
        sindex = DS;
        intIndex = -1;
        
        set8(0x484, 0x18);
        
        callStack = new ArrayList<Long>();
    }
    
    public void setDebug(boolean isDebug){
        this.isDebug = isDebug;
    }
    
    public boolean isDebug(){
        return isDebug;
    }

    public boolean flag = false;
    
    public boolean getFlag(){
        return flag;
    }

    public void execute() throws NotImplementException{
        long eip = registers[EIP];
        
        try{
            int codeAddress = getCodeAddress(0);
            int code = getCode8(0) & 0xFF;

            Instruction instruction = getInstruction(code);
            instruction.execute(this);
            
            if(flag){
                System.out.printf("%x %x %x %s\n", eip, codeAddress, code, instruction.toString());
            }
            
            if(isDebug){
                System.out.printf("%x %s %x\n", eip, instruction.toString(), codeAddress);
                System.out.printf("ECX = %x\n", getRegister32(ECX));
                System.out.printf("EDX = %x\n", getRegister32(EDX));
            }
            
            if(intIndex > -1 && eflags.isInterrupt()){
                interrupt();
            }
        }catch(PageFaultException e){
            throw new RuntimeException(e);
//            registers[EIP] = eip;
//            interrupt();
        }
    }
    
    public int getEIP(){
        return (int)registers[EIP];
    }
    
    public Instruction getInstruction(int code){
        Instruction instruction = instMap.get(code);
        
        if(instruction == null){
            throw new RuntimeException("Not Implemented " + Integer.toHexString(code));
        }
        
        return instruction;
    }
    
    public void load(String fileName, int mode) throws IOException{
        disk = new RandomAccessFile(fileName, "r");
        
        if(mode == 0){
            disk.read(memory, 0x7C00, 0x200);
        }else if(mode == 1){
           disk.seek(2048 * 17 + 0x47);
           disk.read(memory, 0, 4);
            
           int catalog = (int)getCode32(0);

           disk.seek(catalog * 2048 + 0x20);
           disk.read(memory, 0, 2048);
            
           int sector = getCode16(6);
           int boot = (int)getCode32(8);
            
            disk.seek(boot * 2048);
            disk.read(memory, 0x7C00, 2048 * sector);
        }
        
        setRegister32(EIP, 0x7C00);
    }
    
    public void read(int diskStart, int memoryStart, int size) throws IOException{
        disk.seek(diskStart);
        disk.read(memory, memoryStart, size);
    }
    
    public void readDisk2(int diskStart, int memoryStart, int size) throws IOException{
        if(disk2 == null){
            disk2 = new RandomAccessFile("disk2", "rw");
        }
        
        disk2.seek(diskStart);
        disk2.read(memory, memoryStart, size);
    }

    public void setDisk2Offset(int diskStart, int size){
        disk2Offset = diskStart;
        disk2Count = size;
        disk2Index = 0;
    }
    
    public void writeDisk2(int data, int size) throws IOException{
        for(int i = 0; i < size; i++){
            if(disk2Index < disk2Count){
                memory[disk2Index] = (byte)((data >> (i * 8)) & 0xFF);
                disk2Index++;
            }
        }
        
        if(disk2Index == disk2Count){
            writeDisk2(disk2Offset, 0, disk2Count);
        }
    }
    
    public void writeDisk2(int diskStart, int memoryStart, int size) throws IOException{
        if(disk2 == null){
            disk2 = new RandomAccessFile("disk2", "rw");
        }
        
        disk2.seek(diskStart);
        disk2.write(memory, memoryStart, size);
    }
    
    public void ltr(int tr){
        this.tr = tr;
    }

    public void setSegmentIndex(int sindex){
        this.sindex = sindex;
    }

    public void setSegmentDataX(int selector, int register, int data){
        long address = (long)getSegmentAddress(selector) + getRegisterX(register);
        
        if(usePaging){
            address = Paging.getAddress(this, (int)address);
        }
        
        setX((int)address, data);
    }
    
    public int getSegmentIndex(){
        return sindex;
    }
    
    public int getSegmentAddress(int selector){
        if(isProtectMode(selector)){
            int gindex = getRegister16(selector) >> 3;
            return getGDT().get(gindex).getBase();
        }else{
            return getRegister16(selector) * 16;
        }
    }
    
    public int getSegmentAddress(int selector, int register){
        long rvalue = is32bitAddress() ? getRegister32(register) : getRegister16(register);
        
        long address = (long)getSegmentAddress(selector) + rvalue;
        
        if(usePaging){
            address = Paging.getAddress(this, (int)address);
        }

        return (int)address;
    }
    
    public int getCodeAddress(int index){
        int address = getSegmentAddress(CS) + getEIP() + index;
        
        if(usePaging){
            address = Paging.getAddress(this, address);
        }

        return address;
    }
    
    public int getSignedCode8(int index){
        int address = getCodeAddress(index);

        return memory[address];
    }
    
    public int getCode8(int index){
        int address = getCodeAddress(index);

        return memory[address];
    }
    
    public int getSignedCode16(int index){
        int address = getCodeAddress(index);

        int data = memory[address] & 0xFF;
        data |= (memory[address + 1] & 0xFF) << 8;
        
        return data;
    }
    
    public int getCode16(int index){
        int address = getCodeAddress(index);

        int data = memory[address] & 0xFF;
        data |= memory[address + 1] << 8;

        return data & 0xFFFF;
    }
    
    public int getSignedCode32(int index){
        int address = getCodeAddress(index);

        int data = memory[address] & 0xFF;
        data |= (memory[address + 1] & 0xFF) << 8;
        data |= (memory[address + 2] & 0xFF) << 16;
        data |= (memory[address + 3] & 0xFF) << 24;

        return data;
    }

    public long getCode32(int index){
        int address = getCodeAddress(index);

        int data = memory[address] & 0xFF;
        data |= (memory[address + 1] & 0xFF) << 8;
        data |= (memory[address + 2] & 0xFF) << 16;
        data |= (memory[address + 3] & 0xFF) << 24;

        return data;
    }
    
    public int getSignedCodeX(int index){
        if(is32bitOperand()){
            return getSignedCode32(index);
        }else{
            return getSignedCode16(index);
        }
    }
    
    public long getCodeX(int index){
        if(is32bitOperand()){
            return getSignedCode32(index);
        }else{
            return getSignedCode16(index);
        }
    }

    public ModRM getModRM(){
        return new ModRM(getCode8(1), this, true);
    }
    
    public ModRM getModRM(boolean addeip){
        return new ModRM(getCode8(1), this, addeip);
    }

    public int getDataAddress(int address){
        if((cr0 & 0x01) == 0x01 && getRegister16(sindex) > 0 && getRegister16(sindex) < 0x48){
            int base = getGDT().get((int)registers[sindex] >> 3).getBase();
            address = base + address;
        }else{
            address = (int)registers[sindex] * 16 + address;
        }
        

        if(usePaging){
            address = Paging.getAddress(this, address);
        }
        
        return address;
    }
    
    public int get8(int address){
        return (memory[address] & 0xFF);
    }
    
    public int getS8(int address){
        return memory[address];
    }
    
    public int get16(int address){
        int data = get8(address);
        data |= get8(address + 1) << 8;

        return data;
    }
    
    public int getS16(int address){
        int data = get8(address);
        data |= memory[address + 1] << 8;

        return data;
    }
    
    public int get32(int address){
        if(address >= memory.length){
            return -1;
        }
        
        int data = get8(address);
        data |= get8(address + 1) << 8;
        data |= get8(address + 2) << 16;
        data |= get8(address + 3) << 24;

        return data;
    }
    
    public int getS32(int address){
        if(address >= memory.length){
            return -1;
        }
        
        int data = get8(address);
        data |= get8(address + 1) << 8;
        data |= get8(address + 2) << 16;
        data |= getS8(address + 3) << 24;

        return data;
    }
    
    public long getu32(int address){
        long data = memory[address] & 0xFF;
        data |= (memory[address + 1] & 0xFF) << 8;
        data |= (memory[address + 2] & 0xFF) << 16;
        data |= (long)(memory[address + 3] & 0xFF) << 24;

        return data;
    }
    
    public int getX(int address){
        if(is32bitOperand()){
            return get32(address);
        }else{
            return get16(address);
        }
    }
    
    public int getSX(int address){
        if(is32bitOperand()){
            return get32(address);
        }else{
            return getS16(address);
        }
    }
    
    public long getDataX(int address){
        if(is32bitAddress()){
            return getData32(address);
        }else{
            return getData16(address);
        }
    }
    
    public int getData8(int address){
        if(is32bitAddress()){
            address = getDataAddress(address);
        }else{
            address = getDataAddress(address & 0xFFFF);
        }
        
        return get8(address);
    }

    public long getData16(int address){
        if(is32bitAddress()){
            address = getDataAddress(address);
        }else{
            address = getDataAddress(address & 0xFFFF) & 0xFFFFF;
        }
        
        if(address == 0x14000){
            System.out.printf("address = %x, %x", address, getX(address));
        }

        return getX(address);
    }
    
    public long getData32(int address){
        address = getDataAddress(address);
/*
        if(address >= 0xa000000 && address <= 0xb000000){
            VMView view = getView();
            return view.getRGB(address - 0xa000000);
        }
*/
        if(address >= memory.length){
            return -1;
        }

        return getu32(address);
    }
    
    int count40 = 1;
    int rtc = 0x04;
    
    public int in(int port){
        if(port == 0){
            return 0;
        }else if(port == 0x20){
            return 0;
        }else if(port == 0x21){
            return pic.getPIC0Mask();
        }else if(port == 0x40){
            count40 = (count40 + 1) % 2;
            
            if(count40 == 0){
                return (count & 0xFF);
            }else{
                return ((count >> 8) & 0xFF);
            }
        }else if(port == 0x53){
            return keycode;
        }else if(port == 0x60){
            //System.out.printf("%x\n", keycode);
            
            return keycode;
        }else if(port == 0x64){
            if(mouseState > -1){
                return mouseState;
            }
            
            return 010;
        }else if(port == 0x70){
            return 0;
        }else if(port == 0x71){
            return rtc;
        }else if(port == 0x80){
            return 0;
        }else if(port == 0x92){
            return 0;
        }else if(port == 0xA0){
            return 0;
        }else if(port == 0xA1){
            return pic.getPIC1Mask();
        }else if(0x1f0 <= port && port <= 0x1f7){
            return hdd.in(this, port);
        }else if(port == 0x3cf){
            return 0;
        }else if(port == 0x3d4 || port == 0x3d5){
            return cursorXY;
        }else if(0x3F0 <= port && port <= 0x3F5){
            return fdc.in(this, port);
        }else if(0x3F8 <= port && port <= 0x3FF){
            return serial.in(this, port);
        }else if(port == 0x4D0){
            return 0x10;
        }else if(port == 0x4D1){
            //throw new RuntimeException("Port = " + Integer.toHexString(port));
            return 0x18;
        }else if(port == 0xCF8){
            return 0;
        }else{
            throw new RuntimeException("Port = " + Integer.toHexString(port));
        }
    }
    
    boolean bwrite = false;
    int color1;
    int color2;
    
    int mouseState = -1;
    
    int cursor = 0;
    
    int rtcAddr;
    
    public void out(int port, int data){
        if(port >= 0x00 && port <= 0x0F){
            fdc.out(this, port, data);
        }else if(port == 0x20 || port == 0xA0){

        }else if(port == 0x21){
            pic.setPIC0Mask(data);
        }else if(port == 0xA1){
            pic.setPIC1Mask(data);
        }else if(port == 0x40 || port == 0x43){
            
        }else if(port == 0x60){
            mouseState = 0x01;
        }else if(port == 0x64){
            if(!(data == 0x20 || data == 0x60 || data == 0xA8 || data == 0xD1 || data == 0xD4 || data == 0xFF)){
                throw new RuntimeException("PORT = 0x64 Not Implemented " + Integer.toHexString(data));
            }
            
            if(data == 0x20){
                mouseState = 0x01;
            }else{
                mouseState = 0x00;
            }
        }else if(port == 0x70){
        }else if(port == 0x71){
            rtc = 0;
        }else if(port == 0x80){
            //view.setCursor(0, 0);
        }else if(port == 0x81){
            fdc.out(this, port,  data);
        }else if(port == 0x92){
            
        }else if(port == 0xD0 || port == 0xD4 || port == 0xD6 || port == 0xDA){
        }else if((0x1f0 <= port && port <= 0x1f7) || port == 0x3F6){
            hdd.out(this, port, data);
        }else if(port == 0xF0 || port == 0xF1){
        }else if(port == 0x3c4){
            color2 = (data >> 8) & 0xFF;
        }else if(port == 0x3c5){
//            System.out.printf("Data = %x\n", data);
        }else if(port == 0x3CE && (data == 0x08 || data == 0xFF08)){
            bwrite = false;
        }else if(port == 0x3CE){
            color1 = (data >> 8) & 0xFF;
            
            bwrite = true;
        }else if(port == 0x3d4){
            if(data == 14){
                cursorXY = cursor >> 8;
                cursorIndex = 0;
            }else if(data == 15){
                cursorXY = cursor & 0xFF;
                cursorIndex = 1;
            }
        }else if(port == 0x3d5){
            if(cursorIndex == 0){
                int temp = cursor & 0xFF;
                cursor = ((data << 8) + temp);
            }else if(cursorIndex == 1){
                int temp = cursor >> 8;
                cursor = ((temp << 8) + data);
            }
        }else if((port >= 0x3F0 && port <= 0x3F5) || port == 0x3F7){
            fdc.out(this, port, data);
        }else if(port >= 0x3F8 && port <= 0x3FF){
            serial.out(this, port, data);
        }else if(port == 0x4D0 || port == 0x4D1){
        }else if(port == 0xCf8 || port == 0xCFB){
            
        }else{
            throw new RuntimeException("Port = " + Integer.toHexString(port) + ", Data = " + Integer.toHexString(data));
        }
    }

    int scount = 0;
    
    public void set8(int address, int data){
        data &= 0xFF;
        
        if(isProtectMode(CS) && address >= 0xa0000 && address <= 0xb0000){
            VMView view = getView();
            int position = (address - 0xa0000) * 8;
            int x = (position % view.getWidth());
            int y = (position / view.getWidth());

            if(bwrite){
                int color = 0;

                if(data != 0xFF){
                    color = ColorMap.getColor(data);
                }else{
                    color = ColorMap.getColor(color2);
                }

                for(int i = 0; i < 8; i++){
                    if((color1 & (0x80 >> i)) > 0){
                        view.setRGB(x + i, y, color);
                    }
                }
            }
        }
        
        if(0xB8000 <= address && address <= 0xB8FFF){
            int index = (address - 0xB8000) / 2;
            int x = index % 80;
            int y = index / 80;

            if((address % 2) == 0){
                view.setCharAt(x, y, (byte)data);
            }
        }
        
        memory[address] = (byte)(data & 0xFF);
    }
    
    public void set16(int address, int data){
        set8(address, data);
        set8(address + 1, data >> 8);
    }
    
    public void set32(int address, long data){
        if(address >= 0xA000000){
            int position = ((int)address - 0xA000000);
            int x = (position % 0x960) / 3;
            int y = (position / 0x960);

            view.setRGB(x, y, (int)data);
        }else{
            if(address < memory.length){
                set8(address, (int)data);
                set8(address + 1, (int)data >> 8);
                set8(address + 2, (int)data >> 16);
                set8(address + 3, (int)data >> 24);
            }
        }
    }
    
    public void setX(int address, int data){
        if(is32bitOperand()){
            set32(address, data);
        }else{
            set16(address, data);
        }
    }

    public void setData8(int address, int data){
        if(address == 0xA0000000){
            throw new RuntimeException();
        }
        
        address = getDataAddress(address);
        
        if(memory.length > address){
            set8(address, data);
        }
    }
    
    public void setData16(int address, int data){
        if(!is32bitAddress()){
            address = address & 0xFFFF;
        }
        
        address = getDataAddress(address) & 0xFFFFF;
        
        if(address < memory.length){
            if(is32bitOperand()){
                set32(address, data);
            }else{
                set16(address, data);
            }
        }
    }

    public void setData32(long address, long data){
        address = getDataAddress((int)address);

        if(address >= 0xA000000){
            int position = ((int)address - 0xA000000);
            int x = (position % 0x960) / 3;
            int y = (position / 0x960);
            view.setRGB(x, y, (int)data);
        }

        if(address < memory.length){
            if(is32bitOperand()){
                set32((int)address, data);
            }else{
                set16((int)address, (int)data);
            }
        }
    }
    
    public void setDataX(long address, long data){
        if(is32bitAddress()){
            setData32(address, data);
        }else{
            setData16((int)address, (int)data);
        }
    }
    
    public int getRegister8(int index){
        if(index < 4){
            return (int)registers[index] & 0xFF;
        }else{
            return (int)(registers[index - 4] >> 8) & 0xFF;
        }
    }

    public int getRegister8Low(int index){
        return (int)registers[index] & 0xFF;
    }
    
    public int getRegister8High(int index){
        return (int)(registers[index] >> 8) & 0xFF;
    }

    public long getRegisterX(int index){
        if(is32bitOperand()){
            return getRegister32(index);
        }else{
            return getRegister16(index);
        }
    }
    
    public int getRegister16(int index){
        return (int)registers[index] & 0xFFFF;
    }
    
    public long getRegister32(int index){
        return registers[index] & 0xFFFFFFFFL;
    }
    
    public void setRegisterX(int index, long data){
        if(is32bitOperand()){
            setRegister32(index, data & 0xFFFFFFFFL);
        }else{
            setRegister16(index, (int)(data & 0xFFFF));
        }
    }

    public void setRegister8(int index, int data){
        if(index < 4){
            setRegister8Low(index, data);
        }else{
            setRegister8High(index - 4, data);
        }
    }
    
    public void setRegister8Low(int index, int data){
        registers[index] &= 0xFFFFFF00;
        registers[index] |= (data & 0xFF);
    }

    public void setRegister8High(int index, int data){
        registers[index] &= 0xFFFF00FF;
        registers[index] |= ((data & 0xFF) << 8);
    }
    
    public void setRegister16(int index, int data){
        registers[index] &= 0xFFFF0000;
        registers[index] |= (data & 0xFFFF);
        
        if(index == CS && data == 0){
            //throw new RuntimeException();
        }
    }
    
    public void setRegister32(int index, long data){
        registers[index] = data & 0xFFFFFFFFL;
    }
    
    public int getSRegisterValue(int index){
        return (int)registers[index + 9];
    }
    
    public void setSRegisterValue(int index, int data){
        registers[index + 9] = (data & 0xFFFF);
    }

    public int getStackAddress(int address){
         if((cr0 & 0x01) == 0x01 && getRegister16(SS) > 0 && getRegister16(SS) < 0x48){
            int base = getGDT().get(getRegister16(SS) >> 3).getBase();
            address = base + address;
        }else{
            address = getRegister16(SS) * 16 + (address & 0xFFFF);
        }
        
        if(usePaging){
            address = Paging.getAddress(this, address);
        }

        return address;
    }
    
    public int getStackAddress(){
        return getStackAddress((int)getRegister32(ESP));
    }
    
    public void push8(int value){
        setRegisterX(ESP, getRegisterX(ESP) - (is32bitOperand() ? 4 : 2));

        if(is32bitOperand()){
            set32(getStackAddress(), value & 0xFF);
        }else{
            set16(getStackAddress(), value & 0xFF);
        }
    }

    public void push16(int value){
        setRegisterX(ESP, getRegisterX(ESP) - (is32bitOperand() ? 4 : 2));

        if(is32bitOperand()){
            set32(getStackAddress(), value);
        }else{
            set16(getStackAddress(), value);
        }
    }
    
    public void push32(int value){
        setRegisterX(ESP, getRegisterX(ESP) - 4);
        set32(getStackAddress(), value);
    }
    
    public void pushX(int value){
        if(is32bitOperand()){
            push32(value);
        }else{
            push16(value);
        }
    }
    
    public int pop16(){
        int data = getX(getStackAddress());
        setRegisterX(ESP, getRegisterX(ESP) + (is32bitOperand() ? 4 : 2));

        return data;
    }
    
    public int pop32(){
        int data = get32(getStackAddress());
        setRegisterX(ESP, getRegisterX(ESP) + 4);

        return data;
    }

    public int popX(){
        if(is32bitOperand()){
            return pop32();
        }else{
            return pop16();
        }
    }
    
    public VMView getView(){
        return view;
    }

    public EFlags getEFlags(){
        return eflags;
    }
    
    public GDT getGDT(){
        return gdt;
    }

    public void setProtectMode(boolean isProtect){
        this.isProtect = isProtect;
    }
    
    public boolean isProtectMode(int index){
        if(is32Flag()){
            return true;
        }
        
        return (cr0 & 0x01) == 0x01 && (registers[index] >= 8 ) && (registers[index] <= 0x48);
    }
    
    public void set32Flag(boolean is32Flag){
        this.is32Flag = is32Flag;
    }
    
    public boolean is32Flag(){
        return is32Flag;
    }

    public void setOperandPrefix(boolean isOperandPrefix){
        this.isOperandPrefix = isOperandPrefix;
    }

    public boolean isOperandPrefix(){
        return isOperandPrefix;
    }

    public void setAddressPrefix(boolean isAddressPrefix){
        this.isAddressPrefix = isAddressPrefix;
    }

    public boolean isAddressPrefix(){
        return isAddressPrefix;
    }
    
    private boolean isGFlag(int index, boolean isProtect){
        if(isProtect){
            return (gdt.get((int)registers[index] >> 3).getAccessRight() & 0x4000) > 0;
        }else{
            if((cr0 & 0x01) == 0x01){
                return true;
            }else{
                return false;
            }
        }
    }
    
    public boolean is32bitOperand(){
        if(is32Flag()){
            return true;
        }
        
        boolean isProtect = isProtectMode(CS);
        boolean flag = isProtect ^ isOperandPrefix();

        if(flag && isOperandPrefix()){
            flag = true;
        }else if(flag){
            //flag = isGFlag(CS, isProtect);
        }
        
        return flag;
    }
    
    public boolean is32bitAddress(){
        boolean isProtect = isProtectMode(DS);

        return (isProtect ^ isAddressPrefix());// && isGFlag(DS, isProtect);
    }
    
    public void addEIP(int count){
        int next = (int)getRegister32(EIP) + count;
        
        if(!is32bitOperand() && isOperandPrefix()){
            setRegister32(EIP, next & 0xFFFFFFFF);
        }else{
            setRegister32(EIP, next  & (is32bitOperand() ? 0xFFFFFFFF: 0xFFFF));
        }
    }
    
    public void setGDTR(int start, int size){
        gdtr.setGDTR(start, size);
    }
    
    public void setIDTR(int start, int size){
        idtr.setIDTR(start, size);
    }
    
    public IDTR getIDTR(){
        return idtr;
    }
    
    public void setLDTR(int address){
        ldtr = address;
    }
    
    public int getLDTR(){
        return ldtr;
    }
    
    public int getCR0(){
        return cr0;
    }
    
    public void fninit(){
        fpucr.setValue((short)0x37);
    }
    
    public FPUStatusRegister getFSR(){
        return fpusr;
    }
    
    public void setCR0(int cr0){
        if((cr0 & 0x01) == 0x01){
            if((this.cr0 & 0x01) != 0x01){
                lgdt();
            }
        }else{
//            is32bit = false;
        }
   
        usePaging = ((cr0 >> 31) & 0x01) == 0x01;

        this.cr0 = cr0;
    }
    
    public int getCR2(){
        return cr2;
    }
    
    public void setCR2(int cr2){
        this.cr2 = cr2;
    }
    
    public int getCR3(){
        return cr3;
    }
    
    public void setCR3(int cr3){
        this.cr3 = cr3;
    }

    public int getCR4(){
        return cr4;
    }
    
    public void setCR4(int cr4){
        System.out.printf("CR4 = %x\n", cr4);
        this.cr4 = cr4;
    }
    
    public boolean usePaging(){
        return usePaging;
    }

    public void lgdt(){
        int address = gdtr.getStart();

        if(usePaging){
            address = Paging.getAddress(this, address);
        }

        gdt.lgdt(memory, address, gdtr.getSize() / 8);
    }
    
    public void lidt(){
        int start = idtr.getStart();
        
        if(usePaging){
            start = Paging.getAddress(this, start);
        }
        
        idt.lidt(memory, start, idtr.getSize() / 8);
    }
    
    public void addColorElement(int element){
        view.addColorElement(element);
    }
    
    public void setInputKey(int code){
        inputKey = code;
    }
    
    public int getInputKey(){
        return inputKey;
    }
    
    public void interrupt(){
//        System.out.printf("INTINDEX = %x\n", intIndex);
        
        InterruptDescriptor id = idt.get(intIndex);
        int offset = id.getOffset();
        int selector = id.getSelector();
        
        if((getRegister16(CS) & 0x03) > 0){
            int esp = (int)getRegister32(ESP);
            int ss = getRegister16(SS);

            int address = gdt.get(4).getBase() + 4;
            setRegister32(ESP, get32(address));

            address += 4;
            setRegister16(SS, get16(address));

            push32(ss);
            push32(esp);
        }
        
        push32(eflags.get());
        push32(getRegister16(CS));
        push32(getEIP());

        if(intIndex == 0x0E){
            push32(0x01);
        }
        
        setRegister32(EIP, offset & 0xFFFF);
        setRegister16(CS, selector);
        eflags.setInterrupt(false);
        
        intIndex = -1;
    }
    
    public void inputKey(int keycode){
        if(isProtectMode(CS) && pic.availableKey() && intIndex == -1 && eflags.isInterrupt()){
            this.keycode = keycode;
            //intIndex = 0x21;
            intIndex = 0x61;
        }
        
        if(keycode == 'Z'){
            flag = true;
        }
        
        this.keycode = keycode;
    }
    
    public void inputTimer(){
        if(isProtectMode(CS) && pic.availableTimer() && intIndex == -1 && eflags.isInterrupt()){
            //intIndex = 0x20;
            intIndex = 0x60;
        }
    }
    
    
    public void inputFDC(int ret){
        if(isProtectMode(CS)  && intIndex == -1){
            //intIndex = 0x26;
            intIndex = 0x66;
            fdc.setRET(ret);
        }
    }
    
    public void inputHDD(){
        if(isProtectMode(CS) && intIndex == -1){
            intIndex = 0x2E;
        }
    }
    
    public void swi(int index){
        if(intIndex == -1){
            intIndex = index;
            
            if(index == 0x0E){
                throw new PageFaultException();
            }
        }
    }
    
    public synchronized void addCallStack(long address){
//        callStack.add(address);
    }
    
    public synchronized void removeCallStack(){
        //callStack.remove(callStack.size() - 1);
    }
    
    public void dump(){
        System.out.println("EAX = " + Long.toHexString(registers[EAX]));
        System.out.println("EBX = " + Long.toHexString(registers[EBX]));
        System.out.println("ECX = " + Long.toHexString(registers[ECX]));
        System.out.println("EDX = " + Long.toHexString(registers[EDX]));
        System.out.println("ESI = " + Long.toHexString(registers[ESI]));
        System.out.println("EDI = " + Long.toHexString(registers[EDI]));
        System.out.println();

        System.out.println("EIP = " + Long.toHexString(registers[EIP]));
        System.out.println("ESP = " + Long.toHexString(registers[ESP]));
        System.out.println("EBP = " + Long.toHexString(registers[EBP]));
        System.out.println();

        System.out.println("CS = " + Long.toHexString(registers[CS]));
        System.out.println("DS = " + Long.toHexString(registers[DS]));
        System.out.println("ES = " + Long.toHexString(registers[ES]));
        System.out.println("FS = " + Long.toHexString(registers[FS]));
        System.out.println("GS = " + Long.toHexString(registers[GS]));
        System.out.println("SS = " + Long.toHexString(registers[SS]));
        System.out.println();

        System.out.printf("CR0 = %x\n", cr0);
        System.out.printf("CR2 = %x\n", cr2);
        System.out.printf("CR3 = %x\n", cr3);
        System.out.printf("CR4 = %x\n", cr4);

        System.out.print("Next:");

        for(int i = 0; i < 10; i++){
            System.out.print(" " + Integer.toHexString(getCode8(i) & 0xFF));
        }
        
        System.out.println();
        
        System.out.printf("IDTR Start = %x, size = %x\n", idtr.getStart(), idtr.getSize());
        
        /*
        System.out.println("CallStack");
        
        synchronized(this){
            for(long address : callStack){
                System.out.printf("Address = %x\n", address);
            }
        }
        */
    }
}
