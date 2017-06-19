package vm.client.device;

import vm.client.VM;

public class Serial{
    public int in(VM vm, int port){
        if(port == 0x3f8){
            return 0;
        }else if(port == 0x3fa){
            return 0;
        }if(port == 0x3fd){
            return 0x20;
        }

        throw new RuntimeException(String.format("port = %x is Not Implemented!", port));
    }
    
    public void out(VM vm, int port, int data){
        if(0x3f8 <= port && port <= 0x3ff){
//            System.out.printf("PORT = %x, data = %x\n", port, data);
        }else{
            throw new RuntimeException(String.format("port = %x is Not Implemented!", port));
        }
    }
}
