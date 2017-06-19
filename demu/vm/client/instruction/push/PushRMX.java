package vm.client.instruction.push;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class PushRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        vm.pushX((int)modrm.getRMValue());
        vm.addEIP(2);
    }
}