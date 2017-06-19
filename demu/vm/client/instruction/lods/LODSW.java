package vm.client.instruction.lods;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.ESI;

public class LODSW implements Instruction{
    public void execute(VM vm){
        int address = vm.getSegmentAddress(vm.getSegmentIndex(), ESI);
        int esi = (int)vm.getRegisterX(ESI);
        vm.setRegisterX(EAX, vm.getX(address));
        int size = vm.is32bitOperand() ? 4 : 2;
        
        if(vm.getEFlags().isDirection()){
            esi -= size;
        }else{
            esi += size;
        }
        
        vm.setRegisterX(ESI, esi);
        
        vm.addEIP(1);
    }
}
