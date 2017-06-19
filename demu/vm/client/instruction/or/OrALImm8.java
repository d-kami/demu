package vm.client.instruction.or;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class OrALImm8 implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int imm = vm.getCode8(1);

        int result = al | imm;
        FlagCheck.check(al, result, vm.getEFlags(), 8);
        vm.setRegister8(EAX, result);

        vm.addEIP(2);
    }
}