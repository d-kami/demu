package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ES;
import static vm.client.register.RegisterIndex.EDI;
import static vm.client.register.RegisterIndex.EDX;

public class INSX implements Instruction{
    public void execute(VM vm){
        int port = vm.getRegister16(EDX);
        int address = vm.getSegmentAddress(ES, EDI);

        int data = vm.in(port);
        vm.setX(address, data);
        
        int size = vm.is32bitOperand() ? 4 : 2;
        if(vm.getEFlags().isDirection()){
            vm.setRegister32(EDI, vm.getRegister32(EDI) - size);
        }else{
            vm.setRegister32(EDI, vm.getRegister32(EDI) + size);
        }

        vm.addEIP(1);
    }
}