package vm.client.instruction.pop;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class Pop implements Instruction{
    private final int index;

    public Pop(int index){
        this.index = index;
    }

    public void execute(VM vm){
        vm.setRegisterX(index, vm.popX());

        vm.addEIP(1);
    }
}