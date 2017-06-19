package vm.client.instruction.fpu;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.EAX;

public class FNSTSWAX implements Instruction{
    public void execute(VM vm){
        vm.setRegister16(EAX, vm.getFSR().getValue());
        vm.addEIP(2);
    }
}
