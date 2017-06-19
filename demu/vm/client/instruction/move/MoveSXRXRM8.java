package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveSXRXRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rm8 = modrm.getRMValue8();
        long rx = ((rm8 & 0x80) > 0) ? (~rm8 + 1) : rm8;

        modrm.setRegisterX(rx);
        vm.addEIP(2);
    }
}