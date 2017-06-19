package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.rotate.ROLRMXImm8;
import vm.client.instruction.rotate.RORRMXImm8;
import vm.client.instruction.rotate.SHLRMXImm8;
import vm.client.instruction.rotate.SHRRMXImm8;
import vm.client.instruction.rotate.SARRMXImm8;

public class ExtC1 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtC1(){
        instructions = new Instruction[8];
        
        instructions[0] = new ROLRMXImm8();
        instructions[1] = new RORRMXImm8();
        instructions[4] = new SHLRMXImm8();
        instructions[5] = new SHRRMXImm8();
        instructions[7] = new SARRMXImm8();
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