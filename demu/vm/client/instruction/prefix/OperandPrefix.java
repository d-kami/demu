package vm.client.instruction.prefix;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class OperandPrefix implements Instruction{
    private Instruction instruction;

    public void execute(VM vm){
        int code = vm.getCode8(1) & 0xFF;

        vm.setOperandPrefix(true);
        vm.addEIP(1);

        instruction = vm.getInstruction(code);
        instruction.execute(vm);

        vm.setOperandPrefix(false);
    }

    @Override
    public String toString(){
        return "Operand " + instruction.toString();
    }
}