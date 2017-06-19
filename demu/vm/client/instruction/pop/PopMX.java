package vm.client.instruction.pop;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class PopMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        vm.setX((int)modrm.getAddressOnSegment(), vm.popX());

        vm.addEIP(2);
    }
}