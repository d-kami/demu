package vm.client.instruction.descriptor;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.ESP;

public class LLDT implements Instruction{
    public void execute(VM vm){
        boolean is32bit = vm.is32bitAddress();
        
        if(is32bit){
            vm.setAddressPrefix(true);
        }
        
        ModRM modrm = vm.getModRM();
        int value = (int)modrm.getRMValue() & 0xFFFF;
        
        if(is32bit){
            vm.setAddressPrefix(false);
        }
        
        vm.setLDTR(value);
        vm.addEIP(2);
    }
}