package vm.client.instruction.prefix;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.*;

public class Repeat implements Instruction{
    private Instruction instruction;

    public void execute(VM vm){
        boolean isOperand = false;
        
        int code = vm.getCode8(1)& 0xFF;
        vm.addEIP(1);

        instruction = vm.getInstruction(code);

        if(code == 0x66){
            code = vm.getCode8(1)& 0xFF;
            instruction = vm.getInstruction(code);
            vm.addEIP(1);
            
            vm.setOperandPrefix(true);
            isOperand = true;
        }
        
        if(code == 0x64){
            code = vm.getCode8(1)& 0xFF;
            instruction = vm.getInstruction(code);
            vm.addEIP(1);
            
            vm.setSegmentIndex(FS);
        }
        
        int eip = (int)vm.getRegisterX(EIP);
        
        if(code == 0x6D || code == 0x6F|| code == 0xA4 || code == 0xA5 || code == 0xAA || code == 0xAB){
            if(vm.getRegisterX(ECX) == 0){
                vm.addEIP(1);
            }else{
            
                while(vm.getRegisterX(ECX) > 0){
                    vm.setRegisterX(EIP, eip);
                    instruction.execute(vm);
                    vm.setRegisterX(ECX, vm.getRegisterX(ECX) - 1);
                }
            }
        }else if(code == 0xA6){
            while(vm.getRegisterX(ECX) > 0){
                vm.setRegisterX(EIP, eip);
                instruction.execute(vm);
                vm.setRegisterX(ECX, vm.getRegisterX(ECX) - 1);
                
                if(!vm.getEFlags().isZero()){
                    break;
                }
            }
        }else{
            throw new RuntimeException("Not Implemented " + Integer.toHexString(code));
        }

        vm.setOperandPrefix(false);
        vm.setSegmentIndex(DS);
    }

    @Override
    public String toString(){
        return "REP " + instruction.toString();
    }
}