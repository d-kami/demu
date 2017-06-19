package vm.client.instruction.test;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

public class TestRMXImmX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long src = modrm.getRMValue();
        long dest = vm.getCodeX(2) & 0xFFFFFFFFL;

        long result = src & dest;
        int size = vm.is32bitOperand() ? 32 : 16;
        FlagCheck.check(src, result, vm.getEFlags(), size);

        if(vm.is32bitOperand()){
            vm.addEIP(6);
        }else{
            vm.addEIP(4);
        }
    }
}