package vm.client.instruction.comp;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class CompRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = (int)modrm.getRMValue();
        int imm = vm.getSignedCode8(2);
        long result = rmx - imm;
        
        FlagCheck.subCheck(rmx, imm, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        vm.addEIP(3);
    }
}