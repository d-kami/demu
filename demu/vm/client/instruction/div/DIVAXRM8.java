package vm.client.instruction.div;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class DIVAXRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        
        int src = vm.getRegister16(EAX);
        int value =  modrm.getRMValue8();
        
        int al = (int)(src / value);
        int ah = (int)(src % value);
        
//        System.out.printf("AX = %x, value = %x, AH = %x, AL = %x\n", src, value, ah, al);

        vm.setRegister16(EAX, (ah << 8) | al);
        
        vm.addEIP(2);
    }
}
