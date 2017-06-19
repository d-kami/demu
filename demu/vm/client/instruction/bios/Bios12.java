package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.*;

public class Bios12 implements Instruction{
    public void execute(VM vm){
        vm.setRegister16(EAX, 0x27f);
    }
}