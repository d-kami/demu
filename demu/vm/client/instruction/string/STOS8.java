package vm.client.instruction.string;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDI;
import static vm.client.register.RegisterIndex.ES;

public class STOS8 implements Instruction{
    public void execute(VM vm){
        long eax = vm.getRegister8(EAX);
        int address = vm.getSegmentAddress(ES, EDI);
        long edi = vm.getRegisterX(EDI);
        
        vm.setX(address, (int)eax);

        edi += vm.getEFlags().isDirection() ? -1 : 1;
        vm.setRegisterX(EDI, edi);
        
        vm.addEIP(1);
    }
}
