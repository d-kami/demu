package vm.client.modrm;

import vm.client.VM;

public class Register8 extends RM{
    private int registerIndex;

    public Register8(VM vm, int registerIndex){
        super(vm);
        this.registerIndex = registerIndex;
        super.size = 0;
    }

    public void set(long value){
        vm.setRegister8(registerIndex, (int)value);
    }

    public long get(){
        return vm.getRegister8(registerIndex);
    }
}