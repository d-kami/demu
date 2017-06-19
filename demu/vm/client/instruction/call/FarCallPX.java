package vm.client.instruction.call;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class FarCallPX implements Instruction{
    public void execute(VM vm){
        int cs = vm.getCode16(vm.is32bitOperand() ? 5 : 3);
        int eip = (int)vm.getCodeX(1);

        vm.pushX(vm.getRegister16(CS));
        vm.pushX((int)vm.getRegister32(EIP) + (vm.is32bitOperand() ? 7 : 5));

        vm.setRegister32(EIP, eip);
        vm.setRegister16(CS, cs);
    }
}