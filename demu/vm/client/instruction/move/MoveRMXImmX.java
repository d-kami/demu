package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveRMXImmX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRMValue(vm.getCodeX(2));

        vm.addEIP(vm.is32bitOperand() ? 6 : 4);
    }
}