package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveSRegRM16 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        
        vm.setSRegisterValue(modrm.getRegisterIndex(), (int)modrm.getRMValue() & 0xFFFF);
        vm.addEIP(2);
    }
}