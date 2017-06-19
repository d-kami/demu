package vm.client.instruction.cbw;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class CBW implements Instruction{
    public void execute(VM vm){
        if(vm.is32bitOperand()){
            vm.setRegister32(EAX, (short)vm.getRegister16(EAX));
        }else{
            vm.setRegister16(EAX, (byte)vm.getRegister8Low(EAX));
        }
        
        vm.addEIP(1);
    }
}
