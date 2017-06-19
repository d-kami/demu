package vm.client.instruction.neg;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class NegRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        vm.getEFlags().setCarry(value != 0);
        vm.getEFlags().setZero(value == 0);
        modrm.setRMValue(-value);

        vm.addEIP(2);
    }
}