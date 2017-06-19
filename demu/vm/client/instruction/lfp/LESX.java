package vm.client.instruction.lfp;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ES;
import static vm.client.register.RegisterIndex.SS;

public class LESX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int address = (int)modrm.getAddressOnSegment();

        int es = vm.get16(address + (vm.is32bitOperand() ? 4 : 2));
        long register = vm.getX(address);

        vm.setRegister16(ES, es);
        modrm.setRegisterX(register);
        
        vm.addEIP(2);
    }
}