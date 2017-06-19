package vm.client.instruction.push;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class PushImm16 implements Instruction{
    public void execute(VM vm){
        int imm = (int)vm.getCodeX(1);
        vm.pushX(imm);

        if(vm.is32bitOperand()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}