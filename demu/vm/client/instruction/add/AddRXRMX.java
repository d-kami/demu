package vm.client.instruction.add;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class AddRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rx = modrm.getRegisterX();
        long rmx = modrm.getRMValue();

        long result = rx + rmx;
        FlagCheck.addCheck(rx, rmx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRegisterX(result);

        vm.addEIP(2);
    }
}