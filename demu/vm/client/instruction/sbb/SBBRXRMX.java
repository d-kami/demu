package vm.client.instruction.sbb;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class SBBRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long rx = modrm.getRegisterX();

        long result = rmx - (rx + (vm.getEFlags().isCarry() ? 1 : 0));
        FlagCheck.subCheck(rmx, rx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRMValue(result);

        vm.addEIP(2);
    }
}