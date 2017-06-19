package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.rotate.ROLRM8;
import vm.client.instruction.rotate.RCLRM8;
import vm.client.instruction.rotate.RORRM8;
import vm.client.instruction.rotate.SALRM8;
import vm.client.instruction.rotate.SHRRM8;

public class ExtD0 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtD0(){
        instructions = new Instruction[8];
        
        instructions[0] = new ROLRM8();
        instructions[1] = new RORRM8();
        instructions[2] = new RCLRM8();
        instructions[4] = new SALRM8();
        instructions[5] = new SHRRM8();
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