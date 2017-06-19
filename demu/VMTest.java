import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.io.IOException;

import vm.client.VM;
import vm.client.view.VMView;
import vm.client.view.VMComponent;

public class VMTest{
    public static void start(final String[] args) throws Exception{
        final VMComponent view = new VMComponent();
        view.setSize(80 * 8, 25 * 16);

        JFrame frame = new JFrame();
        frame.add(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        final String os = args[0];
        final int memorySize = 32 * 1024 * 1024;

        Thread thread = new Thread(new Runnable(){
            public void run(){
                int mode = 0;
                boolean isDebug = false;

                for(String arg : args){
                    if("-cdrom".equals(arg)){
                        mode = 1;
                    }else if("-DEBUG".equals(arg)){
                        isDebug = true;
                    }
                }

                VM vm = new VM(memorySize, view);
                vm.setDebug(isDebug);
                
                int count = 0;

                try{
                    vm.load(os, mode);

                    while(true){
                        count++;
                        vm.execute();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                System.out.println("Execute Count = " + count);
                vm.dump();
            }
        });

        thread.run();
    }

    public static void main(final String[] args) throws Exception{
        VMTest.start(args);
    }
}