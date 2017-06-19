package vm.client.instruction.leave;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.EBP;
import static vm.client.register.RegisterIndex.ESP;

public class Leave implements Instruction{
    public void execute(VM vm){
        vm.setRegisterX(ESP, vm.getRegisterX(EBP));
        vm.setRegisterX(EBP, vm.popX());

        vm.addEIP(1);
    }
}