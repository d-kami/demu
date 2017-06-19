package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;
import vm.client.modrm.Memory16;
import vm.client.modrm.Memory32;

import static vm.client.register.RegisterIndex.*;

public class Mod1RM4 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            int count = 1;
            int sib = vm.getCode8(2);
            
            return new Memory32(vm, SIB.getSIBSegment(vm, sib, 1), SIB.getSIB(vm, sib, 1) + vm.getSignedCode8(3), 1 + count);
        }else{
            return new Memory16(vm, vm.getSegmentAddress(vm.getSegmentIndex()), vm.getRegister16(ESI) + vm.getSignedCode8(2), 1);
        }
    }
}