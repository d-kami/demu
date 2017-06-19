package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ECX;

public class SHRRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();

        int result = value >> 1;
        modrm.setRMValue8(result);
        vm.addEIP(2);
    }
}