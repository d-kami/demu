package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SHRRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int lsb = (int)value & 0x01;
        int msb = (int)value >> (vm.is32bitOperand() ? 31 : 15);

        long result = value >> 1;
        modrm.setRMValue(result);

        vm.getEFlags().setCarry(lsb == 0x01);
        vm.getEFlags().setOverflow(msb == 0x01);
        vm.addEIP(2);
    }
}