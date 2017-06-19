package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import vm.client.instruction.fpu.FNInit;
import vm.client.instruction.fpu.FSETPM;

public class ExtDB implements Instruction{
    private Instruction instruction;
    private Instruction[] instructions;

    public ExtDB(){
        instructions = new Instruction[256];
        
        instructions[0xE3] = new FNInit();
        instructions[0xE4] = new FSETPM();
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