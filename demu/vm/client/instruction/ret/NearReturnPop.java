package vm.client.instruction.ret;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EIP;
import static vm.client.register.RegisterIndex.ESP;

public class NearReturnPop implements Instruction{
    public void execute(VM vm){
        int eip = vm.popX();
        int count = vm.getCode16(1);

        vm.setRegisterX(EIP, eip);
        vm.setRegisterX(ESP, vm.getRegisterX(ESP) + count);
        vm.removeCallStack();
    }
}