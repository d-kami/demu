package vm.client.instruction.cwd;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class CWD implements Instruction{
    public void execute(VM vm){
        if(vm.is32bitOperand()){
            vm.setRegister32(EDX, vm.getRegister32(EAX));
        }else{
            vm.setRegister16(EDX, vm.getRegister16(EAX));
        }
        
        vm.addEIP(1);
    }
}
