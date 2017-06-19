package vm.client.gdt;

public class LDT{
    private SegmentDescriptor[] descriptors;

    public void lldt(byte[] buff, int start, int count){
        count++;
        descriptors = new SegmentDescriptor[count];
        
        for(int  i = 0; i < count; i++){
            descriptors[i] = new SegmentDescriptor(buff, start + (i * 8));
        }
    }

    public SegmentDescriptor get(int index){
        return descriptors[index];
    }
}