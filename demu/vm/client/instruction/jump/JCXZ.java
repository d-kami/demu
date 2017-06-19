package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.ECX;

public class JCXZ implements Instruction{
    public void execute(VM vm){
        if(vm.getRegisterX(ECX) == 0){
            int diff = vm.getSignedCode8(1);
            vm.addEIP(diff);
        }

        vm.addEIP(2);
    }
}