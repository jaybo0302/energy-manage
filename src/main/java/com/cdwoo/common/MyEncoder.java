package com.cdwoo.common;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.cdwoo.utils.DataUtil;

public class MyEncoder implements ProtocolEncoder {
	private Charset charset = Charset.forName("UTF-8");
	@Override
	public void dispose(IoSession arg0) throws Exception {}
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        IoBuffer ioBuffer = IoBuffer.allocate(300, false);
        ioBuffer.setAutoExpand(true);
        ioBuffer.setAutoShrink(true);
        byte[] responseByteArr = DataUtil.toBytes(message.toString().replaceAll(" ", ""));
        ioBuffer.put(responseByteArr);
        ioBuffer.flip();    //Flip it or there will be nothing to send
        out.write(ioBuffer);
        out.flush();
	}
	
	public static String hex2String(byte[] b, int length) {
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < length; i++) {
    		String hex = Integer.toHexString(0xFF & b[i]);
    		if(hex.length() == 1) {
    			sb.append("0");
    		}
    		sb.append(hex);
    		sb.append(" ");
    	}
    	String s = sb.toString();
    	return s;
    }
}
