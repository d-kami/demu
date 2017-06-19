package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;
import vm.client.modrm.Memory16;
import vm.client.modrm.Memory32;

import static vm.client.register.RegisterIndex.*;

public class Mod2RM3 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Memory32(vm, vm.getSegmentAddress(vm.getSegmentIndex()), (int)vm.getRegister32(EBX) + vm.getSignedCode32(2), 4);
        }else{
            return new Memory16(vm, vm.getSegmentAddress(SS) + vm.getRegister16(EBP), vm.getRegister16(EDI) + vm.getSignedCode16(2), 2);
        }
    }
}