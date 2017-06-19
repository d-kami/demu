package vm.client.instruction.flag;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class CLTS implements Instruction{
    public void execute(VM vm){
        int cr0 = vm.getCR0();
        cr0 &= ~8;
        vm.setCR0(cr0);

        vm.addEIP(1);
    }
}