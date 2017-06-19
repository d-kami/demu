package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.view.VMView;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class BiosGraphics implements Instruction{
    private static int[] PLANE_PALETTE = {
        0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xAA5500,
        0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF,
        0xFFFF55, 0xFFFFFF
    }; 

    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int ah = vm.getRegister8High(EAX);
        int ax = vm.getRegister16(EAX);
        int bl = vm.getRegister8Low(EBX);
        int es = vm.getRegister16(ES);
        int di = vm.getRegister16(EDI);

        if(ah == 0){
            VMView view = vm.getView();

            if(al == 0x02){
                view.setSize(80 * 8, 25 * 16);
            }else if(al == 0x03){
                view.setSize(80 * 8, 25 * 16);
            }else if(al == 0x12 || al == 0x13){
                view.setSize(640, 480);
                view.setMode(1);
            }else{
                throw new RuntimeException("Not Implemented Bios Graphics AH = 0, AL = " + Integer.toHexString(al));
            }
        }else if(ah == 0x02){
            VMView view = vm.getView();
            int dl = vm.getRegister8Low(EDX);
            int dh = vm.getRegister8High(EDX);

            view.setCursor(dl, dh + 1);
        }else if(ah == 0x03){
            vm.setRegister16(EAX, 0);
            vm.setRegister16(ECX, 0);
            vm.setRegister16(EDX, 0);
        }else if(ah == 0x06){
            if(al == 0){
                vm.getView().clear();
            }
        }else if(ah == 0x09){
            byte c = (byte)al;
            int count = vm.getRegister16(ECX);

            VMView view = vm.getView();

            for(int i = 0; i < count; i++){
                view.putCharacter(c);
            }
        }else if(ah == 0x0C){
            VMView view = vm.getView();
            int color = PLANE_PALETTE[al];
            view.setRGB(vm.getRegister16(ECX), vm.getRegister16(EDX), color);
            view.repaint();
        }else if(ah == 0x0e){
            byte c = (byte)al;

            VMView view = vm.getView();
            view.putCharacter(c);
        }else if(ah == 0x0f){
            vm.setRegister8High(EAX, 24);
            vm.setRegister8Low(EAX, 0x12);
            vm.setRegister8High(EBX, 1);
        }else if(ax == 0x1002){
        }else if(ax == 0x1012){
        }else if(ax == 0x1130){
        }else if(ax == 0x1200){
        }else if(ah == 0x12 && bl == 0x10){
            vm.setRegister16(ECX, 0x09);
            vm.setRegister8Low(EBX, 0x03);
        }else if(ax == 0x1A00){
            vm.setRegister8Low(EAX, 0x1A);
            vm.setRegister16(EBX, 0x08);
        }else if(ax == 0x4F00){
            vm.setRegister16(EAX, 0x4F);
            vm.set16(es * 16 + di + 4, 0x0200);
        }else if(ax == 0x4F01){
            vm.setRegister16(EAX, 0x004F);

            int address = es * 16 + di;

            vm.set16(address, 0x9B);
            vm.set8(address + 2, 0x07);
            vm.set8(address + 3, 0x00);
            vm.set16(address + 0x10, 0x960);

            int cx = vm.getRegister16(ECX);

            if(cx == 0x00){
                vm.set16(address + 0x12, 320);
                vm.set16(address + 0x14, 200);
            }else if(cx == 0x18){
                vm.set16(address + 0x12, 720);
                vm.set16(address + 0x14, 480);
            }
            
            vm.set8(address + 0x19, 0x18);
            vm.set8(address + 0x1b, 0x06);
            vm.set8(address + 0x1F, 0x08);
            vm.set8(address + 0x21, 0x08);
            vm.set8(address + 0x23, 0x08);
            vm.set32(address + 0x28, 0xA000000);
            
//            throw new RuntimeException();
        }else if(ax == 0x4F02){
            int ebx = vm.getRegister16(EBX);
            VMView view = vm.getView();
            vm.setRegister16(EAX, 0x4F);
            
            if(ebx == 0x4105){
                view.setSize(1024, 768);
            }else if(ebx == 0x4112){
                view.setSize(640, 480);
            }else if(ebx == 0x4114 || ebx == 0x4115){
                view.setSize(800, 600);
            }else{
                throw new RuntimeException("GraphicMode 4F02 Not Implemented = " + Integer.toHexString(ebx));
            }
        }else if(ax == 0x4F0A){
            vm.setRegister16(EAX, 0x4F01);
        }else{
            throw new RuntimeException("Not Implemented Bios Graphics AX = " + Integer.toHexString(ax));
        }
    }
}