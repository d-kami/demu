package vm.client.instruction.lea;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class LEA implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRegisterX(modrm.getMemoryAddress());
        
        vm.addEIP(2);
    }
}