package vm.client.instruction;

import vm.client.VM;

public interface Instruction{
    void execute(VM vm);
}