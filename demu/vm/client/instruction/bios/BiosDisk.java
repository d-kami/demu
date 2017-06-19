package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;

import static vm.client.register.RegisterIndex.*;

public class BiosDisk implements Instruction{
    public void execute(VM vm){
        int ah = vm.getRegister8High(EAX);
        int al = vm.getRegister8Low(EAX);
        int ch = vm.getRegister8High(ECX);
        int cl = vm.getRegister8Low(ECX);
        int dh = vm.getRegister8High(EDX);
        int dl = vm.getRegister8Low(EDX);
        int ax = vm.getRegister16(EAX);
        int bx = vm.getRegister16(EBX);

        EFlags eflags = vm.getEFlags();
        
        if(ah == 0x00){
            eflags.setCarry(false);
        }else if(ah == 0x02){
            try{
                int cylinder = ch;
                int head = dh;
                int sector = cl;

                int readStart = cylinder * 2 * 18 * 512;
                readStart += head * 18 * 512;
                readStart += (sector - 1) * 512;

                int start = (vm.getRegister16(ES) << 4) + bx;
                vm.read(readStart, start, 512 * al);

                vm.setRegister8High(EAX, 0);
                vm.getEFlags().setCarry(false);
            }catch(Exception e){
                e.printStackTrace();
                vm.getEFlags().setCarry(true);
            }
        }else if(ah == 0x08){
            vm.setRegister16(EAX, 0);
            vm.setRegister16(EBX, 0x02);
            vm.setRegister16(ECX, 0xFFFF);
            vm.setRegister16(EDX, 0x0101);
            vm.getEFlags().setCarry(false);
        }else if(ah == 0x15){
            vm.setRegister16(EAX, 0x100);
        }else if(ah == 0x41 && bx == 0x55aa){
            eflags.setCarry(false);
            vm.setRegister8(ECX, cl | 0x01);
            vm.setRegister16(EBX, 0xaa55);
        }else if(ah == 0x42){
            try{
                int esi = (int)vm.getRegisterX(ESI);
                int size = (int)vm.getData16(esi);
                int sector = (int)vm.getData16(esi + 2);
                int buffStart = (int)vm.getData16(esi + 4);
                int selector = (int)vm.getData16(esi + 6);

                int readSector = (int)vm.getData32(esi + 8);
                int start = (selector << 4) + buffStart;
                
                vm.read(readSector * 512, start, sector * 512);

                vm.setRegister8High(EAX, 0);
                vm.getEFlags().setCarry(false);
            }catch(Exception e){
                e.printStackTrace();
                vm.getEFlags().setCarry(true);
            }
        }else if(ah == 0x48){
            int address = vm.getRegister16(ESI);
            vm.setData16(address, 0x1A);
            vm.setData16(address + 0x02, 2);
            vm.setData32(address + 0x04, 80);
            vm.setData32(address + 0x08, 2);
            vm.setData32(address + 0x0C, 18);
            vm.setData32(address + 0x10, 80 * 2 * 18);
            vm.setData32(address + 0x14, 0);
            vm.setData16(address + 0x18, 512);
            vm.getEFlags().setCarry(false);
        }else if(ax == 0x4B01){
            int esi = (int)vm.getRegisterX(ESI);
            vm.setData8(esi  + 0x00, 0x13);
            vm.setData8(esi  + 0x01, 0x07);
            vm.setData8(esi  + 0x02, 0x00);
            vm.setData8(esi  + 0x03, 0x00);
            vm.setData32(esi + 0x04, 0x00);
            vm.setData16(esi + 0x08, 0x00);
            vm.setData16(esi + 0x0A, 0x00);
            vm.setData16(esi + 0x0C, 0x00);
            vm.setData16(esi + 0x0E, 0x00);
            vm.setData8(esi  + 0x10, 0xFF);
            vm.setData8(esi  + 0x11, 0xFF);
            vm.setData8(esi +  0x12, 0x01);

            vm.getEFlags().setCarry(false);
        }else{
            throw new RuntimeException("Not Implement BiosDisk AH = " + Integer.toHexString(ah));
        }
    }
}