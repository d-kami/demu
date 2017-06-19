package vm.client.instruction.aad;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class AAD implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int ah = vm.getRegister8High(EAX);
        int imm8 = vm.getCode8(1);
        
        al = (al + (ah * imm8)) & 0xFF;

        FlagCheck.check(vm.getRegister16(EAX), al, vm.getEFlags(), 8);
        vm.setRegister8Low(EAX, al);
        vm.setRegister8High(EAX, 0);
        vm.addEIP(2);
    }
}
