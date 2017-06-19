package vm.client.view;

import vm.client.VM;

public interface VMView{
    void setSize(int width, int height);
    
    int getRGB(int index);

    void setRGB(int index, int rgb);

    void setRGB(int x, int y, int rgb);

    void putCharacter(byte c);
    
    void setCharAt(int x, int y, byte c);
    
    void setCursor(int x, int y);

    void addColorElement(int element);

    int getPaletteSize();

    void setMode(int mode);
    
    void clear();

    int getWidth();
    
    void setVM(VM vm);
    
    void repaint();
}