package vm.client.instruction.div;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class DIVAXRMX implements Instruction{
    public void execute(VM vm){
        long src = vm.getRegisterX(EDX) << (vm.is32bitOperand() ? 32 : 16);
        src |= (vm.getRegisterX(EAX) & (vm.is32bitOperand() ? 0xFFFFFFFF : 0xFFFF));
        
        ModRM modrm = vm.getModRM();
        long value =  modrm.getRMValue();
        
        int eax = (int)(src / value);
        int edx = (int)(src % value);

        vm.setRegisterX(EAX, eax);
        vm.setRegisterX(EDX, edx);
        
        vm.addEIP(2);
    }
}
