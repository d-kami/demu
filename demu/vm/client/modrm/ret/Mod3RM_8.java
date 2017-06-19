package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;
import vm.client.modrm.Register8;

public class Mod3RM_8 implements RMReturn{
    private int rm;

    public Mod3RM_8(int rm){
        this.rm = rm;
    }

    public RM getRM(VM vm){
        return new Register8(vm, rm);
    }
}