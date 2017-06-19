package vm.client.instruction.string;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDI;
import static vm.client.register.RegisterIndex.ES;

public class SCASX implements Instruction{
    public void execute(VM vm){
        long eax = vm.getRegisterX(EAX);
        long data = vm.getX(vm.getSegmentAddress(ES, EDI));
        long edi = vm.getRegisterX(EDI);
        
        long result = eax - data;
        FlagCheck.subCheck(eax, data, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        
        int size = vm.is32bitOperand() ? 4 : 2;
        edi += vm.getEFlags().isDirection() ? -size : size;
        vm.setRegisterX(EDI, edi);
        
        vm.addEIP(1);
    }
}
