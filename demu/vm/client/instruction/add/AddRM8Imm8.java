package vm.client.instruction.add;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class AddRM8Imm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rm8 = (byte)modrm.getRMValue8();
        int imm8 = vm.getCode8(2);

        int result = rm8 + imm8;
        FlagCheck.addCheck(rm8, imm8, result, vm.getEFlags(), 8);
        modrm.setRMValue8(result);

        vm.addEIP(3);
    }
}