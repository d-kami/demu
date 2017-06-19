package vm.client.instruction.call;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EIP;

public class RelativeNearCall16 implements Instruction{
    public void execute(VM vm){
        vm.addCallStack((long)vm.getEIP() & 0xFFFFFFFFL);
        
        int address = vm.getSignedCodeX(1) & (vm.is32bitOperand() ? 0xFFFFFFFF: 0xFFFF);
        int addeip = vm.is32bitOperand() ? 5 : 3;
        address = (address + addeip) & (vm.is32bitOperand() ? 0xFFFFFFFF: 0xFFFF);

        vm.pushX(((int)vm.getRegister32(EIP) + addeip) & (vm.is32bitOperand() ? 0xFFFFFFFF: 0xFFFF));
        vm.addEIP(address);
    }
}