package vm.client.instruction.dec;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;

public class DECRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long result = rmx - 1;

        EFlags eflags = vm.getEFlags();
        boolean isCarry = eflags.isCarry();
        int size = vm.is32bitOperand() ? 32 : 16;
        FlagCheck.check(rmx, result, eflags, size);
        eflags.setCarry(isCarry);

        modrm.setRMValue(result);
        vm.addEIP(2);
    }
}