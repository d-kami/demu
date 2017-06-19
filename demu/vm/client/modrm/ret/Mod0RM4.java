package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;
import vm.client.modrm.Memory16;
import vm.client.modrm.Memory32;

import static vm.client.register.RegisterIndex.*;

public class Mod0RM4 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            int count = 1;
            int sib = vm.getCode8(2);
            
            if((sib & 0x07) == 0x05){
                count += 4;
            }
            
            return new Memory32(vm, SIB.getSIBSegment(vm, sib, 0), SIB.getSIB(vm, sib, 0), count);
        }else{
            return new Memory16(vm, vm.getSegmentAddress(vm.getSegmentIndex()), vm.getRegister16(ESI), 0);
        }
    }
}