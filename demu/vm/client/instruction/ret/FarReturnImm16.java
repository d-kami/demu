package vm.client.instruction.ret;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.CS;
import static vm.client.register.RegisterIndex.ESP;
import static vm.client.register.RegisterIndex.EIP;

public class FarReturnImm16 implements Instruction{
    public void execute(VM vm){
        int eip = vm.popX();
        int cs = vm.popX();
        int count = vm.getCode16(1);

        vm.setRegisterX(ESP, vm.getRegisterX(ESP) + count);
        vm.setRegister16(CS, cs);
        vm.setRegisterX(EIP, eip);
    }
}
