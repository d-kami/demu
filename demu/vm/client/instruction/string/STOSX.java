package vm.client.instruction.string;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDI;
import static vm.client.register.RegisterIndex.ES;

public class STOSX implements Instruction{
    public void execute(VM vm){
        long eax = vm.getRegisterX(EAX);
        vm.setSegmentDataX(ES, EDI, (int)eax);

        long edi = vm.getRegisterX(EDI);
        int size = vm.is32bitOperand() ? 4 : 2;
        edi += vm.getEFlags().isDirection() ? -size : size;
        vm.setRegisterX(EDI, edi);
        
        vm.addEIP(1);
    }
}
