package vm.client.instruction.rotate;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.ECX;

public class ROLRMXCL implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();
        int cl = vm.getRegister8Low(ECX);

        int shift = vm.is32bitOperand() ? 32 : 16;

        value <<= cl;
        value |= (value >> shift) & ((1 << cl) - 1);
        value &= ((long)1 << shift) - 1;

        modrm.setRMValue(value);
        vm.addEIP(2);
    }
}