package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;
import vm.client.modrm.Memory16;
import vm.client.modrm.Memory32;

import static vm.client.register.RegisterIndex.*;

public class Mod1RM2 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Memory32(vm, vm.getSegmentAddress(vm.getSegmentIndex()), (int)(vm.getRegister32(EDX) + vm.getSignedCode8(2)), 1);
        }else{
            return new Memory16(vm, vm.getSegmentAddress(SS), vm.getRegister16(EBP) + vm.getRegister16(ESI) + vm.getSignedCode8(2), 1);
        }
    }
}