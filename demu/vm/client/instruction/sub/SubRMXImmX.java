package vm.client.instruction.sub;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class SubRMXImmX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = (int)modrm.getRMValue();
        long imm = vm.getCodeX(2);

        long result = rmx - imm;
        FlagCheck.subCheck(rmx, imm, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRMValue(result);

        vm.addEIP(vm.is32bitOperand() ? 6 : 4);
    }
}