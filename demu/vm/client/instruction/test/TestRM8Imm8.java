package vm.client.instruction.test;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class TestRM8Imm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int src = modrm.getRMValue8();
        int dest = vm.getCode8(2) & 0xFF;

        int result = src & dest;
        FlagCheck.check(src, result, vm.getEFlags(), 8);

        vm.addEIP(3);
    }
}