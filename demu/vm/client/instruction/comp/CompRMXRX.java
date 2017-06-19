package vm.client.instruction.comp;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class CompRMXRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long rx = modrm.getRegisterX();

        long result = rmx - rx;
        FlagCheck.subCheck(rmx, rx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        
        vm.addEIP(2);
    }
}