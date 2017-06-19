package vm.client.modrm;

import vm.client.VM;
import static vm.client.register.RegisterIndex.SS;

public class Memory16 extends RM{
    private int address;
    private int segment;

    public Memory16(VM vm, int segment, int address, int size){
        super(vm);
        this.segment = segment;
        this.address = address;
        super.size = size;
    }
    
    @Override
    public void set8(int value){
        vm.set8(segment + address, (int)value);
    }
    
    @Override
    public int get8(){
        return vm.get8(segment + address);
    }

    public void set(long value){
        vm.setX(segment + address, (int)value);
    }

    public long get(){
        return vm.getX(segment + address);
    }
    
    public long getAddress(){
        return address;
    }
    
    public long getAddressOnSegment(){
        return segment + address;
    }
}