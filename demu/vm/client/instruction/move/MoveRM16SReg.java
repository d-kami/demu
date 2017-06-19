package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveRM16SReg implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int index = modrm.getRegisterIndex();
        int value = vm.getSRegisterValue(index);
        
        modrm.setRMValue(value);
        vm.addEIP(2);
    }
}