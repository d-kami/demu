package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.*;

public class Bios16 implements Instruction{
    public void execute(VM vm){
        int ah = vm.getRegister8High(EAX);
        
        if(ah == 0x00){
            int key;
            
            while((key = vm.getInputKey()) == 0){
                try{
                    Thread.sleep(50);
                }catch(Exception e){
                    
                }
            }
            
            if(key == 0x0a){
                vm.setRegister16(EAX, 0x1c0d);
                vm.setInputKey(0);
            }else if(0x70 <= key && key <= 0x79){
                int code = 0x3b + (key - 0x70);
                vm.setRegister8High(EAX, code);
                vm.setRegister8Low(EAX, 0);
                
                vm.setInputKey(0);
            }else{
                vm.setRegister8High(EAX, 0);
                vm.setRegister8Low(EAX, key);
                vm.setInputKey(0);
            }
        }else if(ah == 0x01){
            int key = vm.getInputKey();

            if(key == 0){
                vm.getEFlags().setZero(true);
            }else{
                vm.getEFlags().setZero(false);
                
                if(key == 0x0a){
                    vm.setRegister16(EAX, 0x1c0d);
                }else if(0x70 <= key && key <= 0x79){
                    int code = 0x3b + (key - 0x70);
                    vm.setRegister8High(EAX, code);
                    vm.setRegister8Low(EAX, 0);
                }else{
                    vm.setRegister8High(EAX, 0);
                    vm.setRegister8Low(EAX, key);
                }
            }
        }else if(ah == 0x02){
            
        }else if(ah == 0x03){
            vm.setRegister16(EBX, 0);
        }else{
            throw new RuntimeException("Not Implemented AH = " + Integer.toHexString(ah));
        }
    }
}