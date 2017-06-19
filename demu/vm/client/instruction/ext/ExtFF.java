package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.inc.INCRMX;
import vm.client.instruction.dec.DECRMX;
import vm.client.instruction.call.CallRMX;
import vm.client.instruction.call.FarCallMX;
import vm.client.instruction.jump.JumpRMX;
import vm.client.instruction.jump.FarJumpMX;
import vm.client.instruction.push.PushRMX;

public class ExtFF implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtFF(){
        instructions = new Instruction[8];
        
        instructions[0] = new INCRMX();
        instructions[1] = new DECRMX();
        instructions[2] = new CallRMX();
        instructions[3] = new FarCallMX();
        instructions[4] = new JumpRMX();
        instructions[5] = new FarJumpMX();
        instructions[6] = new PushRMX();
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