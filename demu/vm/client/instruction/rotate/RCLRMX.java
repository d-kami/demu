package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class RCLRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int imm = 1;
        
        if(vm.getEFlags().isCarry()){
            value |= vm.is32bitOperand() ? 1 << 31 : 1 << 15;
        }

        int shift = vm.is32bitOperand() ? 32 : 16;

        value <<= imm;
        value |= (value >> shift) & ((1 << imm) - 1);
        value &= ((long)1 << shift) - 1;

        modrm.setRMValue(value & (vm.is32bitOperand() ? 0xFFFFFFFFL : 0xFFFF));
        vm.getEFlags().setCarry(value > (vm.is32bitOperand() ?0xFFFFFFFFL : 0xFFFF));
        vm.addEIP(2);
    }
}