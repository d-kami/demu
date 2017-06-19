package vm.client.instruction.io;

import vm.client.VM;
import vm.client.instruction.Instruction;
import static vm.client.register.RegisterIndex.EDX;
import static vm.client.register.RegisterIndex.ESI;

public class OUTS implements Instruction{
    public void execute(VM vm){
        long esi = vm.getRegister32(ESI);
        int data = vm.getX(vm.getSegmentAddress(vm.getSegmentIndex(), ESI));
        int port = vm.getRegister16(EDX);
        
        int size = vm.is32bitOperand() ? 4 : 2;
        if(vm.getEFlags().isDirection()){
            vm.setRegister32(ESI, esi - size);
        }else{
            vm.setRegister32(ESI, esi + size);
        }
        
        vm.out(port, data);
        vm.addEIP(1);
    }
}