package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;
import vm.client.modrm.Memory16;
import vm.client.modrm.Memory32;

import static vm.client.register.RegisterIndex.*;

public class Mod2RM4 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            int count = 1;
            int sib = vm.getCode8(2);
            
            return new Memory32(vm, SIB.getSIBSegment(vm, sib, 2), SIB.getSIB(vm, sib, 2) + (int)vm.getSignedCode32(3), 4 + count);
        }else{
            return new Memory16(vm, vm.getSegmentAddress(vm.getSegmentIndex()), vm.getRegister16(ESI) + vm.getSignedCode16(2), 2);
        }
    }
}