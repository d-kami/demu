package vm.client.instruction.move;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class MoveMOffsAL implements Instruction{
    public void execute(VM vm){
        vm.setData8((int)vm.getCodeX(1), vm.getRegister8(EAX));

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}