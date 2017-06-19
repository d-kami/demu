package vm.client.instruction.string;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDI;
import static vm.client.register.RegisterIndex.ES;

public class SCAS8 implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int data = vm.get8(vm.getSegmentAddress(ES, EDI));
        long edi = vm.getRegisterX(EDI);
        
        int result = al - data;
        FlagCheck.subCheck(al, data, result, vm.getEFlags(), 8);
        
        edi += vm.getEFlags().isDirection() ? -1 : 1;
        vm.setRegisterX(EDI, edi);
        
        vm.addEIP(1);
    }
}
