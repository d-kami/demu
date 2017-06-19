package vm.client.instruction.not;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class NotRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = ~modrm.getRMValue();
        modrm.setRMValue(value);

        vm.addEIP(2);
    }
}