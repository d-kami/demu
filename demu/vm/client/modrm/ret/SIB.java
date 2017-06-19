package vm.client.modrm.ret;

import vm.client.VM;

import static vm.client.register.RegisterIndex.*;

public class SIB{
    protected static int getSIBSegment(VM vm, int sib, int mod){
        if((sib & 0x07) == 0x04 || (((sib & 0x07) == 0x05) && mod != 0)){
            return vm.getSegmentAddress(SS);
        }else{
            return vm.getSegmentAddress(vm.getSegmentIndex());
        }
    }
    
    protected static int getSIB(VM vm, int sib, int mod){
        int scale = 1 << ((sib >> 6) & 0x03);
        int index = (sib >> 3) & 0x07;
        int base = (int)vm.getRegister32(sib & 0x07);
        int reg = 0;

//        System.out.printf("base = %x, index = %x, scale = %x\n", sib & 0x07, (sib >> 3) & 0x07, ((sib >> 6) & 0x03));
        if((sib & 0x07) == 0x05 && mod == 0){
            base = vm.getSignedCode32(3);
        }
        
        if(index != 4){
            reg = (int)vm.getRegister32(index);
        }

        return base + reg * scale;
    }
}