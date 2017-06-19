package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class OUTDXAL implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int port = vm.getRegister16(EDX);
        vm.out(port, al);

        vm.addEIP(1);
    }
}