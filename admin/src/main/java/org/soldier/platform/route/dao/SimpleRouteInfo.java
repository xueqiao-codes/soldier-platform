/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.route.dao;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRouteInfo implements org.apache.thrift.TBase<SimpleRouteInfo, SimpleRouteInfo._Fields>, java.io.Serializable, Cloneable, Comparable<SimpleRouteInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SimpleRouteInfo");

  private static final org.apache.thrift.protocol.TField SERVICE_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("serviceKey", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField IP_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("ipList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SimpleRouteInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SimpleRouteInfoTupleSchemeFactory());
  }

  public int serviceKey; // required
  public List<Long> ipList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SERVICE_KEY((short)1, "serviceKey"),
    IP_LIST((short)2, "ipList");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SERVICE_KEY
          return SERVICE_KEY;
        case 2: // IP_LIST
          return IP_LIST;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SERVICEKEY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SERVICE_KEY, new org.apache.thrift.meta_data.FieldMetaData("serviceKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.IP_LIST, new org.apache.thrift.meta_data.FieldMetaData("ipList", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SimpleRouteInfo.class, metaDataMap);
  }

  public SimpleRouteInfo() {
  }

  public SimpleRouteInfo(
    int serviceKey,
    List<Long> ipList)
  {
    this();
    this.serviceKey = serviceKey;
    setServiceKeyIsSet(true);
    this.ipList = ipList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SimpleRouteInfo(SimpleRouteInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.serviceKey = other.serviceKey;
    if (other.isSetIpList()) {
      List<Long> __this__ipList = new ArrayList<Long>(other.ipList);
      this.ipList = __this__ipList;
    }
  }

  public SimpleRouteInfo deepCopy() {
    return new SimpleRouteInfo(this);
  }

  @Override
  public void clear() {
    setServiceKeyIsSet(false);
    this.serviceKey = 0;
    this.ipList = null;
  }

  public int getServiceKey() {
    return this.serviceKey;
  }

  public SimpleRouteInfo setServiceKey(int serviceKey) {
    this.serviceKey = serviceKey;
    setServiceKeyIsSet(true);
    return this;
  }

  public void unsetServiceKey() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SERVICEKEY_ISSET_ID);
  }

  /** Returns true if field serviceKey is set (has been assigned a value) and false otherwise */
  public boolean isSetServiceKey() {
    return EncodingUtils.testBit(__isset_bitfield, __SERVICEKEY_ISSET_ID);
  }

  public void setServiceKeyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SERVICEKEY_ISSET_ID, value);
  }

  public int getIpListSize() {
    return (this.ipList == null) ? 0 : this.ipList.size();
  }

  public java.util.Iterator<Long> getIpListIterator() {
    return (this.ipList == null) ? null : this.ipList.iterator();
  }

  public void addToIpList(long elem) {
    if (this.ipList == null) {
      this.ipList = new ArrayList<Long>();
    }
    this.ipList.add(elem);
  }

  public List<Long> getIpList() {
    return this.ipList;
  }

  public SimpleRouteInfo setIpList(List<Long> ipList) {
    this.ipList = ipList;
    return this;
  }

  public void unsetIpList() {
    this.ipList = null;
  }

  /** Returns true if field ipList is set (has been assigned a value) and false otherwise */
  public boolean isSetIpList() {
    return this.ipList != null;
  }

  public void setIpListIsSet(boolean value) {
    if (!value) {
      this.ipList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SERVICE_KEY:
      if (value == null) {
        unsetServiceKey();
      } else {
        setServiceKey((Integer)value);
      }
      break;

    case IP_LIST:
      if (value == null) {
        unsetIpList();
      } else {
        setIpList((List<Long>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SERVICE_KEY:
      return Integer.valueOf(getServiceKey());

    case IP_LIST:
      return getIpList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SERVICE_KEY:
      return isSetServiceKey();
    case IP_LIST:
      return isSetIpList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SimpleRouteInfo)
      return this.equals((SimpleRouteInfo)that);
    return false;
  }

  public boolean equals(SimpleRouteInfo that) {
    if (that == null)
      return false;

    boolean this_present_serviceKey = true;
    boolean that_present_serviceKey = true;
    if (this_present_serviceKey || that_present_serviceKey) {
      if (!(this_present_serviceKey && that_present_serviceKey))
        return false;
      if (this.serviceKey != that.serviceKey)
        return false;
    }

    boolean this_present_ipList = true && this.isSetIpList();
    boolean that_present_ipList = true && that.isSetIpList();
    if (this_present_ipList || that_present_ipList) {
      if (!(this_present_ipList && that_present_ipList))
        return false;
      if (!this.ipList.equals(that.ipList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(SimpleRouteInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetServiceKey()).compareTo(other.isSetServiceKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServiceKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serviceKey, other.serviceKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIpList()).compareTo(other.isSetIpList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIpList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ipList, other.ipList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SimpleRouteInfo(");
    boolean first = true;

    sb.append("serviceKey:");
    sb.append(this.serviceKey);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ipList:");
    if (this.ipList == null) {
      sb.append("null");
    } else {
      sb.append(this.ipList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'serviceKey' because it's a primitive and you chose the non-beans generator.
    if (ipList == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'ipList' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SimpleRouteInfoStandardSchemeFactory implements SchemeFactory {
    public SimpleRouteInfoStandardScheme getScheme() {
      return new SimpleRouteInfoStandardScheme();
    }
  }

  private static class SimpleRouteInfoStandardScheme extends StandardScheme<SimpleRouteInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SimpleRouteInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SERVICE_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.serviceKey = iprot.readI32();
              struct.setServiceKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IP_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list56 = iprot.readListBegin();
                struct.ipList = new ArrayList<Long>(_list56.size);
                for (int _i57 = 0; _i57 < _list56.size; ++_i57)
                {
                  long _elem58;
                  _elem58 = iprot.readI64();
                  struct.ipList.add(_elem58);
                }
                iprot.readListEnd();
              }
              struct.setIpListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetServiceKey()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'serviceKey' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SimpleRouteInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SERVICE_KEY_FIELD_DESC);
      oprot.writeI32(struct.serviceKey);
      oprot.writeFieldEnd();
      if (struct.ipList != null) {
        oprot.writeFieldBegin(IP_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.ipList.size()));
          for (long _iter59 : struct.ipList)
          {
            oprot.writeI64(_iter59);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SimpleRouteInfoTupleSchemeFactory implements SchemeFactory {
    public SimpleRouteInfoTupleScheme getScheme() {
      return new SimpleRouteInfoTupleScheme();
    }
  }

  private static class SimpleRouteInfoTupleScheme extends TupleScheme<SimpleRouteInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SimpleRouteInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.serviceKey);
      {
        oprot.writeI32(struct.ipList.size());
        for (long _iter60 : struct.ipList)
        {
          oprot.writeI64(_iter60);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SimpleRouteInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.serviceKey = iprot.readI32();
      struct.setServiceKeyIsSet(true);
      {
        org.apache.thrift.protocol.TList _list61 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
        struct.ipList = new ArrayList<Long>(_list61.size);
        for (int _i62 = 0; _i62 < _list61.size; ++_i62)
        {
          long _elem63;
          _elem63 = iprot.readI64();
          struct.ipList.add(_elem63);
        }
      }
      struct.setIpListIsSet(true);
    }
  }

}

