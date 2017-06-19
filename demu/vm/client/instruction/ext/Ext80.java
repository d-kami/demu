package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

import vm.client.instruction.adc.AdcRM8Imm8;
import vm.client.instruction.add.AddRM8Imm8;
import vm.client.instruction.and.AndRM8Imm8;
import vm.client.instruction.or.OrRM8Imm8;
import vm.client.instruction.sub.SubRM8Imm8;
import vm.client.instruction.comp.CompRM8Imm8;

public class Ext80 implements Instruction{
    private Instruction instruction;
    private Instruction[] instructions;

    public Ext80(){
        instructions = new Instruction[8];
        
        instructions[0] = new AddRM8Imm8();
        instructions[1] = new OrRM8Imm8();
        instructions[2] = new AdcRM8Imm8();
        instructions[4] = new AndRM8Imm8();
        instructions[5] = new SubRM8Imm8();
        instructions[7] = new CompRM8Imm8();
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