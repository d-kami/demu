package vm.client.instruction.flag;

import vm.client.VM;
import vm.client.register.EFlags;
import vm.client.instruction.Instruction;

public class CLC implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();
        eflags.setCarry(false);

        vm.addEIP(1);
    }
}