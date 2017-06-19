package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class INALDX implements Instruction{
    public void execute(VM vm){
        int port = vm.getRegister16(EDX);
        vm.setRegister8Low(EAX, vm.in(port));
        
        vm.addEIP(1);
    }
}