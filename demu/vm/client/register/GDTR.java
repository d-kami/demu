package vm.client.register;

public  class GDTR{
    private int start;
    private int size;

    public void setGDTR(int start, int size){
        this.start = start;
        this.size = size;
    }

    public int getStart(){
        return start;
    }

    public int getSize(){
        return size;
    }
}