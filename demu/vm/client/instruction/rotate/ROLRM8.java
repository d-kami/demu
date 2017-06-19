package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class ROLRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();

        int shift = 8;
        value <<= 1;
        value |= (value >> 8) & 0x01;
        value &= 0xFF;

        modrm.setRMValue8(value);
        vm.addEIP(2);
    }
}