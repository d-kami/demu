package vm.client.instruction.call;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class FarCallMX implements Instruction{
    public void execute(VM vm){
        int address = (int)vm.getModRM().getAddressOnSegment();
        int cs = vm.get16(address + (vm.is32bitAddress() ? 4 : 2));
        int eip = vm.getX(address);

        vm.pushX(vm.getRegister16(CS));
        vm.pushX((int)vm.getRegister32(EIP) + (vm.is32bitOperand() ? 7 : 5));

        vm.setRegister32(EIP, eip);
        vm.setRegister16(CS, cs);
    }
}