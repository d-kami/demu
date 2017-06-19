package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.mul.MulALRM8;
import vm.client.instruction.div.DIVAXRM8;
import vm.client.instruction.not.NotRM8;
import vm.client.instruction.test.TestRM8Imm8;

public class ExtF6 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;
    
    public ExtF6(){
        instructions = new Instruction[8];
        
        instructions[0] = new TestRM8Imm8();
        instructions[2] = new NotRM8();
        instructions[4] = new MulALRM8();
        instructions[6] = new DIVAXRM8();
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