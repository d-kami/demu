package vm.client.instruction.descriptor;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.ESP;

public class LIDT implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long address = modrm.getAddressOnSegment() & (vm.is32bitOperand() ? 0xFFFFFFFF : 0xFFFF);

        int size = (int)vm.get16((int)address);
        int start = (int)vm.getX((int)address + 2);

        vm.setIDTR(start, size);
        vm.addEIP(2);
        vm.lidt();
    }
}