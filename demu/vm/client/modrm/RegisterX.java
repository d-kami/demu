package vm.client.modrm;

import vm.client.VM;

public class RegisterX extends RM{
    private int registerIndex;

    public RegisterX(VM vm, int registerIndex){
        super(vm);
        this.registerIndex = registerIndex;
        super.size = 0;
    }

    public void set(long value){
        vm.setRegisterX(registerIndex, value);
    }

    public long get(){
        return vm.getRegisterX(registerIndex);
    }
}