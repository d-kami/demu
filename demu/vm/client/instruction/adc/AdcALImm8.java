package vm.client.instruction.adc;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class AdcALImm8 implements Instruction{
    public void execute(VM vm){
        int al = (byte)vm.getRegister8Low(EAX);
        int imm8 = vm.getCode8(1);

        int result = al + imm8 + (vm.getEFlags().isCarry() ? 1 : 0);
        FlagCheck.addCheck(al, imm8, result, vm.getEFlags(), 8);
        vm.setRegister8Low(EAX, result);

        vm.addEIP(2);
    }
}