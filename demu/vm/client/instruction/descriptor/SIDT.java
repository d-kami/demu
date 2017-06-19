package vm.client.instruction.descriptor;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.ESP;

public class SIDT implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long address = modrm.getAddressOnSegment() & (vm.is32bitOperand() ? 0xFFFFFFFF : 0xFFFF);

        int start = vm.getIDTR().getStart();
        int size = vm.getIDTR().getSize();
        
        vm.set16((int)address, size);
        vm.set32((int)address + 2, start);
        
        vm.addEIP(2);
    }
}