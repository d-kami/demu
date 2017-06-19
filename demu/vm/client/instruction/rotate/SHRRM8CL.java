package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ECX;

public class SHRRM8CL implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        int cl = vm.getRegister8Low(ECX);

        int result = value >> cl;
        modrm.setRMValue8(result);
        vm.addEIP(2);
    }
}