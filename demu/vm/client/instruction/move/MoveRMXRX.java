package vm.client.instruction.move;

import vm.client.VM;
import vm.client.view.VMView;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveRMXRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();

        modrm.setRMValue(modrm.getRegisterX());
        vm.addEIP(2);
    }
}