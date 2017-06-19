package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import vm.client.instruction.fpu.FNSTSWAX;

public class ExtDF implements Instruction{
    private Instruction instruction;
    private Instruction[] instructions;

    public ExtDF(){
        instructions = new Instruction[256];
        
        instructions[0xE0] = new FNSTSWAX();
    }
    
    public void execute(VM vm){
        int code = vm.getCode8(1) & 0xFF;

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