package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class FarJumpMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int address = (int)modrm.getAddressOnSegment();

        int cs = vm.get16(address + (vm.is32bitOperand() ? 4 : 2));
        int eip = vm.getX(address) & 0xFFFFFFFF;
        
        vm.setRegister32(EIP, eip);
        vm.setRegister16(CS, cs);
    }
}