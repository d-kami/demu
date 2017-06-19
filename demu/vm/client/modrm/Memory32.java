package vm.client.modrm;

import vm.client.VM;
import vm.client.util.Paging;

public class Memory32 extends RM{
    private int address;
    private int segment;

    public Memory32(VM vm, int segment, int address, int size){
        super(vm);
        this.segment = segment;
        this.address = address;
        super.size = size;
    }
    
    @Override
    public void set8(int value){
        vm.set8(Paging.getAddress(vm, segment + address), value);
    }
    
    @Override
    public int get8(){
        return vm.get8(Paging.getAddress(vm, segment + address));
    }

    public void set(long value){
        if(vm.is32bitOperand()){
            if(segment + address >= 0xA000000){
                if(vm.isDebug() && address == 0xA000000){
                    throw new RuntimeException();
                }
                
                System.out.printf("Address = %d\n", address- 0xA000000);
                vm.setDebug(true);
            }
            
            vm.set32(Paging.getAddress(vm, segment + address), value);
        }else{
            vm.set16(Paging.getAddress(vm, segment + address), (int)value);
        }
    }

    public long get(){
        if(vm.is32bitOperand()){
            return vm.get32(Paging.getAddress(vm, segment + address));
        }else{
            return vm.get16(Paging.getAddress(vm, segment + address));
        }
    }

    public long getAddress(){
        return address;
    }
    
    public long getAddressOnSegment(){
        return segment + address;
    }
}