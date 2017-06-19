package vm.client.instruction.set;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SetNBE implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRMValue8((!vm.getEFlags().isZero() && !vm.getEFlags().isCarry()) ? 1 : 0);

        vm.addEIP(2);
    }
}