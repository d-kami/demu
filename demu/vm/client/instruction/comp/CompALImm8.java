package vm.client.instruction.comp;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class CompALImm8 implements Instruction{
    public void execute(VM vm){
        int al = (byte)vm.getRegister8Low(EAX);
        int imm = vm.getCode8(1);

        int result = al - imm;
        FlagCheck.subCheck(al, imm, result, vm.getEFlags(), 8);

        vm.addEIP(2);
    }
}