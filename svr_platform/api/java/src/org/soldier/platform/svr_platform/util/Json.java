package org.soldier.platform.svr_platform.util;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.soldier.base.StringFactory;

import com.google.gson.Gson;

public class Json {
	private static Gson gsonInstance = new Gson();
	
	public static String toJson(TBase<?, ?> obj){
		AutoExpandingBufferWriteTransport transport = new AutoExpandingBufferWriteTransport(128, 2.0);
		TProtocol protol = new TSimpleJSONProtocol(transport);
		try {
			obj.write(protol);
			return StringFactory.netUtf8String(transport.getBuf().array(), transport.getPos());
		} catch (TException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	public static <T extends TBase<?, ?>> T fromJson(String jsonString,  Class<T> clazz) throws JsonException {
		try {
			return gsonInstance.fromJson(jsonString, clazz);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}
}
