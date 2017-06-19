package vm.client.instruction.move;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class MoveRXImmX implements Instruction{
    private final int index;

    public MoveRXImmX(int index){
        this.index = index;
    }

    public void execute(VM vm){
        vm.setRegisterX(index, vm.getCodeX(1));
        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}