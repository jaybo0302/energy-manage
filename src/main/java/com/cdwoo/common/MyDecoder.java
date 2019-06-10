package com.cdwoo.common;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.cdwoo.utils.DataUtil;

public class MyDecoder extends CumulativeProtocolDecoder {

    /**

     * 包解码器组件
     /**

     * 这种方法的返回值是重点：

     * 1、当内容刚好时，返回false，告知父类接收下一批内容

     * 2、内容不够时须要下一批发过来的内容，此时返回false，这样父类 CumulativeProtocolDecoder

     *   会将内容放进IoSession中，等下次来数据后就自己主动拼装再交给本类的doDecode

     * 3、当内容多时，返回true，由于须要再将本批数据进行读取。父类会将剩余的数据再次推送本

     * 类的doDecode

     */ 
	
    public boolean doDecode(IoSession session,IoBuffer in, 

            ProtocolDecoderOutput out) throws Exception { 
    	in.remaining();
        if(in.remaining() > 0){//有数据时。读取前8字节推断消息长度 
            byte [] sizeBytes = new byte[3]; 
            in.mark();//标记当前位置。以便reset
            //由于我的前数据包的长度是保存在第4-8字节中，

            in.get(sizeBytes,0,3);//读取4字节 

            //DataTypeChangeHelper是自己写的一个byte[]转int的一个工具类 
            int size = DataUtil.getLength(new String[] {DataUtil.hex2String(new byte[] {sizeBytes[1]}).substring(0,2), DataUtil.hex2String(new byte[] {sizeBytes[2]}).substring(0,2)})+8;

            System.out.println(size);
            in.reset();

            if(size > in.remaining()){//假设消息内容不够，则重置。相当于不读取size 
                return false;//父类接收新数据，以拼凑成完整数据 
            } else{ 
                byte[] bytes = new byte[size];  
                in.get(bytes, 0, size);
                //把字节转换为Java对象的工具类
                out.write(DataUtil.hex2String(bytes, bytes.length));
                if(in.remaining() > 0){//假设读取内容后还粘了包，就让父类再重读  一次。进行下一次解析 
                    return true; 
                } 
            } 
        } 

        return false;//处理成功，让父类进行接收下个包 
    }
}