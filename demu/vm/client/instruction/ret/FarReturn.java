package vm.client.instruction.ret;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.CS;
import static vm.client.register.RegisterIndex.EIP;

public class FarReturn implements Instruction{
    public void execute(VM vm){
        int eip = vm.popX();
        int cs = vm.popX();

        vm.setRegister16(CS, cs);
        vm.setRegisterX(EIP, eip);
    }
}
