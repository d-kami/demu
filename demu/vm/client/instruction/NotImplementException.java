package vm.client.instruction;

public class NotImplementException extends Exception{
    public NotImplementException(int code){
        super("Not Implement Code: " + Integer.toHexString(code));
    }
}