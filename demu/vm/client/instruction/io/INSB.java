package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ES;
import static vm.client.register.RegisterIndex.EDI;
import static vm.client.register.RegisterIndex.EDX;

public class INSB implements Instruction{
    public void execute(VM vm){
        int port = vm.getRegister16(EDX) & 0xFFFF;
        int address = vm.getSegmentAddress(ES, EDI);

        int data = vm.in(port & 0xFF);
        vm.set8(address, data);
        
        int size = 1;
        if(vm.getEFlags().isDirection()){
            vm.setRegister32(EDI, vm.getRegister32(EDI) - size);
        }else{
            vm.setRegister32(EDI, vm.getRegister32(EDI) + size);
        }

        vm.addEIP(1);
    }
}