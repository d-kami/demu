package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.mul.MulAXRMX;
import vm.client.instruction.neg.NegRMX;
import vm.client.instruction.not.NotRMX;
import vm.client.instruction.imul.IMulAXRMX;
import vm.client.instruction.test.TestRMXImmX;
import vm.client.instruction.div.DIVAXRMX;
import vm.client.instruction.div.IDivRMX;

public class ExtF7 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;
    
    public ExtF7(){
        instructions = new Instruction[8];
        
        instructions[0] = new TestRMXImmX();
        instructions[2] = new NotRMX();
        instructions[3] = new NegRMX();
        instructions[4] = new MulAXRMX();
        instructions[5] = new IMulAXRMX();
        instructions[6] = new DIVAXRMX();
        instructions[7] = new IDivRMX();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();
        instruction = instructions[code];
        
        if(instruction == null){
            throw new RuntimeException("Not Implemented " + Integer.toHexString(code));
        }
        
        instruction.execute(vm);
    }
    
    @Override
    public String toString(){
        return instruction.toString();
    }
}