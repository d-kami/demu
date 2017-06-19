package vm.client.idt;

public class InterruptDescriptor{
    private int offset;
    private int selector;
    private int type;
    
    public InterruptDescriptor(byte[] memory, int index){
        int offsetLow = ((memory[index + 1] & 0xFF) << 8) + (memory[index] & 0xFF);
        int selector = ((memory[index + 3] & 0xFF) << 8) + (memory[index + 2] & 0xFF);
        int type = memory[index + 5] & 0xFF;
        int offsetHigh = ((memory[index + 7] & 0xFF) << 8) + (memory[index + 6] & 0xFF);
        
        this.offset = (offsetHigh << 16) | offsetLow;
        this.selector = selector;
        this.type = type;
    }
    
    public int getOffset(){
        return offset;
    }
    
    public int getSelector(){
        return selector;
    }
    
    public int getType(){
        return type;
    }
    
    public String toString(){
        String str = "Offset = " + Integer.toHexString(offset);
        str += ", Selector = " + Integer.toHexString(selector);
        str += ", Type = " + Integer.toHexString(type);
        
        return str;
    }
}
