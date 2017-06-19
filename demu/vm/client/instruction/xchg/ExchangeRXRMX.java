package vm.client.instruction.xchg;

import vm.client.VM;
import vm.client.modrm.ModRM;
import vm.client.instruction.Instruction;

public class ExchangeRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long temp = modrm.getRegisterX();
        modrm.setRegisterX(modrm.getRMValue());
        modrm.setRMValue(temp);

        vm.addEIP(2);
    }
}