package vm.client.instruction.mul;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.register.EFlags;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class MulALRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        int eax = vm.getRegister8(EAX);
        
        int result = eax * value;
        EFlags eflags = vm.getEFlags();
        
        boolean cof = (eax >> 8) > 0;
        eflags.setCarry(cof);
        eflags.setOverflow(cof);
        
        vm.setRegister16(EAX, result);
        vm.addEIP(2);
    }
}
