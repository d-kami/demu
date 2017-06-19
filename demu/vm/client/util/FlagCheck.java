package vm.client.util;

import vm.client.register.EFlags;

public class FlagCheck{
    public static void addCheck(long target, long value, long result, EFlags eflags, int bits){
        if(bits == 8){
            eflags.setOverflow(result > Byte.MAX_VALUE  || result < Byte.MIN_VALUE );
        }else if(bits == 16){
            eflags.setOverflow(result > Short.MAX_VALUE  || result < Short.MIN_VALUE );
        }else if(bits == 32){
            eflags.setOverflow(result > Integer.MAX_VALUE  || result < Integer.MIN_VALUE );
        }

        FlagCheck.check(target, result, eflags, bits);

        if(value >= 0){
            eflags.setCarry((result & (1L << bits)) > 0);
        }else{
            eflags.setCarry(value > target);
        }
        
        /*
        eflags.setCarry(target > result)
        */;
    }
    
    public static void subCheck(long target, long value, long result, EFlags eflags, int bits){
        if(bits == 8){
            eflags.setOverflow(result > Byte.MAX_VALUE  || result < Byte.MIN_VALUE );
        }else if(bits == 16){
            eflags.setOverflow(result > Short.MAX_VALUE  || result < Short.MIN_VALUE );
        }else if(bits == 32){
            eflags.setOverflow(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE );
        }

        FlagCheck.check(target, result, eflags, bits);
        
        if(value < 0){
            eflags.setCarry((result & (1L << bits)) > 0);
        }/*else{
            eflags.setCarry(value > target);
        }*/
    }

    public static void check(long before, long result, EFlags eflags, int bits){
        boolean flag = (result & (1L << bits)) > 0;
        eflags.setCarry(flag);

        flag =  ((result >> (bits - 1)) & 0x01) == 0x01;
        eflags.setSign(flag);
        
        if(bits == 8){
            result &= 0xFF;
        }else if(bits == 16){
            result &= 0xFFFF;
        }else{
            result &= 0xFFFFFFFF;
        }
        
        flag = (result == 0);
        eflags.setZero(flag);

        flag = ((before & 0x0F) == 0x0F && (result & 0x0F) != 0x0F) || ((before & 0x0F) != 0x0F && (result & 0x0F) == 0x0F);
        eflags.setAdjust(flag);

        int count = (int)result & 0x01;
        count += (result & 0x02) >> 1;
        count += (result & 0x04) >> 2;
        count += (result & 0x08) >> 3;
        count += (result & 0x10) >> 4;
        count += (result & 0x20) >> 5;
        count += (result & 0x40) >> 6;
        count += (result & 0x80) >> 7;

        flag = (count % 2) == 0;
        eflags.setParity(flag);
    }
}