package vm.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.image.BufferedImage;

import java.io.IOException;

import vm.client.VM;
import static vm.client.register.RegisterIndex.*;

public class VMComponent extends JComponent implements VMView, KeyListener{
    private BufferedImage image;

    private boolean isTextMode;
    private int cx = 0;
    private int cy = 0;
    private byte[][] display;
    private Palette palette;
    private VM vm;
    private Font font = new Font("ÇlÇr ÉSÉVÉbÉN", Font.PLAIN, 16);

    public VMComponent(){
        display = new byte[25][];;

        clear();

        isTextMode = true;
        palette = new Palette();
    }
    
    public void clear(){
        for(int i = 0; i < display.length; i++){
            display[i] = new byte[80];
        }
    }

    public void setSize(final int width, final int height){
        Runnable runnable = new Runnable(){
            public void run(){
                image = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);

                setPreferredSize(new Dimension(width , height));
                Container container = VMComponent.this;

                while((container = container.getParent()) != null){
                    if(container instanceof JFrame){
                        JFrame frame = (JFrame)container;
                        frame.pack();

                        break;
                    }
                }
            }
        };

        if(SwingUtilities.isEventDispatchThread()){
            runnable.run();
        }else{
            try{
                SwingUtilities.invokeAndWait(runnable);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public int getPaletteSize(){
        return palette.getSize();
    }

    public void setCharAt(int x, int y, byte c){
        display[y][x] = c;
        /*
        if(y > 0 && x == 0){
            putCharacter((byte)'\n');
        }
        
        putCharacter(c);
        */
        repaint();
    }
    
    public void setRGB(int index, int rgb){
        int x = index % getWidth();
        int y = index / getWidth();

        if(x >= getWidth() || y >= getHeight() || x < 0 || y < 0){
            return;
        }

        setRGB(x, y, rgb);
    }
    
    public int getRGB(int index){
        int x = index % getWidth();
        int y = index / getWidth();

        if(x >= getWidth() || y >= getHeight() || x < 0 || y < 0){
            return 0;
        }
        
        return image.getRGB(x, y);
    }
    
    int count = 0;
    
    public void setRGB(int x, int y, int rgb){
        isTextMode = false;
        
        if(x >= image.getWidth() || y >= image.getHeight() || x < 0 || y < 0){
            return;
        }
        
        image.setRGB(x, y, rgb);
        
        count++;
        
        if(count == 4){
            repaint();
            count = 0;
        }
    }

    char old = (char)0;
    
    public void putCharacter(byte c){
        if(c == 0x20 && old == 0x20){
            return;
        }
        
        if(c == 0x0d){
            return;
        }
        
        if(c == 0x08){
            cx--;
            return;
        }
        
        old = (char)c;
        
        if(c == 0x0a){
            if(cy >= display.length - 2){
                while(cy >= display.length - 2){
                    for(int i = 0; i < display.length - 1; i++){
                        display[i] = display[i + 1];
                    }
                
                    display[23] = new byte[80];
                
                    cy--;
                    cx = 0;
                }
            }else{
                cy++;
                cx = 0;
            }
        }else{
            if(cx == 80){
                if(cy >= display.length - 2){
                    while(cy >= display.length - 2){
                        for(int i = 0; i < display.length - 1; i++){
                            display[i] = display[i + 1];
                        }

                        display[23] = new byte[80];
                    
                        cy--;
                        cx = 0;
                    }
                }else{
                    cy++;
                    cx = 0;
                }
            }
            
            display[cy][cx] = c;
            cx++;
        }

        repaint();
    }

    public void setCursor(int x, int y){
        if(x == 0 && y == 0){
            return;
        }

        if(y >= display.length - 1){
            while(y >= display.length - 1){
                for(int i = 0; i < display.length - 1; i++){
                    display[i] = display[i + 1];
                }

                display[23] = new byte[80];
                
                y--;
            }
        }
        
        cx = x;
        cy = y;
    }

    public void addColorElement(int element){
        palette.addColorElement(element);
        isTextMode = false;
    }
    
    public void setVM(VM vm){
        this.vm = vm;
        
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        addTimer();
    }

    public boolean isFOacuable(){
        return false;
    }
    
    public boolean isRequestFocusEnabled(){
        return true;
    }
    
    public void keyPressed(KeyEvent event){
        int code = event.getKeyCode();
        vm.inputKey(code);
        vm.setInputKey(code);
    }
    
    public void keyReleased(KeyEvent event){
        int code = event.getKeyCode() | 0x80;
        vm.inputKey(code);
    }
    
    public void keyTyped(KeyEvent event){
        
    }
    
    public void addTimer(){
        Timer timer = new Timer(10, new ActionListener(){
            public void actionPerformed(ActionEvent event){
                vm.inputTimer();
            }
        });
        
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g){
        if(isTextMode){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(font);

            try{
                for(int y = 0; y < display.length; y++){
                    String line = new String(display[y], "Cp437");
                    
                    for(int x = 0; x < line.length(); x++){
                        g.drawString(Character.toString(line.charAt(x)), x * 8, y * 16 + 16);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            g.drawImage(image, 0, 0, this);
        }
    }

    public void setMode(int mode){
        if(mode > 0){
            isTextMode = false;
        }
    }
}