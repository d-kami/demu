package vm.client.instruction.add;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class AddALImm8 implements Instruction{
    public void execute(VM vm){
        int al = (byte)vm.getRegister8Low(EAX);
        int imm = vm.getCode8(1) & 0xFF;
        int result = al + imm;
        
        FlagCheck.addCheck(al, imm, result, vm.getEFlags(), 8);
        vm.setRegister8Low(EAX, result);

        vm.addEIP(2);
    }
}