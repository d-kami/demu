package vm.client.instruction.not;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class NotRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        modrm.setRMValue8(~value);

        vm.addEIP(2);
    }
}