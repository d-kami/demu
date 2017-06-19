package vm.client.instruction.wait;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class Wait implements Instruction{
    public void execute(VM vm){
        vm.addEIP(1);
    }
}
