package vm.client.instruction.xor;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class XorAXImmX implements Instruction{
    public void execute(VM vm){
        long ax = vm.getRegisterX(EAX);
        long imm = vm.getCodeX(1);
        
        long result = ax ^ imm;
        FlagCheck.check(ax, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        vm.setRegisterX(EAX, result);

        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}