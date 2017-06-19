package vm.client.modrm;

import vm.client.VM;
import static vm.client.register.RegisterIndex.SS;

public class MemoryX extends RM{
    private int address;

    public MemoryX(VM vm, int address, int size){
        super(vm);
        this.address = address;
        super.size = size;
    }

    public void set(long value){
        vm.setDataX(address, value);
        vm.setX(vm.getSegmentAddress(SS) + address, (int)value);
    }

    public long get(){
        return vm.getDataX(address);
    }
}