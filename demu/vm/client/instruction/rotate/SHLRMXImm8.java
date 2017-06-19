package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SHLRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int imm = vm.getCode8(2) & 0xFF;
        int m = (int)(value >> (vm.is32bitOperand() ? 31 : 15)) & 0x01;

        long result = value << imm;
        modrm.setRMValue(result);
        vm.getEFlags().setCarry(m == 0x01);
        vm.addEIP(3);
    }
}