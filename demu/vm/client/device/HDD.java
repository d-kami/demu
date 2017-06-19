package vm.client.device;

import vm.client.VM;
import java.io.IOException;

public class HDD{
    private int count = 0;
    private int offset = 0;
    private int index = 0;
    private boolean isInterrupt= false;
    private boolean isDisk0;

    public int in(VM vm, int port){
        if(port == 0x1f0){
            if(!isDisk0){
                return 0;
            }
            
            int ret = (int)vm.get32(index);
            index += 4;

            return ret;
        }else if(port == 0x1f7){
            return 0x40;
        }

        throw new RuntimeException(String.format("port = %x is Not Implemented!", port));
    }
    
    public void out(VM vm, int port, int data){
        if(port == 0x1f0){
            try{
                vm.writeDisk2(data, vm.is32bitOperand() ? 4 : 2);
            }catch(IOException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }else if(port == 0x1f2){
            count = data;
        }else if(port == 0x1f3){
            offset &= 0xFFFFFF00;
            offset |= (data & 0xFF);
        }else if(port == 0x01f4){
            offset &= 0xFFFF00FF;
            offset |= (data & 0xFF) << 8;
        }else if(port == 0x1f5){
            offset &= 0xFF00FFFF;
            offset |= (data & 0xFF) << 16;
        }else if(port == 0x1f6){
            offset &= 0x00FFFFFF;
            offset |= ((data & 0x0F) << 24);
            
            if((data & 0x10) == 0x10){
                isDisk0 = false;
            }else{
                isDisk0 = true;
            }
        }else if(port == 0x1f7){
            try{
                if(data == 0x20){
                    if(isDisk0){
                        vm.read(offset * 0x200, 0x0, 0x200 * count);
                    }else{
                        vm.readDisk2(offset * 0x200, 0x0, 0x200 * count);
                        
                        System.out.printf("Offset = %x, count = %d\n", offset, count);
                    }
                    
                    index = 0;

                    if(isInterrupt){
                        vm.inputHDD();
                    }
                }else{
                    vm.setDisk2Offset(offset * 0x200, 0x200 * count);
                }
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }else if(port == 0x3f6){
            if((data & 0x02) == 0x00){
                isInterrupt = true;
            }
        }else{
            throw new RuntimeException(String.format("port = %x is Not Implemented!", port));
        }
    }
}
