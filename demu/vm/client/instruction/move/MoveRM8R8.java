package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveRM8R8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRMValue8(modrm.getRegister8());
        vm.addEIP(2);
    }
}