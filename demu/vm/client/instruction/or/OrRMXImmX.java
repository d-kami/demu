package vm.client.instruction.or;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class OrRMXImmX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long immx = vm.getCodeX(2);

        long result = rmx | immx;
        FlagCheck.check(rmx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRMValue(result);

        vm.addEIP(vm.is32bitOperand() ? 6 : 4);
    }
}