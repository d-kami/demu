package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.or.OrRMXImm8;
import vm.client.instruction.adc.AdcRMXImm8;
import vm.client.instruction.add.AddRMXImm8;
import vm.client.instruction.and.AndRMXImm8;
import vm.client.instruction.sbb.SBBRMXImm8;
import vm.client.instruction.sub.SubRMXImm8;
import vm.client.instruction.comp.CompRMXImm8;

public class Ext83 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Ext83(){
        instructions = new Instruction[8];
        
        instructions[0] = new AddRMXImm8();
        instructions[1] = new OrRMXImm8();
        instructions[2] = new AdcRMXImm8();
        instructions[3] = new SBBRMXImm8();
        instructions[4] = new AndRMXImm8();
        instructions[5] = new SubRMXImm8();
        instructions[7] = new CompRMXImm8();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();
        instruction = instructions[code];
        
        if(instruction == null){
            throw new RuntimeException("Not Implemented = " + Integer.toHexString(code));
        }
        
        instruction.execute(vm);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}