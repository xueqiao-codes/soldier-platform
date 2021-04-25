package org.soldier.platform.fast_thrift_proxy.dispatcher.framework;

import java.util.LinkedList;
import java.util.List;

import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TStruct;

public class ThriftReqParts {
	public TProtocolFactory protocolFactory;
	
	public TMessage headerMsg;
	public TStruct argsStruct;
	
	public List<FieldPart> fields = new LinkedList<FieldPart>();
}
