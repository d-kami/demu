package vm.client.gdt;

public class SegmentDescriptor{
    private int base;
    private int limit;
    private int accessRight;

    public SegmentDescriptor(byte[] memory, int index){
        int limitLow = ((memory[index + 1] & 0xFF) << 8) + (memory[index] & 0xFF);
        int baseLow = ((memory[index + 3] & 0xFF) << 8) + (memory[index + 2] & 0xFF);
        int baseMiddle = memory[index + 4] & 0xFF;
        int accessRight = ((memory[index + 6] & 0xF0) << 8) | (memory[index + 5] & 0xFF);
        int limitHigh = memory[index + 6] & 0x0F;
        int baseHigh = memory[index + 7] & 0xFF;

        this.base = (baseHigh << 24) + (baseMiddle << 16) + baseLow;
        this.limit = (limitHigh << 16) + limitLow;
        this.accessRight = accessRight;
    }

    public int getBase(){
        return base;
    }

    public int getLimit(){
        return limit;
    }

    public int getAccessRight(){
        return accessRight;
    }

    public String toString(){
        String str =  "Base = " + Integer.toHexString(base);
        str += ", limit = " + Integer.toHexString(limit);
        str += ", accessRight = " + Integer.toHexString(accessRight);

        return str;
    }
}