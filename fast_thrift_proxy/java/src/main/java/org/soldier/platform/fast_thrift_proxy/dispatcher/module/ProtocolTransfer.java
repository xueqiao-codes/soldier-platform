package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TList;
import org.apache.thrift.protocol.TMap;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TSet;
import org.apache.thrift.protocol.TStruct;
import org.apache.thrift.protocol.TType;

public class ProtocolTransfer {
	private int startMaxDepth = Integer.MAX_VALUE;
	private TProtocol inProtocol;
	private TProtocol outProtocol;
	
	public ProtocolTransfer(TProtocol inProtocol, TProtocol outProtocol) {
		this.inProtocol = inProtocol;
		this.outProtocol = outProtocol;
	}
	
	public void startTransfer(byte type) throws TException {
		transfer(type, getStartMaxDepth());
	}

	private void transfer(byte type, int maxDepth) throws TException {
		if (maxDepth <= 0) {
			throw new TException("Maximum skip depth exceeded");
		}
		switch (type) {
		case TType.BOOL:
			outProtocol.writeBool(inProtocol.readBool());
			break;

		case TType.BYTE:
			outProtocol.writeByte(inProtocol.readByte());
			break;

		case TType.I16:
			outProtocol.writeI16(inProtocol.readI16());
			break;

		case TType.I32:
			outProtocol.writeI32(inProtocol.readI32());
			break;

		case TType.I64:
			outProtocol.writeI64(inProtocol.readI64());
			break;

		case TType.DOUBLE:
			outProtocol.writeDouble(inProtocol.readDouble());
			break;

		case TType.STRING:
		    if (inProtocol instanceof TJSONProtocol) {
		        outProtocol.writeString(inProtocol.readString());
		    } else {
		        if (outProtocol instanceof TJSONProtocol) {
		            outProtocol.writeString(inProtocol.readString());
		        } else {
		            outProtocol.writeBinary(inProtocol.readBinary());
		        }
		    }
			break;

		case TType.STRUCT:
			TStruct struct = inProtocol.readStructBegin();
			outProtocol.writeStructBegin(struct);
			
			while (true) {
				TField field = inProtocol.readFieldBegin();
				if (field.type == TType.STOP) {
					outProtocol.writeFieldStop();
					break;
				}
				outProtocol.writeFieldBegin(field);
				transfer(field.type, maxDepth - 1);
				inProtocol.readFieldEnd();
				outProtocol.writeFieldEnd();
			}
			inProtocol.readStructEnd();
			outProtocol.writeStructEnd();
			break;

		case TType.MAP:
			TMap map = inProtocol.readMapBegin();
			outProtocol.writeMapBegin(map);
			for (int i = 0; i < map.size; i++) {
				transfer(map.keyType, maxDepth - 1);
				transfer(map.valueType, maxDepth - 1);
			}
			inProtocol.readMapEnd();
			outProtocol.writeMapEnd();
			break;

		case TType.SET:
			TSet set = inProtocol.readSetBegin();
			outProtocol.writeSetBegin(set);
			for (int i = 0; i < set.size; i++) {
				transfer(set.elemType, maxDepth - 1);
			}
			inProtocol.readSetEnd();
			outProtocol.writeSetEnd();
			break;

		case TType.LIST:
			TList list = inProtocol.readListBegin();
			outProtocol.writeListBegin(list);
			for (int i = 0; i < list.size; i++) {
				transfer(list.elemType, maxDepth - 1);
			}
			inProtocol.readListEnd();
			outProtocol.writeListEnd();
			break;

		default:
			throw new TException("unkown transfer type " + type);
		}
	}
	
	

	public int getStartMaxDepth() {
		return startMaxDepth;
	}

	public void setStartMaxDepth(int startMaxDepth) {
		this.startMaxDepth = startMaxDepth;
	}

	/*public static void main(String[] args) throws TException {
		AppLog.init("ProtocolTransfer");
		
		PlatformArgs platformArgs = new PlatformArgs();
		platformArgs.setRemoteAddress("remoteAddress");
		platformArgs.setRemotePort(1000);
		platformArgs.setSourceDesc("this is test");
		platformArgs.setSourceIpV4(255);
		
		System.out.println(platformArgs);
		
		TMemoryBuffer transport = new TMemoryBuffer(1024);
		platformArgs.write(
				new TBinaryProtocol.Factory().getProtocol(transport));
		
		System.out.println("transport len=" + transport.length());
//		System.out.println("transportContent=" + new String(transport.getArray(), 0, transport.length()));
		
		TMemoryInputTransport inputTransport = new TMemoryInputTransport(
				transport.getArray(), 0, transport.length());
		TProtocol inProtocol = new TBinaryProtocol.Factory().getProtocol(inputTransport);
		
		TMemoryBuffer outTransport = new TMemoryBuffer(1024);
		TProtocol outProtocol = new TCompactProtocol.Factory().getProtocol(outTransport);
		
		new ProtocolTransfer(inProtocol, outProtocol).startTransfer(TType.STRUCT);
		
		System.out.println("outTransport len=" + outTransport.length());
		
		TMemoryInputTransport recoveryTransport = new TMemoryInputTransport(
				outTransport.getArray(), 0, outTransport.length());
		TProtocol recoveryProtocol =  new TCompactProtocol.Factory().getProtocol(recoveryTransport);
		
		PlatformArgs recoveryArgs = new PlatformArgs();
		recoveryArgs.read(recoveryProtocol);
		
		System.out.println(recoveryArgs);
	}*/
}
