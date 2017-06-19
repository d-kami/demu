package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.EAX;

public class OUTImm8AL implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int port = vm.getCode8(1) & 0xFF;
        
        if(vm.getFlag()){
            System.out.printf("port = %x, value = %x\n", port, al);
        }
        
        vm.out(port, al);

        vm.addEIP(2);
    }
}