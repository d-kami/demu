package vm.client.instruction.push;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class PushF implements Instruction{
    public void execute(VM vm){
        vm.pushX(vm.getEFlags().get());
        vm.addEIP(1);
    }
}