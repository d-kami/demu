package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveCRXR32 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int registerIndex = modrm.getRegisterIndex();

        if(registerIndex == 0){
            int value = (int)modrm.getRMValue();
            int cr0 = vm.getCR0();
            
            if(((cr0 & 0x01) == 1) && ((value & 0x01) == 0)){
                vm.set32Flag(true);
            }
            
            vm.setCR0(value);
        }else if(registerIndex == 2){
            vm.setCR2((int)modrm.getRMValue());
        }else if(registerIndex == 3){
            vm.setCR3((int)modrm.getRMValue());
        }else if(registerIndex == 4){
            vm.setCR4((int)modrm.getRMValue());
        }else{
            throw new RuntimeException("CR" + registerIndex);
        }
        
        vm.addEIP(2);
    }
}