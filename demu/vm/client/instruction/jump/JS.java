package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class JS implements Instruction{
    public void execute(VM vm){
        if(vm.getEFlags().isSign()){
            int diff = vm.getSignedCode8(1);
            vm.addEIP(diff);
        }

        vm.addEIP(2);
    }
}