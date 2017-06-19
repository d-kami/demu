package vm.client.instruction.flag;

import vm.client.VM;
import vm.client.register.EFlags;
import vm.client.instruction.Instruction;

public class CMC implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();
        eflags.setCarry(!eflags.isCarry());

        vm.addEIP(1);
    }
}