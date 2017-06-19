package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class ROLRM8Imm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        int imm = vm.getCode8(2) & 0xFF;

        int shift = 8;
        value <<= imm;
        value |= (value >> shift) & ((1 << imm) - 1);
        value &= (1 << shift) - 1;

        modrm.setRMValue8(value);
        vm.addEIP(3);
    }
}