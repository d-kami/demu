package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import vm.client.instruction.or.OrRMXImmX;
import vm.client.instruction.add.AddRMXImmX;
import vm.client.instruction.and.AndRMXImmX;
import vm.client.instruction.sub.SubRMXImmX;
import vm.client.instruction.comp.CompRMXImmX;

public class Ext81 implements Instruction{
    private Instruction instruction;
    private Instruction[] instructions;

    public Ext81(){
        instructions = new Instruction[8];
        
        instructions[0] = new AddRMXImmX();
        instructions[1] = new OrRMXImmX();
        instructions[4] = new AndRMXImmX();
        instructions[5] = new SubRMXImmX();
        instructions[7] = new CompRMXImmX();
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