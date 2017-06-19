package vm.client.instruction.move;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class MoveR32CRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM(false);
        int registerIndex = modrm.getRegisterIndex();
        
        if(registerIndex == 0){
            modrm.setRMValue(vm.getCR0());
        }else if(registerIndex == 2){
            modrm.setRMValue(vm.getCR2());
        }else if(registerIndex == 3){
            modrm.setRMValue(vm.getCR3());
        }else if(registerIndex == 4){
            modrm.setRMValue(vm.getCR4());
        }else{
            throw new RuntimeException("Register CR" + registerIndex + " is Not Implemented");
        }

        vm.addEIP(2);
    }
}