package vm.client.instruction.fpu;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class FNInit implements Instruction{
    public void execute(VM vm){
        vm.fninit();
        vm.addEIP(2);
    }
}
