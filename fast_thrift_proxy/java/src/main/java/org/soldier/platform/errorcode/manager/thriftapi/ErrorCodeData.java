/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.errorcode.manager.thriftapi;

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

/**
 * 总错误信息
 */
public class ErrorCodeData implements org.apache.thrift.TBase<ErrorCodeData, ErrorCodeData._Fields>, java.io.Serializable, Cloneable, Comparable<ErrorCodeData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ErrorCodeData");

  private static final org.apache.thrift.protocol.TField VERSION_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("versionCode", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField DOMAIN_MAP_FIELD_DESC = new org.apache.thrift.protocol.TField("domainMap", org.apache.thrift.protocol.TType.MAP, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ErrorCodeDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ErrorCodeDataTupleSchemeFactory());
  }

  public long versionCode; // optional
  public Map<String,Map<Integer,ErrorCodeItem>> domainMap; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VERSION_CODE((short)1, "versionCode"),
    DOMAIN_MAP((short)2, "domainMap");

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
        case 1: // VERSION_CODE
          return VERSION_CODE;
        case 2: // DOMAIN_MAP
          return DOMAIN_MAP;
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
  private static final int __VERSIONCODE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.VERSION_CODE,_Fields.DOMAIN_MAP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VERSION_CODE, new org.apache.thrift.meta_data.FieldMetaData("versionCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DOMAIN_MAP, new org.apache.thrift.meta_data.FieldMetaData("domainMap", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32), 
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ErrorCodeItem.class)))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ErrorCodeData.class, metaDataMap);
  }

  public ErrorCodeData() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ErrorCodeData(ErrorCodeData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.versionCode = other.versionCode;
    if (other.isSetDomainMap()) {
      Map<String,Map<Integer,ErrorCodeItem>> __this__domainMap = new HashMap<String,Map<Integer,ErrorCodeItem>>(other.domainMap.size());
      for (Map.Entry<String, Map<Integer,ErrorCodeItem>> other_element : other.domainMap.entrySet()) {

        String other_element_key = other_element.getKey();
        Map<Integer,ErrorCodeItem> other_element_value = other_element.getValue();

        String __this__domainMap_copy_key = other_element_key;

        Map<Integer,ErrorCodeItem> __this__domainMap_copy_value = new HashMap<Integer,ErrorCodeItem>(other_element_value.size());
        for (Map.Entry<Integer, ErrorCodeItem> other_element_value_element : other_element_value.entrySet()) {

          Integer other_element_value_element_key = other_element_value_element.getKey();
          ErrorCodeItem other_element_value_element_value = other_element_value_element.getValue();

          Integer __this__domainMap_copy_value_copy_key = other_element_value_element_key;

          ErrorCodeItem __this__domainMap_copy_value_copy_value = new ErrorCodeItem(other_element_value_element_value);

          __this__domainMap_copy_value.put(__this__domainMap_copy_value_copy_key, __this__domainMap_copy_value_copy_value);
        }

        __this__domainMap.put(__this__domainMap_copy_key, __this__domainMap_copy_value);
      }
      this.domainMap = __this__domainMap;
    }
  }

  public ErrorCodeData deepCopy() {
    return new ErrorCodeData(this);
  }

  @Override
  public void clear() {
    setVersionCodeIsSet(false);
    this.versionCode = 0;
    this.domainMap = null;
  }

  public long getVersionCode() {
    return this.versionCode;
  }

  public ErrorCodeData setVersionCode(long versionCode) {
    this.versionCode = versionCode;
    setVersionCodeIsSet(true);
    return this;
  }

  public void unsetVersionCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VERSIONCODE_ISSET_ID);
  }

  /** Returns true if field versionCode is set (has been assigned a value) and false otherwise */
  public boolean isSetVersionCode() {
    return EncodingUtils.testBit(__isset_bitfield, __VERSIONCODE_ISSET_ID);
  }

  public void setVersionCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VERSIONCODE_ISSET_ID, value);
  }

  public int getDomainMapSize() {
    return (this.domainMap == null) ? 0 : this.domainMap.size();
  }

  public void putToDomainMap(String key, Map<Integer,ErrorCodeItem> val) {
    if (this.domainMap == null) {
      this.domainMap = new HashMap<String,Map<Integer,ErrorCodeItem>>();
    }
    this.domainMap.put(key, val);
  }

  public Map<String,Map<Integer,ErrorCodeItem>> getDomainMap() {
    return this.domainMap;
  }

  public ErrorCodeData setDomainMap(Map<String,Map<Integer,ErrorCodeItem>> domainMap) {
    this.domainMap = domainMap;
    return this;
  }

  public void unsetDomainMap() {
    this.domainMap = null;
  }

  /** Returns true if field domainMap is set (has been assigned a value) and false otherwise */
  public boolean isSetDomainMap() {
    return this.domainMap != null;
  }

  public void setDomainMapIsSet(boolean value) {
    if (!value) {
      this.domainMap = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VERSION_CODE:
      if (value == null) {
        unsetVersionCode();
      } else {
        setVersionCode((Long)value);
      }
      break;

    case DOMAIN_MAP:
      if (value == null) {
        unsetDomainMap();
      } else {
        setDomainMap((Map<String,Map<Integer,ErrorCodeItem>>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VERSION_CODE:
      return Long.valueOf(getVersionCode());

    case DOMAIN_MAP:
      return getDomainMap();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VERSION_CODE:
      return isSetVersionCode();
    case DOMAIN_MAP:
      return isSetDomainMap();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ErrorCodeData)
      return this.equals((ErrorCodeData)that);
    return false;
  }

  public boolean equals(ErrorCodeData that) {
    if (that == null)
      return false;

    boolean this_present_versionCode = true && this.isSetVersionCode();
    boolean that_present_versionCode = true && that.isSetVersionCode();
    if (this_present_versionCode || that_present_versionCode) {
      if (!(this_present_versionCode && that_present_versionCode))
        return false;
      if (this.versionCode != that.versionCode)
        return false;
    }

    boolean this_present_domainMap = true && this.isSetDomainMap();
    boolean that_present_domainMap = true && that.isSetDomainMap();
    if (this_present_domainMap || that_present_domainMap) {
      if (!(this_present_domainMap && that_present_domainMap))
        return false;
      if (!this.domainMap.equals(that.domainMap))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ErrorCodeData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetVersionCode()).compareTo(other.isSetVersionCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersionCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.versionCode, other.versionCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDomainMap()).compareTo(other.isSetDomainMap());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDomainMap()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.domainMap, other.domainMap);
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
    StringBuilder sb = new StringBuilder("ErrorCodeData(");
    boolean first = true;

    if (isSetVersionCode()) {
      sb.append("versionCode:");
      sb.append(this.versionCode);
      first = false;
    }
    if (isSetDomainMap()) {
      if (!first) sb.append(", ");
      sb.append("domainMap:");
      if (this.domainMap == null) {
        sb.append("null");
      } else {
        sb.append(this.domainMap);
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

  private static class ErrorCodeDataStandardSchemeFactory implements SchemeFactory {
    public ErrorCodeDataStandardScheme getScheme() {
      return new ErrorCodeDataStandardScheme();
    }
  }

  private static class ErrorCodeDataStandardScheme extends StandardScheme<ErrorCodeData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ErrorCodeData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VERSION_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.versionCode = iprot.readI64();
              struct.setVersionCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DOMAIN_MAP
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map40 = iprot.readMapBegin();
                struct.domainMap = new HashMap<String,Map<Integer,ErrorCodeItem>>(2*_map40.size);
                for (int _i41 = 0; _i41 < _map40.size; ++_i41)
                {
                  String _key42;
                  Map<Integer,ErrorCodeItem> _val43;
                  _key42 = iprot.readString();
                  {
                    org.apache.thrift.protocol.TMap _map44 = iprot.readMapBegin();
                    _val43 = new HashMap<Integer,ErrorCodeItem>(2*_map44.size);
                    for (int _i45 = 0; _i45 < _map44.size; ++_i45)
                    {
                      int _key46;
                      ErrorCodeItem _val47;
                      _key46 = iprot.readI32();
                      _val47 = new ErrorCodeItem();
                      _val47.read(iprot);
                      _val43.put(_key46, _val47);
                    }
                    iprot.readMapEnd();
                  }
                  struct.domainMap.put(_key42, _val43);
                }
                iprot.readMapEnd();
              }
              struct.setDomainMapIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ErrorCodeData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetVersionCode()) {
        oprot.writeFieldBegin(VERSION_CODE_FIELD_DESC);
        oprot.writeI64(struct.versionCode);
        oprot.writeFieldEnd();
      }
      if (struct.domainMap != null) {
        if (struct.isSetDomainMap()) {
          oprot.writeFieldBegin(DOMAIN_MAP_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, struct.domainMap.size()));
            for (Map.Entry<String, Map<Integer,ErrorCodeItem>> _iter48 : struct.domainMap.entrySet())
            {
              oprot.writeString(_iter48.getKey());
              {
                oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I32, org.apache.thrift.protocol.TType.STRUCT, _iter48.getValue().size()));
                for (Map.Entry<Integer, ErrorCodeItem> _iter49 : _iter48.getValue().entrySet())
                {
                  oprot.writeI32(_iter49.getKey());
                  _iter49.getValue().write(oprot);
                }
                oprot.writeMapEnd();
              }
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ErrorCodeDataTupleSchemeFactory implements SchemeFactory {
    public ErrorCodeDataTupleScheme getScheme() {
      return new ErrorCodeDataTupleScheme();
    }
  }

  private static class ErrorCodeDataTupleScheme extends TupleScheme<ErrorCodeData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ErrorCodeData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetVersionCode()) {
        optionals.set(0);
      }
      if (struct.isSetDomainMap()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetVersionCode()) {
        oprot.writeI64(struct.versionCode);
      }
      if (struct.isSetDomainMap()) {
        {
          oprot.writeI32(struct.domainMap.size());
          for (Map.Entry<String, Map<Integer,ErrorCodeItem>> _iter50 : struct.domainMap.entrySet())
          {
            oprot.writeString(_iter50.getKey());
            {
              oprot.writeI32(_iter50.getValue().size());
              for (Map.Entry<Integer, ErrorCodeItem> _iter51 : _iter50.getValue().entrySet())
              {
                oprot.writeI32(_iter51.getKey());
                _iter51.getValue().write(oprot);
              }
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ErrorCodeData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.versionCode = iprot.readI64();
        struct.setVersionCodeIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TMap _map52 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, iprot.readI32());
          struct.domainMap = new HashMap<String,Map<Integer,ErrorCodeItem>>(2*_map52.size);
          for (int _i53 = 0; _i53 < _map52.size; ++_i53)
          {
            String _key54;
            Map<Integer,ErrorCodeItem> _val55;
            _key54 = iprot.readString();
            {
              org.apache.thrift.protocol.TMap _map56 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I32, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
              _val55 = new HashMap<Integer,ErrorCodeItem>(2*_map56.size);
              for (int _i57 = 0; _i57 < _map56.size; ++_i57)
              {
                int _key58;
                ErrorCodeItem _val59;
                _key58 = iprot.readI32();
                _val59 = new ErrorCodeItem();
                _val59.read(iprot);
                _val55.put(_key58, _val59);
              }
            }
            struct.domainMap.put(_key54, _val55);
          }
        }
        struct.setDomainMapIsSet(true);
      }
    }
  }

}

