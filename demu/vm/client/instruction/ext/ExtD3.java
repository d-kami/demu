package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.rotate.SALRMXCL;
import vm.client.instruction.rotate.SARRMXCL;
import vm.client.instruction.rotate.SHRRMXCL;
import vm.client.instruction.rotate.ROLRMXCL;

public class ExtD3 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtD3(){
        instructions = new Instruction[8];
        
        instructions[0] = new ROLRMXCL();
        instructions[4] = new SALRMXCL();
        instructions[5] = new SHRRMXCL();
        instructions[7] = new SARRMXCL();
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