package vm.client.instruction.mul;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.register.EFlags;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class MulAXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        long eax = vm.getRegisterX(EAX);
        
        long result = eax * value;
        EFlags eflags = vm.getEFlags();
        int edx = 0;
        
        if(vm.is32bitOperand()){
            eax = result & 0xFFFFFFFF;
            edx = (int)((result >> 32) & 0xFFFFFFFF);
        }else{
            eax = result & 0xFFFF;
            edx = (int)((result >> 16) & 0xFFFF);
        }
        
        boolean cof = (edx != 0);
        eflags.setCarry(cof);
        eflags.setOverflow(cof);
        
        vm.setRegisterX(EAX, eax);
        vm.setRegisterX(EDX, edx);
        
        vm.addEIP(2);
    }
}
