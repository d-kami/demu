package vm.client.instruction.set;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;

public class SetLE implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        EFlags eflags = vm.getEFlags();

        modrm.setRMValue8(eflags.isZero() || (eflags.isSign() != eflags.isOverflow()) ? 1 : 0);

        vm.addEIP(2);
    }
}