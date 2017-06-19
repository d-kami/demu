package vm.client.instruction.fpu;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class FSETPM implements Instruction{
    public void execute(VM vm){
        vm.addEIP(2);
    }
}
