package vm.client.register;

public class FPUControlRegister{
    private short value;
    
    public void setValue(short value){
        this.value = value;
    }
    
    public short getValue(){
        return value;
    }
}
