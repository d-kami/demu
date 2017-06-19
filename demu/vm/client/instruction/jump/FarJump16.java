package vm.client.instruction.jump;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class FarJump16 implements Instruction{
    public void execute(VM vm){
        int address = 0;
        int cs = 0;
        
        if(vm.is32bitOperand()){
            address = (int)vm.getCode32(1) & 0xFFFFFFFF;
            cs = vm.getCode16(5) & 0xFFFF;
        }else{
            address = vm.getCode16(1) & 0xFFFF;
            cs = vm.getCode16(3) & 0xFFFF;
        }
        
        vm.set32Flag(false);
        vm.setRegister32(EIP, address);
        vm.setRegister16(CS, cs);
    }
}