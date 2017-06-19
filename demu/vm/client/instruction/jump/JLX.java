package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;

public class JLX implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();

        if(eflags.isSign() != eflags.isOverflow()){
            int diff = (int)vm.getSignedCodeX(1);
            vm.addEIP(diff);
        }

        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}