package vm.client.instruction.sbb;

import vm.client.VM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;

public class SBBAXImmX implements Instruction{
    public void execute(VM vm){
        long ax = (int)vm.getRegisterX(EAX);
        long imm = vm.getCodeX(1);

        long result = ax - (imm + (vm.getEFlags().isCarry() ? 1 : 0));
        FlagCheck.subCheck(ax, imm, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        vm.setRegisterX(EAX, result);
        
        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}