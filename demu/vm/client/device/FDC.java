package vm.client.device;

import vm.client.VM;

public class FDC{
    private int ret = 0x80;
    private int recalibrate = 0;
    private int[] result;
    private int resultIndex = 0;
    private boolean recalibrateMode = false;
    private int seekMode = 0;
    private int dmaReadMode = 0;
    private int offset;
    private int size;
    private int readMode = 0;
    
    private int track;
    private int head;
    private int sector;
    
    public void setRET(int ret){
        this.ret = ret;
    }
    
    public int in(VM vm, int port){
        if(port == 0x3f4){
            int retResult = ret;
            
            if(ret == 0x80){
                ret = 0xd0;
            }else if(ret == 0xd0){
                ret = 0x80;
            }
            
            return retResult;
        }else if(port == 0x3f5){
            int retResult = result[resultIndex];
            resultIndex++;
            
            if(resultIndex == result.length){
                ret = 0x80;
            }
            
            return retResult;
        }
        
        throw new RuntimeException("Port = " + Integer.toHexString(port));
    }
    
    public void out(VM vm, int port, int data){
        if(recalibrateMode){
            recalibrateMode = false;
            
            if(port == 0x3f5 && data == 0x00){
                vm.inputFDC(0x80);
                return;
            }
        }

        if(seekMode > 0){
            ret = 0x80;
            
            if(seekMode == 1 && data == 0){
                seekMode++;
                return;
            }else if(seekMode == 2){
                track = data;
                vm.inputFDC(0x80);
                seekMode = 0;
                
                return;
            }
        }
        
        if(dmaReadMode > 0){
            if(port == 0x04){
                if(dmaReadMode == 0x01){
                    offset &= 0xFFFFFF00;
                    offset |= (data & 0xFF);
                }else{
                    offset &= 0xFFFF00FF;
                    offset |= ((data & 0xFF) << 8);
                }
                
                dmaReadMode++;
            }else if(port == 0x05){
                if(dmaReadMode == 0x03){
                    size |= (data & 0xFF);
                }else{
                    size |= ((data & 0xFF) << 8);
                }
                
                dmaReadMode++;
            }else if(port == 0x81){
                offset &= 0xFF00FFFF;
                offset |= ((data & 0xFF) << 16);
                
                dmaReadMode = 0;
            }
        }
        
        if(readMode > 0){
            if(port == 0x3f5){
                switch(readMode){
                    case 1:
                        break;
                    
                    case 2:
                        track = (data & 0xFF);
                        break;
                    
                    case 3:
                        head = (data & 0xFF);
                        break;
                    
                    case 4:
                        sector = (data & 0xFF);
                        break;
                    case 8:
                        result = new int[7];
                        resultIndex = 0;
                        int readStart = track * 2 * 18 * 512;
                        readStart += head * 18 * 512;
                        readStart += (sector - 1) * 512;
                        int length = data;
                    
                        try{
                            vm.read(readStart, offset, 0x200);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        vm.inputFDC(0x80);
                        break;
                }
                
                readMode = (readMode + 1) % 9;
                return;
            }
        }
        
        if(port == 0x0a || port == 0x0c){
        }else if(port == 0x0b){
            if(data == 0x046){
                dmaReadMode = 1;
            }
        }else if(port == 0x3f2 || port == 0x3f7){
        }else if(port == 0x3f5){
            if(data == 0x03 || data == 0xC1 || data == 0x10){
                ret = 0x80;
            }else if(data == 0x07){
                recalibrateMode = true;
                ret = 0x80;
            }else if(data == 0x08){
                ret = 0xd0;
                result = new int[2];
                resultIndex = 0;
            }else if(data == 0x0F){
                seekMode = 1;
                ret = 0x80;
            }else if(data == 0xE6){
                readMode = 1;
                ret = 0x80;
            }else{
                throw new RuntimeException("Port = " + Integer.toHexString(port) + ", Data = " + Integer.toHexString(data));
            }
        }else if(port == 0x3F7){
            return;
        }
    }
}
