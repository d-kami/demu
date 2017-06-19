package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class JEX implements Instruction{
    public void execute(VM vm){
        if(vm.getEFlags().isZero()){
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