package vm.client.instruction.comp;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.util.FlagCheck;
import vm.client.instruction.Instruction;

import static vm.client.register.RegisterIndex.*;

public class CompRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rx = modrm.getRegisterX();
        long rmx = modrm.getRMValue();
        
        long result = rx - rmx;
        FlagCheck.subCheck(rx, rmx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
/*        
        if(vm.getEIP() == 0x353){
            System.out.printf("ES = %x, EIP = %x\n", vm.getRegister16(ES), vm.getCodeAddress(0));
            System.out.printf("rx = %x, rmx = %x\n", rx, rmx);
            
            throw new RuntimeException();
        }
*/
        vm.addEIP(2);
    }
}