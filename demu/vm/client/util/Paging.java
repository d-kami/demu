package vm.client.util;

import vm.client.VM;

public class Paging{
    public static int getAddress(VM vm, int src){
        if(!vm.usePaging()){
            return src;
        }
        
        int directoryIndex = (src >> 22) & 0x3FF;
        int directoryEntry = vm.get32((vm.getCR3() & 0xFFFFF000) | (directoryIndex * 4));

        if((directoryEntry & 1) == 0){
            vm.setCR2(src & 0xFFFFFFFF);
            vm.swi(0x0E);
        }
        
        if(((vm.getCR4() & 0x10) == 0x10) && ((directoryEntry & 0x80) == 0x80)){
            int pagePointer =  directoryEntry & 0xFFC00000;
            int pageIndex = src & 0x3FFFFF;

            return (pagePointer | pageIndex);
        }

        int tableIndex = (src >> 12) & 0x3FF;
        int tablePointer = directoryEntry & 0xFFFFF000;
        int tableEntry = vm.get32(tablePointer | (tableIndex * 4));

        if((tableEntry & 1) == 0){
            vm.setCR2(src & 0xFFFFFFFF);
            vm.swi(0x0E);
        }
        
        int pageIndex = src & 0xFFF;
        int pagePointer = tableEntry & 0xFFFFF000;
        
        return (pagePointer | pageIndex);
    }
}
