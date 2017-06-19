package vm.client.instruction.move;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class MoveR8Imm8 implements Instruction{
    private final int index;

    public MoveR8Imm8(int index){
        this.index = index;
    }

    public void execute(VM vm){
        vm.setRegister8(index, vm.getCode8(1) & 0xFF);
        vm.addEIP(2);
    }
}