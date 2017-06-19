package vm.client.instruction.loop;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.ECX;

public class Loop implements Instruction{
    public void execute(VM vm){
        long cx = vm.getRegisterX(ECX) - 1;
        vm.setRegisterX(ECX, cx);
        
        if(cx != 0){
            vm.addEIP(vm.getSignedCode8(1));
        }
        
        vm.addEIP(2);
    }
}
