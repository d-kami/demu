package vm.client.instruction.stc;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class STC implements Instruction{
    public void execute(VM vm){
        vm.getEFlags().setCarry(true);
        vm.addEIP(1);
    }
}
