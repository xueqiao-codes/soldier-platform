/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.id_maker_dao;

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

public class IdMakerInfoList implements org.apache.thrift.TBase<IdMakerInfoList, IdMakerInfoList._Fields>, java.io.Serializable, Cloneable, Comparable<IdMakerInfoList> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IdMakerInfoList");

  private static final org.apache.thrift.protocol.TField TOTAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("totalCount", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("resultList", org.apache.thrift.protocol.TType.LIST, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new IdMakerInfoListStandardSchemeFactory());
    schemes.put(TupleScheme.class, new IdMakerInfoListTupleSchemeFactory());
  }

  public int totalCount; // optional
  public List<IdMakerInfo> resultList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_COUNT((short)1, "totalCount"),
    RESULT_LIST((short)5, "resultList");

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
        case 1: // TOTAL_COUNT
          return TOTAL_COUNT;
        case 5: // RESULT_LIST
          return RESULT_LIST;
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
  private static final int __TOTALCOUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TOTAL_COUNT,_Fields.RESULT_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("totalCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RESULT_LIST, new org.apache.thrift.meta_data.FieldMetaData("resultList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IdMakerInfo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IdMakerInfoList.class, metaDataMap);
  }

  public IdMakerInfoList() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IdMakerInfoList(IdMakerInfoList other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalCount = other.totalCount;
    if (other.isSetResultList()) {
      List<IdMakerInfo> __this__resultList = new ArrayList<IdMakerInfo>(other.resultList.size());
      for (IdMakerInfo other_element : other.resultList) {
        __this__resultList.add(new IdMakerInfo(other_element));
      }
      this.resultList = __this__resultList;
    }
  }

  public IdMakerInfoList deepCopy() {
    return new IdMakerInfoList(this);
  }

  @Override
  public void clear() {
    setTotalCountIsSet(false);
    this.totalCount = 0;
    this.resultList = null;
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public IdMakerInfoList setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    return this;
  }

  public void unsetTotalCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  /** Returns true if field totalCount is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalCount() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  public void setTotalCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID, value);
  }

  public int getResultListSize() {
    return (this.resultList == null) ? 0 : this.resultList.size();
  }

  public java.util.Iterator<IdMakerInfo> getResultListIterator() {
    return (this.resultList == null) ? null : this.resultList.iterator();
  }

  public void addToResultList(IdMakerInfo elem) {
    if (this.resultList == null) {
      this.resultList = new ArrayList<IdMakerInfo>();
    }
    this.resultList.add(elem);
  }

  public List<IdMakerInfo> getResultList() {
    return this.resultList;
  }

  public IdMakerInfoList setResultList(List<IdMakerInfo> resultList) {
    this.resultList = resultList;
    return this;
  }

  public void unsetResultList() {
    this.resultList = null;
  }

  /** Returns true if field resultList is set (has been assigned a value) and false otherwise */
  public boolean isSetResultList() {
    return this.resultList != null;
  }

  public void setResultListIsSet(boolean value) {
    if (!value) {
      this.resultList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOTAL_COUNT:
      if (value == null) {
        unsetTotalCount();
      } else {
        setTotalCount((Integer)value);
      }
      break;

    case RESULT_LIST:
      if (value == null) {
        unsetResultList();
      } else {
        setResultList((List<IdMakerInfo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_COUNT:
      return Integer.valueOf(getTotalCount());

    case RESULT_LIST:
      return getResultList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_COUNT:
      return isSetTotalCount();
    case RESULT_LIST:
      return isSetResultList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof IdMakerInfoList)
      return this.equals((IdMakerInfoList)that);
    return false;
  }

  public boolean equals(IdMakerInfoList that) {
    if (that == null)
      return false;

    boolean this_present_totalCount = true && this.isSetTotalCount();
    boolean that_present_totalCount = true && that.isSetTotalCount();
    if (this_present_totalCount || that_present_totalCount) {
      if (!(this_present_totalCount && that_present_totalCount))
        return false;
      if (this.totalCount != that.totalCount)
        return false;
    }

    boolean this_present_resultList = true && this.isSetResultList();
    boolean that_present_resultList = true && that.isSetResultList();
    if (this_present_resultList || that_present_resultList) {
      if (!(this_present_resultList && that_present_resultList))
        return false;
      if (!this.resultList.equals(that.resultList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(IdMakerInfoList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTotalCount()).compareTo(other.isSetTotalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalCount, other.totalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResultList()).compareTo(other.isSetResultList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResultList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resultList, other.resultList);
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
    StringBuilder sb = new StringBuilder("IdMakerInfoList(");
    boolean first = true;

    if (isSetTotalCount()) {
      sb.append("totalCount:");
      sb.append(this.totalCount);
      first = false;
    }
    if (isSetResultList()) {
      if (!first) sb.append(", ");
      sb.append("resultList:");
      if (this.resultList == null) {
        sb.append("null");
      } else {
        sb.append(this.resultList);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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

  private static class IdMakerInfoListStandardSchemeFactory implements SchemeFactory {
    public IdMakerInfoListStandardScheme getScheme() {
      return new IdMakerInfoListStandardScheme();
    }
  }

  private static class IdMakerInfoListStandardScheme extends StandardScheme<IdMakerInfoList> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, IdMakerInfoList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalCount = iprot.readI32();
              struct.setTotalCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RESULT_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.resultList = new ArrayList<IdMakerInfo>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  IdMakerInfo _elem10;
                  _elem10 = new IdMakerInfo();
                  _elem10.read(iprot);
                  struct.resultList.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setResultListIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, IdMakerInfoList struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalCount()) {
        oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
        oprot.writeI32(struct.totalCount);
        oprot.writeFieldEnd();
      }
      if (struct.resultList != null) {
        if (struct.isSetResultList()) {
          oprot.writeFieldBegin(RESULT_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.resultList.size()));
            for (IdMakerInfo _iter11 : struct.resultList)
            {
              _iter11.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IdMakerInfoListTupleSchemeFactory implements SchemeFactory {
    public IdMakerInfoListTupleScheme getScheme() {
      return new IdMakerInfoListTupleScheme();
    }
  }

  private static class IdMakerInfoListTupleScheme extends TupleScheme<IdMakerInfoList> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IdMakerInfoList struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalCount()) {
        optionals.set(0);
      }
      if (struct.isSetResultList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTotalCount()) {
        oprot.writeI32(struct.totalCount);
      }
      if (struct.isSetResultList()) {
        {
          oprot.writeI32(struct.resultList.size());
          for (IdMakerInfo _iter12 : struct.resultList)
          {
            _iter12.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IdMakerInfoList struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.totalCount = iprot.readI32();
        struct.setTotalCountIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.resultList = new ArrayList<IdMakerInfo>(_list13.size);
          for (int _i14 = 0; _i14 < _list13.size; ++_i14)
          {
            IdMakerInfo _elem15;
            _elem15 = new IdMakerInfo();
            _elem15.read(iprot);
            struct.resultList.add(_elem15);
          }
        }
        struct.setResultListIsSet(true);
      }
    }
  }

}

