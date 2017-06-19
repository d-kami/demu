package vm.client.instruction.cpuid;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EBX;
import static vm.client.register.RegisterIndex.ECX;
import static vm.client.register.RegisterIndex.EDX;

public class CPUID implements Instruction{
    public void execute(VM vm){
        int eax = (int)vm.getRegisterX(EAX);
        
        if(eax == 0x00){
            int ebx = ((byte)'a' << 24) | ((byte)'k' << 16) | ((byte)'-' << 8) | (byte)'d';
            int edx = ((byte)'C' << 24) | ((byte)' ' << 16) | ((byte)'i' << 8) | (byte)'m';
            int ecx = 0 << 24 | 0 << 16 | ((byte)'U' << 8) | (byte)'P';

            vm.setRegister32(EAX, 0x0);
            vm.setRegister32(EBX, ebx);
            vm.setRegister32(ECX, ecx);
            vm.setRegister32(EDX, edx);
        }else if(eax == 0x01){
            vm.setRegister32(EAX, 0xF61);
            vm.setRegister32(EDX, 0xF61);
        }else{
            throw new RuntimeException("Not Implemented " + Integer.toHexString(eax));
        }
        
        vm.addEIP(1);
    }
}
