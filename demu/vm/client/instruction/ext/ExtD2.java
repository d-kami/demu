package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;
import vm.client.instruction.rotate.SALRM8CL;
import vm.client.instruction.rotate.SHRRM8CL;

public class ExtD2 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtD2(){
        instructions = new Instruction[8];
        
        instructions[4] = new SALRM8CL();
        instructions[5] = new SHRRM8CL();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();

        instruction = instructions[code];
        
        if(instruction == null){
            throw new RuntimeException("Not Implement Code = " + code);
        }

        instruction.execute(vm);
    }

    public String toString(){
        return instruction.toString();
    }
}