package vm.client.instruction.prefix;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.DS;

public class SegmentPrefix implements Instruction{
    private int sindex;
    private Instruction instruction;

    public SegmentPrefix(int sindex){
        this.sindex = sindex;
    }

    public void execute(VM vm){
        int code = vm.getCode8(1);

        vm.setSegmentIndex(sindex);
        vm.addEIP(1);

        instruction = vm.getInstruction(code);
        instruction.execute(vm);

        vm.setSegmentIndex(DS);
    }

    @Override
    public String toString(){
        return "" + sindex + ": " + instruction.toString();
    }
}