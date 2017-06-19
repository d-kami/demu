package vm.client.instruction.push;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class Push implements Instruction{
    private final int index;

    public Push(int index){
        this.index = index;
    }

    public void execute(VM vm){
        vm.pushX((int)vm.getRegisterX(index));

        vm.addEIP(1);
    }
    
    @Override
    public String toString(){
        return String.format("Push %x", index);
    }
}