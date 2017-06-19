package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveZXRXRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        
        int value = modrm.getRMValue8() & 0xFF;
        modrm.setRegisterX(value);

        vm.addEIP(2);
    }
}
