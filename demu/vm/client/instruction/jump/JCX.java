package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class JCX implements Instruction{
    public void execute(VM vm){
        if(vm.getEFlags().isCarry()){
            int diff = vm.getSignedCodeX(1);
            vm.addEIP(diff);
        }

        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}