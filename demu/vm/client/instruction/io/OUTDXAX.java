package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class OUTDXAX implements Instruction{
    public void execute(VM vm){
        int ax = vm.getRegister16(EAX);
        int port = vm.getRegister16(EDX);
        
        vm.out(port, ax);
        
        vm.addEIP(1);
    }
}