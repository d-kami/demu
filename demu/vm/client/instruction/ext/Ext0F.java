package vm.client.instruction.ext;

import vm.client.VM;
import vm.client.instruction.Instruction;

import vm.client.instruction.lfp.LFSX;
import vm.client.instruction.lfp.LGSX;
import vm.client.instruction.lfp.LSSX;
import vm.client.instruction.pop.Pop;
import vm.client.instruction.set.SetC;
import vm.client.instruction.set.SetE;
import vm.client.instruction.set.SetG;
import vm.client.instruction.set.SetLE;
import vm.client.instruction.set.SetNE;
import vm.client.instruction.set.SetNBE;
import vm.client.instruction.flag.CLTS;
import vm.client.instruction.imul.IMulRXRMX;
import vm.client.instruction.jump.JAX;
import vm.client.instruction.jump.JCX;
import vm.client.instruction.jump.JEX;
import vm.client.instruction.jump.JLX;
import vm.client.instruction.jump.JLEX;
import vm.client.instruction.jump.JNAX;
import vm.client.instruction.jump.JNBX;
import vm.client.instruction.jump.JNEX;
import vm.client.instruction.jump.JNLX;
import vm.client.instruction.jump.JNLEX;
import vm.client.instruction.jump.JNSEX;
import vm.client.instruction.jump.JSEX;
import vm.client.instruction.push.Push;
import vm.client.instruction.move.MoveCRXR32;
import vm.client.instruction.move.MoveR32CRX;
import vm.client.instruction.move.MoveZXRXRM8;
import vm.client.instruction.move.MoveZXR32RM16;
import vm.client.instruction.move.MoveSXRXRM8;
import vm.client.instruction.move.MoveSXR32RM16;
import vm.client.instruction.cpuid.CPUID;
import vm.client.instruction.rdtsc.RDTSC;
import vm.client.instruction.wbinvd.WBINVD;
import vm.client.instruction.descriptor.Descriptor;
import vm.client.instruction.descriptor.Descriptor0;

import static vm.client.register.RegisterIndex.*;

public class Ext0F implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Ext0F(){
        instructions = new Instruction[256];
        
        instructions[0x00] = new Descriptor0();
        instructions[0x01] = new Descriptor();
        instructions[0x06] = new CLTS();
        instructions[0x09] = new WBINVD();
        instructions[0x20] = new MoveR32CRX();
        instructions[0x22] = new MoveCRXR32();
        instructions[0x31] = new RDTSC();
        instructions[0x82] = new JCX();
        instructions[0x83] = new JNBX();
        instructions[0x84] = new JEX();
        instructions[0x85] = new JNEX();
        instructions[0x86] = new JNAX();
        instructions[0x87] = new JAX();
        instructions[0x88] = new JSEX();
        instructions[0x89] = new JNSEX();
        instructions[0x8C] = new JLX();
        instructions[0x8D] = new JNLX();
        instructions[0x8E] = new JLEX();
        instructions[0x8F] = new JNLEX();
        instructions[0x92] = new SetC();
        instructions[0x94] = new SetE();
        instructions[0x95] = new SetNE();
        instructions[0x97] = new SetNBE();
        instructions[0x9E] = new SetLE();
        instructions[0x9F] = new SetG();
        instructions[0xA0] = new Push(FS);
        instructions[0xA1] = new Pop(FS);
        instructions[0xA2] = new CPUID();
        instructions[0xA8] = new Push(GS);
        instructions[0xA9] = new Pop(GS);
        instructions[0xAF] = new IMulRXRMX();
        instructions[0xB2] = new LSSX();
        instructions[0xB4] = new LFSX();
        instructions[0xB5] = new LGSX();
        instructions[0xB6] = new MoveZXRXRM8();
        instructions[0xB7] = new MoveZXR32RM16();
        instructions[0xBE] = new MoveSXRXRM8();
        instructions[0xBF] = new MoveSXR32RM16();
    }

    public void execute(VM vm){
        int code = vm.getCode8(1) & 0xFF;
        vm.addEIP(1);

        instruction = instructions[code];
        if(instruction == null){
            throw new RuntimeException("Not Implement Code = " + Integer.toHexString(code));
        }

        instruction.execute(vm);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}