package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;
import vm.client.modrm.Memory16;
import vm.client.modrm.Memory32;

import static vm.client.register.RegisterIndex.*;

public class Mod0RM3 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Memory32(vm, vm.getSegmentAddress(vm.getSegmentIndex()), (int)vm.getRegister32(EBX), 0);
        }else{
            int svalue = vm.getSegmentIndex() == DS ? vm.getSegmentAddress(SS) : vm.getSegmentAddress(vm.getSegmentIndex());
            return new Memory16(vm, svalue, vm.getRegister16(EBP) + vm.getRegister16(EDI), 0);
        }
    }
}