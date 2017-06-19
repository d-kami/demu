package vm.client.instruction.prefix;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class Lock implements Instruction{
    public void execute(VM vm){
        vm.addEIP(1);

        Instruction instruction = vm.getInstruction(vm.getCode8(0));
        instruction.execute(vm);
    }
}