package vm.client.instruction.prefix;

import vm.client.VM;
import vm.client.instruction.Instruction;

public class AddressPrefix implements Instruction{
    Instruction instruction;

    public void execute(VM vm){
        int code = vm.getCode8(1) & 0xFF;

        vm.setAddressPrefix(true);
        vm.addEIP(1);

        instruction = vm.getInstruction(code);
        instruction.execute(vm);

        vm.setAddressPrefix(false);
    }

    @Override
    public String toString(){
        return "Address " + instruction.toString();
    }
}