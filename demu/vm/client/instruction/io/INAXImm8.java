package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class INAXImm8 implements Instruction{
    public void execute(VM vm){
        int port = vm.getCode8(1) & 0xFF;

        vm.setRegister16(EAX, vm.in(port));

        vm.addEIP(2);
    }
}