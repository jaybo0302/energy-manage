package com.cdwoo.common;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MyDecoder4Net implements ProtocolDecoder {
	private Charset charset = Charset.forName("UTF-8");
	IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		int length = in.remaining();
		while(true) {
			Thread.sleep(500);
			if (in.remaining() > length) {
				length = in.remaining();
			} else {
				break;
			}
		}
        byte[] byteb = new byte[length];
        in.get(byteb);
        out.write(hex2String(byteb, byteb.length));
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		
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

