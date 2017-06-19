package vm.client.instruction.ret;

import vm.client.VM;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.CS;
import static vm.client.register.RegisterIndex.SS;
import static vm.client.register.RegisterIndex.EIP;
import static vm.client.register.RegisterIndex.ESP;

public class IRET implements Instruction{
    public void execute(VM vm){
        long tempEIP = vm.pop32() & 0xFFFFFFFFL;
        long tempCS = vm.pop32() & 0xFFFFFFFFL;
        long eflags = vm.pop32() & 0xFFFFFFFFL;
        int cs = vm.getRegister16(CS);

        vm.setRegister32(EIP, tempEIP);
        vm.setRegister16(CS, (int)tempCS);
        vm.getEFlags().set((int)eflags);
        
        if((cs & 0x03) == 0 && (tempCS & 0x03) > 0){
            int tempESP = (int)vm.pop32();
            int tempSS = (int)vm.pop32();
            
            vm.setRegister32(ESP, tempESP);
            vm.setRegister16(SS, tempSS);
        }
    }
}