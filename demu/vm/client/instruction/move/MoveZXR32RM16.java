package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveZXR32RM16 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue() & 0xFFFF;
        
        modrm.setRegister32(value);
        vm.addEIP(2);
    }
}