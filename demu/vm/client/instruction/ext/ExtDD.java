package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import vm.client.instruction.fpu.FRStore;
import vm.client.instruction.fpu.FNSave;

public class ExtDD implements Instruction{
    private Instruction instruction;
    private Instruction[] instructions;

    public ExtDD(){
        instructions = new Instruction[8];
        
        instructions[4] = new FRStore();
        instructions[6] = new FNSave();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();

        instruction = instructions[code];
        
        if(instruction == null){
            throw new RuntimeException("Not Implemented = " + Integer.toHexString(code));
        }
        
        instruction.execute(vm);
    }

    public String toString(){
        return instruction.toString();
    }
}