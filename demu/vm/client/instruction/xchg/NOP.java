package vm.client.instruction.xchg;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class NOP implements Instruction{
    public void execute(VM vm){
        vm.addEIP(1);
    }
}