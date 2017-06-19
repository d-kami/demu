package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SHRRM8Imm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        int imm = vm.getCode8(2) & 0xFF;

        int result = value >> imm;
        modrm.setRMValue8(result);
        vm.addEIP(3);
    }
}