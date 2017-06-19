package vm.client.instruction.pop;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class PopF implements Instruction{
    public void execute(VM vm){
        vm.getEFlags().set(vm.popX());
        vm.addEIP(1);
    }
}