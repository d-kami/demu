package vm.client.instruction.ret;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EIP;

public class NearReturn implements Instruction{
    public void execute(VM vm){
        int eip = vm.popX();
        
        vm.setRegisterX(EIP, eip);
        vm.removeCallStack();
    }
}