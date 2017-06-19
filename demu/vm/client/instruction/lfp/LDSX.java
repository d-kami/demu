package vm.client.instruction.lfp;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.DS;
import static vm.client.register.RegisterIndex.SS;

public class LDSX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int address = (int)modrm.getAddressOnSegment();

        int ds = vm.get16(address + (vm.is32bitOperand() ? 4 : 2));
        long register = vm.getX(address);

        vm.setRegister16(DS, ds);
        modrm.setRegisterX(register);
        
        vm.addEIP(2);
    }
}
