package vm.client.instruction.aad;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class AAM implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int imm8 = vm.getCode8(1);
        
        int beforeAL = al;
        int ah = al / imm8;
        al = al % imm8;
        
        FlagCheck.check(beforeAL, (ah << 8) | al, vm.getEFlags(), 8);
        vm.setRegister8Low(EAX, al);
        vm.setRegister8High(EAX, ah);
        vm.addEIP(2);
    }
}
