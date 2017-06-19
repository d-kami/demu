package vm.client.modrm;

import vm.client.VM;

public class Register16 extends RM{
    private int registerIndex;

    public Register16(VM vm, int registerIndex){
        super(vm);
        this.registerIndex = registerIndex;
        super.size = 0;
    }

    public void set(long value){
        vm.setRegisterX(registerIndex, (int)value);
    }

    public long get(){
        return vm.getRegisterX(registerIndex);
    }
}