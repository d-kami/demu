package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class RelativeNearJump implements Instruction{
    public void execute(VM vm){
        int diff = vm.getSignedCodeX(1) & (vm.is32bitOperand() ? 0xFFFFFFFF : 0xFFFF);

        vm.addEIP(diff + (vm.is32bitOperand() ? 5 : 3));
    }
}