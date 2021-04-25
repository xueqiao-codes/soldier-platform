package org.soldier.platform.fast_thrift_proxy.dispatcher.framework;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.ProtocolTransfer;

public class FieldPart {
    public TField argField;
    public byte[] argBytes;
    public int offset;
    public int len;
    public TBase fieldBase;
    public TProtocolFactory inProtocolFactory;
    
    public Byte byteValue;
    public Boolean boolValue;
    public Short i16Value;
    public Integer i32Value;
    public Long i64Value;
    public Double doubleValue;
    public String stringValue;
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("argField=").append(argField);
        if (argBytes != null) {
            builder.append("argBytes.length=").append(argBytes.length)
                    .append(", offset=").append(offset)
                    .append(", len=").append(len);
        }
        if (byteValue != null) {
            builder.append(", byteValue=").append(byteValue);
        }
        if (boolValue != null) {
            builder.append(", boolValue=").append(boolValue);
        }
        if (i16Value != null) {
            builder.append(", i16Value=").append(i16Value);
        }
        if (i32Value != null) {
            builder.append(", i32Value=").append(i32Value);
        }
        if (i64Value != null) {
            builder.append(", i64Value=").append(i64Value);
        }
        if (doubleValue != null) {
            builder.append(", doubleValue=").append(doubleValue);
        }
        if (stringValue != null) {
            builder.append(", stringValue=").append(stringValue);
        }
        if (fieldBase != null) {
            builder.append(", fieldBase=").append(fieldBase);
        }
        return builder.toString();
    }
    
    public void write(TProtocol outProtocol) throws TException {
        outProtocol.writeFieldBegin(argField);
        
        if (argField.type == TType.BYTE) {
            outProtocol.writeByte(byteValue);
        } else if (argField.type == TType.BOOL) {
            outProtocol.writeBool(boolValue);
        } else if (argField.type == TType.DOUBLE) {
            outProtocol.writeDouble(doubleValue);
        } else if (argField.type == TType.I16) {
            outProtocol.writeI16(i16Value);
        } else if (argField.type == TType.I32) {
            outProtocol.writeI32(i32Value);
        } else if (argField.type == TType.I64) {
            outProtocol.writeI64(i64Value);
        } else if (argField.type == TType.STRING) {
            outProtocol.writeString(stringValue);
        } else {
            if (fieldBase == null) {
                TProtocol fieldInProtocol = inProtocolFactory.getProtocol(
                        new TMemoryInputTransport(argBytes, offset, len));
                new ProtocolTransfer(fieldInProtocol, outProtocol).startTransfer(argField.type);
            } else {
                fieldBase.write(outProtocol);
            }
        }
        
        outProtocol.writeFieldEnd();
    }
    
    public static FieldPart construct(TField field, TProtocol inProtocol, TProtocolFactory inProtocolFactory) 
            throws TException {
        FieldPart fieldPart = new FieldPart();
        fieldPart.argField = field;
        fieldPart.inProtocolFactory = inProtocolFactory;
        if (field.type == TType.BYTE) {
            fieldPart.byteValue = inProtocol.readByte();
        } else if (field.type == TType.BOOL) {
            fieldPart.boolValue = inProtocol.readBool();
        } else if (field.type == TType.DOUBLE) {
            fieldPart.doubleValue = inProtocol.readDouble();
        } else if (field.type == TType.I16) {
            fieldPart.i16Value = inProtocol.readI16();
        } else if (field.type == TType.I32) {
            fieldPart.i32Value = inProtocol.readI32();
        } else if (field.type == TType.I64) {
            fieldPart.i64Value = inProtocol.readI64();
        } else if (field.type == TType.STRING) {
            fieldPart.stringValue = inProtocol.readString();
        } else {
            AutoExpandingBufferWriteTransport fieldOutTransport = new AutoExpandingBufferWriteTransport(512, 2.0);
            TProtocol fieldOutProtocol = inProtocolFactory.getProtocol(fieldOutTransport);
            new ProtocolTransfer(inProtocol, fieldOutProtocol).startTransfer(field.type);
            fieldPart.argBytes = fieldOutTransport.getBuf().array();
            fieldPart.offset = 0;
            fieldPart.len = fieldOutTransport.getPos();
        }
        
        return fieldPart;
    }
}
