package vm.client.instruction.flag;

import vm.client.VM;
import vm.client.register.EFlags;
import vm.client.instruction.Instruction;

public class STD implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();
        eflags.setDirection(true);

        vm.addEIP(1);
    }
}