package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;

public class JLEX implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();

        if(eflags.isSign() != eflags.isOverflow() || eflags.isZero()){
            int diff = (int)vm.getSignedCodeX(1);
            vm.addEIP(diff);
        }

        if(vm.is32bitOperand()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}