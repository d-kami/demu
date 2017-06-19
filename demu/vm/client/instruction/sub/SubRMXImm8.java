package vm.client.instruction.sub;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class SubRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = (int)modrm.getRMValue();
        int imm = vm.getSignedCode8(2);

        long result = rmx - imm;
        FlagCheck.subCheck(rmx, imm, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRMValue(result);
        
        vm.addEIP(3);
    }
}