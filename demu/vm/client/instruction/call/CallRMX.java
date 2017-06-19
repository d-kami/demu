package vm.client.instruction.call;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EIP;

public class CallRMX implements Instruction{
    public void execute(VM vm){
        vm.addCallStack((long)vm.getEIP() & 0xFFFFFFFFL);
        
        ModRM modrm = vm.getModRM();
        long address = modrm.getRMValue();
        vm.pushX((int)vm.getRegister32(EIP) + 2);

        vm.setRegisterX(EIP, address);
    }
}