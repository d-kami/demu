package vm.client.instruction.move;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ES;
import static vm.client.register.RegisterIndex.ESI;
import static vm.client.register.RegisterIndex.EDI;

public class MoveS8 implements Instruction{
    public void execute(VM vm){
        int src = vm.getSegmentAddress(vm.getSegmentIndex(), ESI);
        int dst = vm.getSegmentAddress(ES, EDI);
        long esi = vm.getRegisterX(ESI);
        long edi = vm.getRegisterX(EDI);
        
        vm.set8(dst, vm.get8(src));
        
        if(vm.getEFlags().isDirection()){
            esi--;
            edi--;
        }else{
            esi++;
            edi++;
        }
        
        vm.setRegisterX(ESI, esi);
        vm.setRegisterX(EDI, edi);
        
        vm.addEIP(1);
    }
}
