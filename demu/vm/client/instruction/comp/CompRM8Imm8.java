package vm.client.instruction.comp;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class CompRM8Imm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rm8 = (byte)modrm.getRMValue8();
        int imm = vm.getCode8(2) & 0xFF;

        int result = rm8 - imm;
        FlagCheck.subCheck(rm8, imm, result, vm.getEFlags(), 8);
        vm.addEIP(3);
    }
}