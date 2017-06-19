package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SHRRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int imm = vm.getCode8(2) & 0xFF;

        long result = value >> imm;
        modrm.setRMValue(result);
        vm.addEIP(3);
    }
}