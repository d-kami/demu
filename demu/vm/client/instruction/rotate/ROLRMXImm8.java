package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class ROLRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int imm = vm.getCode8(2) & 0xFF;

        int shift = vm.is32bitOperand() ? 32 : 16;

        value <<= imm;
        value |= (value >> shift) & ((1 << imm) - 1);
        value &= ((long)1 << shift) - 1;

        modrm.setRMValue(value);
        vm.addEIP(3);
    }
}