package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ECX;

public class SARRMXCL implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int cl = vm.getRegister8Low(ECX);

        long result = value >> cl;
        modrm.setRMValue(result);
        
        vm.addEIP(2);
    }
}