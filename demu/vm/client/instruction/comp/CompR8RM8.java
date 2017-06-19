package vm.client.instruction.comp;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EIP;

public class CompR8RM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int r8 = modrm.getRegister8();
        int rm8 = modrm.getRMValue8();
        
        int result = r8 - rm8;
        FlagCheck.subCheck(r8, rm8, result, vm.getEFlags(), 8);

        vm.addEIP(2);
    }
}