package vm.client.view;

import java.util.List;
import java.util.ArrayList;

public class Palette{
    private List<Color> list;
    private int index;
    private int[] color;

    public Palette(){
        list = new ArrayList<Color>();
        color = new int[3];
    }

    public void addColorElement(int element){
        color[index++] = element;
        index %= 3;

        if(index == 0){
            list.add(new Color(color[0], color[1], color[2]));
        }
    }

    public int getSize(){
        return list.size();
    }

    public Color getColor(int index){
        return list.get(index);
    }
}