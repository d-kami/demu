package vm.client.instruction.lods;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.ESI;

public class LODSB implements Instruction{
    public void execute(VM vm){
        int address = vm.getSegmentAddress(vm.getSegmentIndex(), ESI);
        int esi = (int)vm.getRegisterX(ESI);
        vm.setRegister8Low(EAX, vm.get8(address));

        if(vm.getEFlags().isDirection()){
            esi--;
        }else{
            esi++;
        }
        
        vm.setRegisterX(ESI, esi);
        
        vm.addEIP(1);
    }
}
