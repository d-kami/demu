package vm.client.modrm.ret;

import vm.client.VM;
import vm.client.modrm.RM;

public class ModParser{
    private static final RMReturn[][] returns;

    static{
        returns = new RMReturn[5][];

        returns[0] = new RMReturn[8];
        returns[0][0] = new Mod0RM0();
        returns[0][1] = new Mod0RM1();
        returns[0][2] = new Mod0RM2();
        returns[0][3] = new Mod0RM3();
        returns[0][4] = new Mod0RM4();
        returns[0][5] = new Mod0RM5();
        returns[0][6] = new Mod0RM6();
        returns[0][7] = new Mod0RM7();

        returns[1] = new RMReturn[8];
        returns[1][0] = new Mod1RM0();
        returns[1][1] = new Mod1RM1();
        returns[1][2] = new Mod1RM2();
        returns[1][3] = new Mod1RM3();
        returns[1][4] = new Mod1RM4();
        returns[1][5] = new Mod1RM5();
        returns[1][6] = new Mod1RM6();
        returns[1][7] = new Mod1RM7();

        returns[2] = new RMReturn[8];
        returns[2][0] = new Mod2RM0();
        returns[2][1] = new Mod2RM1();
        returns[2][2] = new Mod2RM2();
        returns[2][3] = new Mod2RM3();
        returns[2][4] = new Mod2RM4();
        returns[2][5] = new Mod2RM5();
        returns[2][6] = new Mod2RM6();
        returns[2][7] = new Mod2RM7();

        returns[3] = new RMReturn[8];
        returns[4] = new RMReturn[8];

        for(int i = 0; i < returns[3].length; i++){
            returns[3][i] = new Mod3RM(i);
            returns[4][i] = new Mod3RM_8(i);
        }
    }

    public static RM parse(VM vm, int mod, int rm, boolean addeip){
        boolean flag = false;
        if(mod == 4 && vm.is32bitAddress()){
            vm.setAddressPrefix(true);
            flag = true;
        }
        
        RM regmem = returns[mod][rm].getRM(vm);

        if(addeip){
            vm.addEIP(regmem.getSize());
        }
        
        if(flag){
            vm.setAddressPrefix(false);
        }

        return regmem;
    }
}