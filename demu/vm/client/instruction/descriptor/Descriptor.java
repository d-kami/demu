package vm.client.instruction.descriptor;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class Descriptor implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Descriptor(){
        instructions = new Instruction[8];

        instructions[2] = new LGDT();
        instructions[3] = new LIDT();
        instructions[6] = new LMSW();
    }

    public void execute(VM vm){
        ModRM modrm = vm.getModRM(false);
        int opecode = modrm.getOpecode();
        instruction = instructions[opecode];
        
        if(instruction == null){
            throw new RuntimeException("Not Implemented = " + Integer.toHexString(opecode));
        }
        
        instruction.execute(vm);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}