package vm.client.instruction.descriptor;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ESP;

public class LGDT implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long address = modrm.getAddressOnSegment() & (vm.is32bitOperand() ? 0xFFFFFFFF : 0xFFFFF);

        int size = (int)vm.get16((int)address);
        int start = (int)vm.get32((int)address + 2) & (vm.is32bitOperand() ? 0xFFFFFFFF : 0xFFFFFF);

        vm.setGDTR(start, size);
        vm.addEIP(2);
        vm.lgdt();
    }
}