package com.chidi.cloud;

import java.io.Serializable;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/** 
 *  
 *  
 * @author QIANG 
 */  
public class UniCodeUtil implements Serializable {
  
    private static final long serialVersionUID = -5628028113003022356L;  
  
    private static final int MAX_SIZE = 25;  
    private static final int MIN_SIZE = 14;  
    private static final int BASE_SIZE = 2;  
  
    /** 
     * 默认生成RCID 12位 
     *  
     * @return 
     */  
    public static String rand() {  
        return rand(16);  
    }  
  
    /** 
     * 根据传入值生成相应位数随机码 
     *  
     * @param offset 最大生成40位，最小生成14位 (位权基础系数为4) 4 6 8 1 2 3 
     *  
     * @return 
     */  
    public static String rand(int offset) {  
  
        if (offset > MAX_SIZE || offset < MIN_SIZE) {  
            return "";  
        }  
        int div = offset / 10;  
        int bitSize = BASE_SIZE + (div << 2 >> 1);  
        int baseSize = offset - bitSize;  
  
        String baseID = randomBaseID(baseSize);
       // System.out.println("baseID is :" + baseID);

        StringBuilder rcid = new StringBuilder(baseID);  
        String rv = bitWeight(bitSize);  
        char[] ch = rv.toCharArray();  
        for (char c : ch) {  
            rcid.insert(Character.getNumericValue(c), c);  
        }  
        return rcid.toString().toLowerCase();
    }  
  
    /** 
     * 根据UUID生成随机指定位数位ID 
     *  
     * @return 
     */  
    public static String randomBaseID(int baseSize) {  
        UUID uid = UUID.randomUUID();  
        long idx = uid.getLeastSignificantBits();  
        StringBuilder buff = new StringBuilder();  
        for (int i = 0; i < 12; i++) {  
            buff.append(toa(0x1F & idx));  
            idx >>>= 5;  
        }  
        long idxb = uid.getMostSignificantBits();  
        buff.append(toa((idxb & 0x1) == 0 ? idx : idx + 0x10));  
        idx = (idxb >>> 1);  
        for (int i = 0; i < 13; i++) {  
            buff.append(toa(0x1F & idx));  
            idx >>>= 5;  
        }
        return buff.reverse().toString().substring(0, baseSize);
    }  
  
    /** 
     * 获取位权数 
     *  
     * @return 
     */  
    private static String bitWeight(int bitSize) {  
        String str = String.valueOf(Math.random()).substring(2);  
        while (str.length() < bitSize || str.indexOf('-') > 0) {  
            str = String.valueOf(Math.random()).substring(2);  
        }  
        return str.substring(0, bitSize);  
    }  
  
    /** 
     * 生成可见字符 
     *  
     * @param lidx 
     * @return 
     */  
    private static char toa(long lidx) {  
        if (lidx < 10) {  
            return (char) (0x30 + lidx);  
        }  
        lidx += (0x41 - 10);  
        int[] skips = { 0x49, 0x4f, 0x57, 0x5a };  
        for (int ch : skips) {  
            if (lidx < ch) {  
                break;  
            }  
            lidx++;  
        }  
        return (char) lidx;  
    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
//        System.out.println(UniCodeUtil.rand());
//        System.out.println("cost : "  + (System.currentTimeMillis() - start));
        final ConcurrentHashMap<String,Long> ids = new ConcurrentHashMap<String, Long>();
        final  AtomicLong values = new AtomicLong(0);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while (values.get() < 1000000){
            executor.execute(new Runnable() {
                public void run() {
                    if(values.get() < 1000000){
                        String key = UniCodeUtil.rand();
//                        if(ids.contains(key)){
//                            System.out.println("error : "  + key);
//                        }
                        ids.put(key,values.get());
                        values.addAndGet(1);
                    }
                }
            });
        }

        System.out.println(ids.size());
        System.out.println("cost : "  + (System.currentTimeMillis() - start));
        executor.shutdown();
    }
}  