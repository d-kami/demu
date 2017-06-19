package vm.client.instruction.task;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class LTR implements Instruction{
    public void execute(VM vm){
        boolean is32bit = vm.is32bitAddress();
        
        if(is32bit){
            vm.setAddressPrefix(true);
        }
        
        ModRM modrm = vm.getModRM();
        //int value = (int)modrm.getRMValue() & 0xFFFF;
        //vm.ltr(value);
        
        if(is32bit){
            vm.setAddressPrefix(false);
        }
        
        vm.addEIP(2);
    }
}