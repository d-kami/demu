package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EIP;

public class JumpRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long address = modrm.getRMValue();
        
        System.out.printf("%x\n", address);

        vm.setRegisterX(EIP, address);
    }
}