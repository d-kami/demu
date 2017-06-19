package vm.client.instruction.move;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ES;
import static vm.client.register.RegisterIndex.ESI;
import static vm.client.register.RegisterIndex.EDI;

public class MoveSX implements Instruction{
    public void execute(VM vm){
        int src = vm.getSegmentAddress(vm.getSegmentIndex(), ESI) & 0xFFFFFFFF;
        int dst = vm.getSegmentAddress(ES, EDI) & 0xFFFFFFF;
        long esi = vm.getRegisterX(ESI);
        long edi = vm.getRegisterX(EDI);

        vm.setX(dst, vm.getX(src));
        
        int size = vm.is32bitOperand() ? 4 : 2;
        
        if(vm.getEFlags().isDirection()){
            esi -= size;
            edi -= size;
        }else{
            esi += size;
            edi += size;
        }
        
        vm.setRegisterX(ESI, esi);
        vm.setRegisterX(EDI, edi);
        
        vm.addEIP(1);
    }
}
