package vm.client.idt;

public class IDT{
    private InterruptDescriptor[] descriptors;

    public void lidt(byte[] buff, int start, int count){
        count++;
        descriptors = new InterruptDescriptor[count];
        
        for(int  i = 0; i < count; i++){
            descriptors[i] = new InterruptDescriptor(buff, start + (i * 8));
        }
    }

    public InterruptDescriptor get(int index){
        return descriptors[index];
    }
}