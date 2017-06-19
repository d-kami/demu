package vm.client.modrm;

import vm.client.VM;
import vm.client.util.Paging;
import vm.client.modrm.ret.ModParser;
import static vm.client.register.RegisterIndex.*;

public class ModRM{
    private int mod;
    private int rm;
    private int registerIndex;
    private VM vm;
    private RM regmem;

    public ModRM(int modrm, VM vm, boolean addeip){
        mod = ModRM.getMod(modrm);
        rm = ModRM.getRM(modrm);
        regmem = ModParser.parse(vm, mod, rm, addeip);
        registerIndex = ModRM.getRegisterIndex(modrm);
        this.vm = vm;
    }

    public int getRMValue8(){
        if(mod == 3){
            return (int)ModParser.parse(vm, 4, rm, false).get();
        }

        return (int)regmem.get8();
    }

    public void setRMValue8(int value){
        if(mod == 3){
            ModParser.parse(vm, 4, rm, false).set(value);
        }else{
            regmem.set8(value);
        }
    }

    public long getRMValue(){
        return regmem.get();
    }

    public void setRMValue(long value){
        regmem.set(value);
    }

    public int getRegister8(){
        return vm.getRegister8(registerIndex);
    }

    public int getRegister16(){
        return vm.getRegister16(registerIndex);
    }

    public long getRegister32(){
        return vm.getRegister32(registerIndex);
    }

    public long getRegisterX(){
        return vm.getRegisterX(registerIndex);
    }

    public void setRegister8(int value){
        vm.setRegister8(registerIndex, value);
    }

    public void setRegister16(int value){
        vm.setRegister16(registerIndex, value);
    }

    public void setRegister32(long value){
        vm.setRegister32(registerIndex, value);
    }

    public void setRegisterX(long value){
        vm.setRegisterX(registerIndex, value);
    }

    public long getMemoryAddress(){
        if(regmem instanceof Memory32){
            return ((Memory32)regmem).getAddress();
        }else{
            return ((Memory16)regmem).getAddress();
        }
    }
    
    public long getAddressOnSegment(){
        if(regmem instanceof Memory32){
            return Paging.getAddress(vm, (int)((Memory32)regmem).getAddressOnSegment());
        }else{
            return (int)((Memory16)regmem).getAddressOnSegment();
        }
    }

    public int getMod(){
        return mod;
    }

    public int getRM(){
        return rm;
    }

    public int getRegisterIndex(){
        return registerIndex;
    }

    public int getOpecode(){
        return registerIndex;
    }

    public static int getMod(int modrm){
        return ((modrm & 0xC0) >> 6) & 0x03;
    }

    public static int getRegisterIndex(int modrm){
        return ((modrm & 0x38) >> 3) & 0x07;
    }

    public static int getRM(int modrm){
        return (modrm & 0x07);
    }
}