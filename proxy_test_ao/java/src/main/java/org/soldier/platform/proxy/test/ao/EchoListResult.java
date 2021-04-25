/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.proxy.test.ao;

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

public class EchoListResult implements org.apache.thrift.TBase<EchoListResult, EchoListResult._Fields>, java.io.Serializable, Cloneable, Comparable<EchoListResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EchoListResult");

  private static final org.apache.thrift.protocol.TField CONTENT_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("contentList", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new EchoListResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new EchoListResultTupleSchemeFactory());
  }

  public List<String> contentList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CONTENT_LIST((short)1, "contentList");

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
        case 1: // CONTENT_LIST
          return CONTENT_LIST;
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
  private _Fields optionals[] = {_Fields.CONTENT_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CONTENT_LIST, new org.apache.thrift.meta_data.FieldMetaData("contentList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EchoListResult.class, metaDataMap);
  }

  public EchoListResult() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EchoListResult(EchoListResult other) {
    if (other.isSetContentList()) {
      List<String> __this__contentList = new ArrayList<String>(other.contentList);
      this.contentList = __this__contentList;
    }
  }

  public EchoListResult deepCopy() {
    return new EchoListResult(this);
  }

  @Override
  public void clear() {
    this.contentList = null;
  }

  public int getContentListSize() {
    return (this.contentList == null) ? 0 : this.contentList.size();
  }

  public java.util.Iterator<String> getContentListIterator() {
    return (this.contentList == null) ? null : this.contentList.iterator();
  }

  public void addToContentList(String elem) {
    if (this.contentList == null) {
      this.contentList = new ArrayList<String>();
    }
    this.contentList.add(elem);
  }

  public List<String> getContentList() {
    return this.contentList;
  }

  public EchoListResult setContentList(List<String> contentList) {
    this.contentList = contentList;
    return this;
  }

  public void unsetContentList() {
    this.contentList = null;
  }

  /** Returns true if field contentList is set (has been assigned a value) and false otherwise */
  public boolean isSetContentList() {
    return this.contentList != null;
  }

  public void setContentListIsSet(boolean value) {
    if (!value) {
      this.contentList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CONTENT_LIST:
      if (value == null) {
        unsetContentList();
      } else {
        setContentList((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CONTENT_LIST:
      return getContentList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CONTENT_LIST:
      return isSetContentList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof EchoListResult)
      return this.equals((EchoListResult)that);
    return false;
  }

  public boolean equals(EchoListResult that) {
    if (that == null)
      return false;

    boolean this_present_contentList = true && this.isSetContentList();
    boolean that_present_contentList = true && that.isSetContentList();
    if (this_present_contentList || that_present_contentList) {
      if (!(this_present_contentList && that_present_contentList))
        return false;
      if (!this.contentList.equals(that.contentList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(EchoListResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetContentList()).compareTo(other.isSetContentList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContentList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.contentList, other.contentList);
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
    StringBuilder sb = new StringBuilder("EchoListResult(");
    boolean first = true;

    if (isSetContentList()) {
      sb.append("contentList:");
      if (this.contentList == null) {
        sb.append("null");
      } else {
        sb.append(this.contentList);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class EchoListResultStandardSchemeFactory implements SchemeFactory {
    public EchoListResultStandardScheme getScheme() {
      return new EchoListResultStandardScheme();
    }
  }

  private static class EchoListResultStandardScheme extends StandardScheme<EchoListResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EchoListResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CONTENT_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.contentList = new ArrayList<String>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  String _elem2;
                  _elem2 = iprot.readString();
                  struct.contentList.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setContentListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EchoListResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.contentList != null) {
        if (struct.isSetContentList()) {
          oprot.writeFieldBegin(CONTENT_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.contentList.size()));
            for (String _iter3 : struct.contentList)
            {
              oprot.writeString(_iter3);
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

  private static class EchoListResultTupleSchemeFactory implements SchemeFactory {
    public EchoListResultTupleScheme getScheme() {
      return new EchoListResultTupleScheme();
    }
  }

  private static class EchoListResultTupleScheme extends TupleScheme<EchoListResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EchoListResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetContentList()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetContentList()) {
        {
          oprot.writeI32(struct.contentList.size());
          for (String _iter4 : struct.contentList)
          {
            oprot.writeString(_iter4);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EchoListResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.contentList = new ArrayList<String>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            String _elem7;
            _elem7 = iprot.readString();
            struct.contentList.add(_elem7);
          }
        }
        struct.setContentListIsSet(true);
      }
    }
  }

}

