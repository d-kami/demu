package vm.client.instruction.bios;

import java.util.Date;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.*;

public class Bios1A implements Instruction{
    public void execute(VM vm){
        int ah = vm.getRegister8High(EAX);
        int ax = vm.getRegister16(EAX);
        
        if(ah == 0){
            long current = System.currentTimeMillis();
            
            vm.setRegister16(EDX, (int)(current & 0xFFFF));
            vm.setRegister16(ECX, (int)(current >> 16) & 0xFFFF);
            vm.setRegister8Low(EAX, 1);
        }else if(ah == 0x02){
            Date date = new Date();
            int second = date.getSeconds();
            int minutes = date.getMinutes();
            int hour = date.getHours();
            
            vm.setRegister8High(ECX, hour);
            vm.setRegister8Low(ECX, minutes);
            vm.setRegister8High(EDX, second);
            vm.setRegister8Low(EDX, 0);
            vm.getEFlags().setCarry(false);
        }else if(ax == 0xB101){
            vm.setRegister16(EAX, 0x0100);
            //vm.setRegister32(EDX, 0x20494350);
        }else{
            throw new RuntimeException("Not Implemented Bios 1A AH = " + Integer.toHexString(ah));
        }
    }
}