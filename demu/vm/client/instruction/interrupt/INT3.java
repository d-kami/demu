package vm.client.instruction.interrupt;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class INT3 implements Instruction{
    public void execute(VM vm){
        vm.swi(0x03);
        vm.addEIP(1);
    }
}

