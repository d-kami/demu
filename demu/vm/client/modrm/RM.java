package vm.client.modrm;

import vm.client.VM;

public abstract class RM{
    protected VM vm;
    protected int size;

    protected RM(VM vm){
        this.vm = vm;
    }

    public int getSize(){
        return size;
    }
    
    public void set8(int value){
        if(true){
            throw new RuntimeException(getClass().getName());
        }
        
        return;
    }
    
    public int get8(){
        if(true){
            throw new RuntimeException(getClass().getName());
        }
        
        return 0;
    }

    public abstract void set(long value);
    public abstract long get();
}