package vm.client.instruction.move;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EIP;

public class MoveALMOffs implements Instruction{
    public void execute(VM vm){
        int data = (int)vm.getDataX((int)vm.getCodeX(1));
        vm.setRegister8(EAX, data);

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}