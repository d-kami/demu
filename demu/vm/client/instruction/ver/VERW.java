package vm.client.instruction.ver;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class VERW implements Instruction{
    public void execute(VM vm){
        vm.getModRM();
        vm.getEFlags().setZero(true);
        vm.addEIP(2);
    }
}
