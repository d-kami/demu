package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class Ext82 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Ext82(){
        instructions = new Instruction[8];
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();
        instruction = instructions[code];
        
        if(instruction == null){
            throw new RuntimeException("Not Implemented = " + Integer.toHexString(code));
        }
        
        instruction.execute(vm);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}