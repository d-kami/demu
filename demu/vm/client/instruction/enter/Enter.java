package vm.client.instruction.enter;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class Enter implements Instruction{
    public void execute(VM vm){
        int size = vm.getCode16(1) & 0xFF;
        int nest = vm.getCode8(3) & 0xFF;
        
        if(nest == 0){
            vm.pushX((int)vm.getRegisterX(EBP));
            int tempFrame = (int)vm.getRegisterX(ESP);
            
            vm.setRegisterX(EBP, tempFrame);
            vm.setRegisterX(ESP, tempFrame - size);
        }else{
            throw new RuntimeException("Enter = 0x" + Integer.toHexString(nest));
        }
        
        vm.addEIP(4);
    }
}

