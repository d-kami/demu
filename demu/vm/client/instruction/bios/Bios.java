package vm.client.instruction.bios;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.CS;

public class Bios implements Instruction{
    private Instruction[] instructions;
    private int function;

    public Bios(){
        instructions = new Instruction[256];
        instructions[0x10] = new BiosGraphics();
        instructions[0x11] = new Bios11();
        instructions[0x12] = new Bios12();
        instructions[0x13] = new BiosDisk();
        instructions[0x15] = new Bios15();
        instructions[0x16] = new Bios16();
        instructions[0x1A] = new Bios1A();
        instructions[0x21] = new Bios21();
        instructions[0x29] = new Bios29();
    }

    public void execute(VM vm){
        function = vm.getCode8(1) & 0xFF;
        
        if(vm.isProtectMode(CS)){
            vm.swi(function);
        }else{
            Instruction instruction = instructions[function];

            if(instruction == null){
                throw new RuntimeException("Not Implement BIOS function = " + function);
            }

            instruction.execute(vm);
        }
        
        vm.addEIP(2);
    }

    @Override
    public String toString(){
        return "BIOS " + Integer.toHexString(function);
    }
}