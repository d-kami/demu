package vm.client.view;

public class Color{
    private int red;
    private int green;
    private int blue;

    public Color(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed(){
        return red;
    }

    public int getGreen(){
        return green;
    }

    public int getBlue(){
        return  blue;
    }

    public int getInt(){
        return (red << 16) | (green << 8) | blue;
    }
}