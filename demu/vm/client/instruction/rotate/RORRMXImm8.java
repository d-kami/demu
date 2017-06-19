package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class RORRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int imm = vm.getCode8(2) & 0xFF;

        int shift = vm.is32bitOperand() ? 32 : 16;

        long left = (value & ((1 << imm) - 1)) << (shift - imm);
        value >>= imm;
        value |= left;

        modrm.setRMValue(value);
        vm.addEIP(3);
    }
}