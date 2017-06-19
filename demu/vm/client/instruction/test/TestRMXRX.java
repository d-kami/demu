package vm.client.instruction.test;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class TestRMXRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long src = modrm.getRMValue();
        long dest = modrm.getRegisterX();

        long result = src & dest;
        FlagCheck.check(src, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);

        vm.addEIP(2);
    }
}