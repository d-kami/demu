package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.*;

public class Bios15 implements Instruction{
    int[] mmapt = new int[]{1, 2, 2, 1, 1, 2, 2, 1, 3, 0};
    int[] base = new int[]{0, 0x9f000, 0xe8000, 0x100000, 0, 0x9f000, 0xe8000, 0x100000,
                           0x1ff0000, 0xfffc0000};

    int[] length = new int[]{0x9f000, 0x1000, 0x18000, 0x1ef0000, 0, 0x9f000, 0x1000,
                           0x18000, 0x1ef0000, 0x40000};

    int index = 0;

    public void execute(VM vm){
        if(vm.getRegister8High(EAX) == 0x88){
            vm.setRegister8Low(EAX, 0xFF);
            vm.setRegister8High(EAX, 0x80);
            vm.getEFlags().setCarry(false);
        }else if(vm.getRegister16(EAX) == 0x5300){
            vm.setRegister16(EAX, 0);
            vm.setRegister16(EBX, 0x504D);
            vm.setRegister16(ECX, 0);
            vm.getEFlags().setCarry(false);
        }else if(vm.getRegister8High(EAX) == 0x87){
            vm.setRegister8High(EAX, 0x00);
            vm.getEFlags().setCarry(false);
        }else if(vm.getRegister16(EAX) == 0xE801){
            vm.setRegister16(EAX, 0x3C00);
            vm.setRegister16(EBX, 0);
            vm.setRegister16(ECX, 0x3C00);
            vm.setRegister16(EDX, 0);
            vm.getEFlags().setCarry(false);
        }else if(vm.getRegister16(EAX) == 0xE820){
            vm.setRegister32(EAX, 0x534D4150);
            vm.setRegister32(ECX, 0x14);

            int ebx = (int)vm.getRegister32(EBX);
            int di = vm.getRegister16(EDI);
            vm.setData32(di, base[index]);
            vm.setData32(di + 0x04, 0);
            vm.setData32(di + 0x08, length[index]);
            vm.setData32(di + 0x0C, 0);
            vm.setData32(di + 0x10, mmapt[index++]);
 
            if(ebx == 6){
                vm.setRegister32(EBX, 0);
            }else if(ebx != 3){
                vm.setRegister32(EBX, ebx + 1);
            }else{
                vm.setRegister32(EBX, ebx + 2);
            }

            vm.getEFlags().setCarry(false);
        }else{
            throw new RuntimeException("INT 0x15 = " + Integer.toHexString(vm.getRegister16(EAX)));
        }
    }
}