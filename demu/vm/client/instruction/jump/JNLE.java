package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;;

public class JNLE implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();

        if(!eflags.isZero() && eflags.isSign() == eflags.isOverflow()){
            int diff = vm.getSignedCode8(1);
            vm.addEIP(diff);
        }

        vm.addEIP(2);
    }
}