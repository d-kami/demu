package vm.client.instruction.sbb;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class SBBRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = (int)modrm.getRMValue();
        int imm8 = vm.getCode8(2);

        long result = rmx - (imm8 + (vm.getEFlags().isCarry() ? 1 : 0));
        FlagCheck.subCheck(rmx, imm8, result, vm.getEFlags(), 8);
        modrm.setRMValue(result);

        vm.addEIP(3);
    }
}