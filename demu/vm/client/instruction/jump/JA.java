package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class JA implements Instruction{
    public void execute(VM vm){
        if(!vm.getEFlags().isCarry() && !vm.getEFlags().isZero()){
            int diff = vm.getSignedCode8(1);
            vm.addEIP(diff);
        }

        vm.addEIP(2);
    }
}