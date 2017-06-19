package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveSXR32RM16 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rm = modrm.getRMValue() & 0xFFFF;
        long rx = ((rm & 0x8000) > 0) ? (~rm + 1) : rm;

        modrm.setRegister32(rx);
        vm.addEIP(2);
    }
}