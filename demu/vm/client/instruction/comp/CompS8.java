package vm.client.instruction.comp;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.DS;
import static vm.client.register.RegisterIndex.ES;
import static vm.client.register.RegisterIndex.ESI;
import static vm.client.register.RegisterIndex.EDI;

public class CompS8 implements Instruction{
    public void execute(VM vm){
        int esi = (int)vm.getRegisterX(ESI);
        int edi = (int)vm.getRegisterX(EDI);
        
        int src = vm.get8(vm.getSegmentAddress(vm.getSegmentIndex(), ESI));
        int dst = vm.get8(vm.getSegmentAddress(ES, EDI));
        
        int result = src - dst;
        FlagCheck.subCheck(src, dst, result, vm.getEFlags(), 8);
        
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
