package vm.client.instruction.wbinvd;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class WBINVD implements Instruction{
    public void execute(VM vm){
        vm.addEIP(1);
    }
}
