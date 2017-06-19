package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.rotate.RCLRMX;
import vm.client.instruction.rotate.SALRMX;
import vm.client.instruction.rotate.SHRRMX;
import vm.client.instruction.rotate.SARRMX;

public class ExtD1 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtD1(){
        instructions = new Instruction[8];
        
        instructions[2] = new RCLRMX();
        instructions[4] = new SALRMX();
        instructions[5] = new SHRRMX();
        instructions[7] = new SARRMX();
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