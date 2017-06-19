package vm.client.pic;

public class PIC{
    private int pic0Mask;
    private int pic1Mask;
    
    public PIC(){
        pic0Mask = 0xFF;
        pic1Mask = 0xFF;
    }
    
    public void setPIC0Mask(int pic0Mask){
        this.pic0Mask = pic0Mask;
    }
    
    public void setPIC1Mask(int pic1Mask){
        this.pic1Mask = pic1Mask;
    }
    
    public int getPIC0Mask(){
        return pic0Mask;
    }
    
    public int getPIC1Mask(){
        return pic1Mask;
    }
    
    public boolean availableTimer(){
        return (this.pic0Mask & 0x01) == 0;
    }
    
    public boolean availableKey(){
        return (this.pic0Mask & 0x02) == 0;
    }
}
