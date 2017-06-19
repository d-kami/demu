package vm.client.instruction.descriptor;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import vm.client.instruction.task.LTR;
import vm.client.instruction.ver.VERW;

public class Descriptor0 implements Instruction{
    private Instruction[] instructions;

    public Descriptor0(){
        instructions = new Instruction[8];

        instructions[1] = new SIDT();
        instructions[2] = new LLDT();
        instructions[3] = new LTR();
        instructions[5] = new VERW();
    }

    public void execute(VM vm){
        int opecode = vm.getModRM(false).getOpecode();
        Instruction instruction = instructions[opecode];
        
        if(instruction == null){
            throw new RuntimeException("Opecode = " + opecode + " is Not Implemented");
        }
        
        instruction.execute(vm);
    }
}