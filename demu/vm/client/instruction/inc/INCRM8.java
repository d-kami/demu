package vm.client.instruction.inc;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;

import static vm.client.register.RegisterIndex.*;

public class INCRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rmx = modrm.getRMValue8();
        int result = rmx + 1;

        EFlags eflags = vm.getEFlags();
        boolean isCarry = eflags.isCarry();
        FlagCheck.check(rmx, result, eflags, 8);
        eflags.setCarry(isCarry);

        modrm.setRMValue8(result);
        vm.addEIP(2);
    }
}