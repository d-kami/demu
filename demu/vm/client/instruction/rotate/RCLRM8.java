package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class RCLRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int imm = 1;
        
        if(vm.getEFlags().isCarry()){
            value |= 1 << 8;
        }

        int shift = 8;

        value <<= imm;
        value |= (value >> shift) & ((1 << imm) - 1);
        value &= ((long)1 << shift) - 1;

        modrm.setRMValue(value & 0xFF);
        vm.getEFlags().setCarry(value > 0xFF);
        vm.addEIP(2);
    }
}