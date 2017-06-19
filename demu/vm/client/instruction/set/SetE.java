package vm.client.instruction.set;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SetE implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRMValue8(vm.getEFlags().isZero() ? 1 : 0);

        vm.addEIP(2);
    }
}