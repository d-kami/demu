package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.rotate.ROLRM8Imm8;
import vm.client.instruction.rotate.RORRM8Imm8;
import vm.client.instruction.rotate.SALRM8Imm8;
import vm.client.instruction.rotate.SHRRM8Imm8;

public class ExtC0 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtC0(){
        instructions = new Instruction[8];
        
        instructions[0] = new ROLRM8Imm8();
        instructions[1] = new RORRM8Imm8();
        instructions[4] = new SALRM8Imm8();
        instructions[5] = new SHRRM8Imm8();
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