package vm.client.instruction.set;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SetNG implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRMValue8((vm.getEFlags().isZero() || (vm.getEFlags().isSign() != vm.getEFlags().isOverflow())) ? 1 : 0);

        vm.addEIP(2);
    }
}