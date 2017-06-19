package vm.client.register;

public class FPUStatusRegister{
    private short value;
    
    public void setValue(short value){
        this.value = value;
    }
    
    public short getValue(){
        return value;
    }
}
