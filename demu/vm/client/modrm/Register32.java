package vm.client.modrm;

import vm.client.VM;

public class Register32 extends RM{
    private int registerIndex;

    public Register32(VM vm, int registerIndex){
        super(vm);
        this.registerIndex = registerIndex;
        super.size = 0;
    }

    public void set(long value){
        vm.setRegister32(registerIndex, value);
    }

    public long get(){
        return vm.getRegister32(registerIndex);
    }
}