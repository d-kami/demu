package vm.client.instruction.test;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class TestRM8R8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int src = modrm.getRMValue8();
        int dest = modrm.getRegister8();

        int result = src & dest;
        FlagCheck.check(src, result, vm.getEFlags(), 8);

        vm.addEIP(2);
    }
}