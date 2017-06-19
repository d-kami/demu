package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.view.VMView;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class Bios29 implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        
        VMView view = vm.getView();
        view.putCharacter((byte)al);
    }
}