package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class SARRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = (int)modrm.getRMValue();
        int lsb = (int)value & 0x01;
        long result = value >> 1;

        modrm.setRMValue(result);
        vm.getEFlags().setCarry(lsb == 0x01);
        vm.addEIP(2);
    }
}