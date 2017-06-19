package vm.client.instruction.inc;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;
import vm.client.register.EFlags;

import static vm.client.register.RegisterIndex.*;

public class INC implements Instruction{
    private int index;

    public INC(int index){
        this.index = index;
    }

    public void execute(VM vm){
        long register = vm.getRegisterX(index);
        long result = register + 1;

        EFlags eflags = vm.getEFlags();
        boolean isCarry = eflags.isCarry();
        int size = vm.is32bitOperand() ? 32 : 16;
        FlagCheck.check(register, result, eflags, size);
        eflags.setCarry(isCarry);

        vm.setRegisterX(index,  result);
        vm.addEIP(1);
    }
}