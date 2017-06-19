package vm.client.view;

import java.util.HashMap;

public class ColorMap{
    private static int[] PLANE_PALETTE = {
        0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xAA5500,
        0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF,
        0xFFFF55, 0xFFFFFF
    }; 
    
    public static int getColor(int index){
        return ColorMap.PLANE_PALETTE[index];
    }
}
