package vm.client.instruction.test;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class TestAXImmX implements Instruction{
    public void execute(VM vm){
        long eax = vm.getRegisterX(EAX);
        long value = vm.getCodeX(1) & 0xFFFFFFFFL;

        long result = eax & value;
        FlagCheck.check(eax, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);

        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}