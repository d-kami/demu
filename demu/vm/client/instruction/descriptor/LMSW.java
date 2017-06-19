package vm.client.instruction.descriptor;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class LMSW implements Instruction{
    public void execute(VM vm){
        boolean is32bit = vm.is32bitAddress();
        
        if(is32bit){
            vm.setAddressPrefix(true);
        }
        
        ModRM modrm = vm.getModRM();
        vm.setCR0((int)modrm.getRMValue());
        
        if(is32bit){
            vm.setAddressPrefix(false);
        }
        
        vm.addEIP(2);
    }
}
