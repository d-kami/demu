package vm.client.instruction.imul;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class IMulRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long imm = vm.getCode8(2);
        long result = rmx * imm;
        
        if(result != (int)result){
            vm.getEFlags().setOverflow(true);
            vm.getEFlags().setSign(true);
        }

        modrm.setRegisterX(result);
        vm.addEIP(3);
    }
}