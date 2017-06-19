package vm.client.instruction.sbb;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class SBBRM8R8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rm8 = modrm.getRMValue8();
        int r8 = modrm.getRegister8();

        int result = rm8 - (r8 + (vm.getEFlags().isCarry() ? 1 : 0));
        FlagCheck.subCheck(rm8, r8, result, vm.getEFlags(), 8);
        modrm.setRMValue8(result);

        vm.addEIP(2);
    }
}