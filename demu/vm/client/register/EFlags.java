package vm.client.register;

public class EFlags{
    private int eflags;

    public void set(int eflags){
        this.eflags = eflags;
    }

    public int get(){
        return eflags;
    }

    public void setCarry(boolean isCarry){
        int carryFlag = 0x01;
        
        if(isCarry){
            eflags |= carryFlag;
        }else{
            eflags &= ~carryFlag;
        }
    }

    public boolean isCarry(){
        return (eflags & 0x01) == 0x01;
    }

    public void setParity(boolean isParity){
        int parityFlag = 0x01 << 0x02;
        
        if(isParity){
            eflags |= parityFlag;
        }else{
            eflags &= ~parityFlag;
        }
    }

    public boolean isParity(){
        int cparity = (eflags >> 0x02) & 0x01;
        return cparity == 0x01;
    }

    public void setAdjust(boolean isAdjust){
        int adjustFlag = 1 << 0x04;
        
        if(isAdjust){
            eflags |= adjustFlag;
        }else{
            eflags &= ~adjustFlag;
        }
    }

    public boolean isAdjust(){
        int cadjust = (eflags >> 0x04) & 0x01;
        return cadjust == 1;
    }

    public void setZero(boolean isZero){
        int zeroFlag = 0x01 << 0x06;
        
        if(isZero){
            eflags |= zeroFlag;
        }else{
            eflags &= ~zeroFlag;
        }
    }

    public boolean isZero(){
        int czero = (eflags >> 0x06) & 0x01;
        return czero == 0x01;
    }

    public void setSign(boolean isSign){
        int signFlag = 1 << 0x07;
        
        if(isSign){
            eflags |= signFlag;
        }else{
            eflags &= ~signFlag;
        }
    }

    public boolean isSign(){
        int csign = (eflags >> 0x07) & 0x01;
        return csign == 1;
    }

    public void setInterrupt(boolean isInterrupt){
        int interruptFlag = 1 << 0x09;
        
        if(isInterrupt){
            eflags |= interruptFlag;
        }else{
            eflags &= ~interruptFlag;
        }
    }    

    public boolean isInterrupt(){
        int cinterrupt = (eflags >> 0x09) & 0x01;
        return cinterrupt == 1;
    }

    public void setDirection(boolean isDirection){
        int directionFlag = 1 << 0x0A;
        
        if(isDirection){
            eflags |= directionFlag;
        }else{
            eflags &= ~directionFlag;
        }
    }    

    public boolean isDirection(){
        int cdirection = (eflags >> 0x0A) & 0x01;
        return cdirection == 1;
    }

    public void setOverflow(boolean isOverflow){
        int overflowFlag = 1 << 0x0B;
        
        if(isOverflow){
            eflags |= overflowFlag;
        }else{
            eflags &= ~overflowFlag;
        }
    }

    public boolean isOverflow(){
        int coverflow = (eflags >> 0x0B) & 0x01;
        return coverflow == 1;
    }
}