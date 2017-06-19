package vm.client.instruction.imul;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class IMulAXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long eax = vm.getRegisterX(EAX);
        long rmx = modrm.getRMValue();
        long result = eax * rmx;
        
        if(result != (int)result){
            vm.getEFlags().setOverflow(true);
            vm.getEFlags().setSign(true);
        }

        vm.setRegisterX(EAX, result & 0xFFFFFFFF);
        vm.setRegisterX(EDX, (result >> 32) & 0xFFFFFFFF);
        vm.addEIP(2);
    }
}