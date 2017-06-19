package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.*;

public class Bios21 implements Instruction{
    public void execute(VM vm){
        int ah = vm.getRegister8High(EAX);
        int ax = vm.getRegister16(EAX);
        
        if(ah == 0x10){
            vm.setRegister8Low(EAX, 0xFF);
        }else if(ah == 0xA2){
            vm.setRegister8Low(EAX, 0x00);
        }else{
            throw new RuntimeException("Not Implemented Bios 21 AH = " + Integer.toHexString(ah));
        }
    }
}