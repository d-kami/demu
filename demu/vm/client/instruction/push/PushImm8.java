package vm.client.instruction.push;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class PushImm8 implements Instruction{
    public void execute(VM vm){
        int imm = vm.getSignedCode8(1);
        vm.pushX(imm);

        vm.addEIP(2);
    }
}