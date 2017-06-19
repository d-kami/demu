package vm.client.instruction.move;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class MoveMOffsAX implements Instruction{
    public void execute(VM vm){
        int address = 0;
        
        if(vm.is32bitAddress()){
            address = (int)vm.getCode32(1) & 0xFFFFFFFF;
        }else{
            address = vm.getCode16(1) & 0xFFFF;
        }

        vm.setDataX(address, vm.getRegisterX(EAX));

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}