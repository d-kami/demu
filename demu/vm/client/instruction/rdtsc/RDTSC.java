package vm.client.instruction.rdtsc;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class RDTSC implements Instruction{
    public void execute(VM vm){
        long time = System.currentTimeMillis();
        vm.setRegister32(EDX, time >> 32);
        vm.setRegister32(EAX, time & 0xFFFFFFFF);
        vm.addEIP(1);
    }
}