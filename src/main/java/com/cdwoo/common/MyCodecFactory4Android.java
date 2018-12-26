package com.cdwoo.common;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MyCodecFactory4Android implements ProtocolCodecFactory {
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return new MyDecoder4Android();
	}
	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return new MyEncoder();
	}
}
