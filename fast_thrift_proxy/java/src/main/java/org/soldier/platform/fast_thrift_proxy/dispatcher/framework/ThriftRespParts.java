package org.soldier.platform.fast_thrift_proxy.dispatcher.framework;

import java.util.LinkedList;
import java.util.List;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TStruct;

public class ThriftRespParts {
    public TMessage headerMsg;
    public TStruct respStruct;
    public TApplicationException ex;
    
    public List<FieldPart> fields = new LinkedList<FieldPart>();
}
