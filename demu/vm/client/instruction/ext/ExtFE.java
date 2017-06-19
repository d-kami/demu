package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.inc.INCRM8;
import vm.client.instruction.dec.DECRM8;

public class ExtFE implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;
    
    public ExtFE(){
        instructions = new Instruction[8];
        
        instructions[0] = new INCRM8();
        instructions[1] = new DECRM8();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();
        instruction = instructions[code];
        
        if(instruction == null){
            throw new RuntimeException("Not Implemented " + Integer.toHexString(code));
        }
        
        instruction.execute(vm);
    }
    
    @Override
    public String toString(){
        return instruction.toString();
    }
}