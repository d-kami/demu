package vm.client.instruction.div;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.EAX;
import static vm.client.register.RegisterIndex.EDX;

public class IDivRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();

        if(value == 0){
            throw new RuntimeException("Divide Error: Divide 0!");
        }

        long eax = vm.getRegisterX(EAX);
        int edx = (int)vm.getRegisterX(EDX);
        
        long divsrc = (edx << 32) | eax & 0xFFFFFFFFL;

        long result = divsrc / value;
        long mod = divsrc % value;

        if(vm.is32bitOperand()){
            if(result > 0xFFFFFFFFL){
                throw new RuntimeException("Divide Error: result > 0xFFFFFFFF");
            }
        }else{
            if(result > 0xFFFF){
                throw new RuntimeException("Divide Error: result > 0xFFFF");
            }
        }

        vm.setRegisterX(EAX, result);
        vm.setRegisterX(EDX, mod);

        vm.addEIP(2);
    }
}