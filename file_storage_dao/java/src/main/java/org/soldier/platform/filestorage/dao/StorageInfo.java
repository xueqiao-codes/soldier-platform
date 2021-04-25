/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.filestorage.dao;

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

public class StorageInfo implements org.apache.thrift.TBase<StorageInfo, StorageInfo._Fields>, java.io.Serializable, Cloneable, Comparable<StorageInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StorageInfo");

  private static final org.apache.thrift.protocol.TField STORAGE_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("storageKey", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ACCESS_ATTRIBUTE_FIELD_DESC = new org.apache.thrift.protocol.TField("accessAttribute", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField DOMAIN_FIELD_DESC = new org.apache.thrift.protocol.TField("domain", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestamp", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestamp", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StorageInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StorageInfoTupleSchemeFactory());
  }

  public String storageKey; // required
  /**
   * 
   * @see AccessAttribute
   */
  public AccessAttribute accessAttribute; // optional
  public String domain; // optional
  public int createTimestamp; // optional
  public int lastmodifyTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    STORAGE_KEY((short)1, "storageKey"),
    /**
     * 
     * @see AccessAttribute
     */
    ACCESS_ATTRIBUTE((short)2, "accessAttribute"),
    DOMAIN((short)3, "domain"),
    CREATE_TIMESTAMP((short)4, "createTimestamp"),
    LASTMODIFY_TIMESTAMP((short)5, "lastmodifyTimestamp");

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
        case 1: // STORAGE_KEY
          return STORAGE_KEY;
        case 2: // ACCESS_ATTRIBUTE
          return ACCESS_ATTRIBUTE;
        case 3: // DOMAIN
          return DOMAIN;
        case 4: // CREATE_TIMESTAMP
          return CREATE_TIMESTAMP;
        case 5: // LASTMODIFY_TIMESTAMP
          return LASTMODIFY_TIMESTAMP;
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
  private static final int __CREATETIMESTAMP_ISSET_ID = 0;
  private static final int __LASTMODIFYTIMESTAMP_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.ACCESS_ATTRIBUTE,_Fields.DOMAIN,_Fields.CREATE_TIMESTAMP,_Fields.LASTMODIFY_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STORAGE_KEY, new org.apache.thrift.meta_data.FieldMetaData("storageKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACCESS_ATTRIBUTE, new org.apache.thrift.meta_data.FieldMetaData("accessAttribute", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, AccessAttribute.class)));
    tmpMap.put(_Fields.DOMAIN, new org.apache.thrift.meta_data.FieldMetaData("domain", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("createTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StorageInfo.class, metaDataMap);
  }

  public StorageInfo() {
  }

  public StorageInfo(
    String storageKey)
  {
    this();
    this.storageKey = storageKey;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StorageInfo(StorageInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetStorageKey()) {
      this.storageKey = other.storageKey;
    }
    if (other.isSetAccessAttribute()) {
      this.accessAttribute = other.accessAttribute;
    }
    if (other.isSetDomain()) {
      this.domain = other.domain;
    }
    this.createTimestamp = other.createTimestamp;
    this.lastmodifyTimestamp = other.lastmodifyTimestamp;
  }

  public StorageInfo deepCopy() {
    return new StorageInfo(this);
  }

  @Override
  public void clear() {
    this.storageKey = null;
    this.accessAttribute = null;
    this.domain = null;
    setCreateTimestampIsSet(false);
    this.createTimestamp = 0;
    setLastmodifyTimestampIsSet(false);
    this.lastmodifyTimestamp = 0;
  }

  public String getStorageKey() {
    return this.storageKey;
  }

  public StorageInfo setStorageKey(String storageKey) {
    this.storageKey = storageKey;
    return this;
  }

  public void unsetStorageKey() {
    this.storageKey = null;
  }

  /** Returns true if field storageKey is set (has been assigned a value) and false otherwise */
  public boolean isSetStorageKey() {
    return this.storageKey != null;
  }

  public void setStorageKeyIsSet(boolean value) {
    if (!value) {
      this.storageKey = null;
    }
  }

  /**
   * 
   * @see AccessAttribute
   */
  public AccessAttribute getAccessAttribute() {
    return this.accessAttribute;
  }

  /**
   * 
   * @see AccessAttribute
   */
  public StorageInfo setAccessAttribute(AccessAttribute accessAttribute) {
    this.accessAttribute = accessAttribute;
    return this;
  }

  public void unsetAccessAttribute() {
    this.accessAttribute = null;
  }

  /** Returns true if field accessAttribute is set (has been assigned a value) and false otherwise */
  public boolean isSetAccessAttribute() {
    return this.accessAttribute != null;
  }

  public void setAccessAttributeIsSet(boolean value) {
    if (!value) {
      this.accessAttribute = null;
    }
  }

  public String getDomain() {
    return this.domain;
  }

  public StorageInfo setDomain(String domain) {
    this.domain = domain;
    return this;
  }

  public void unsetDomain() {
    this.domain = null;
  }

  /** Returns true if field domain is set (has been assigned a value) and false otherwise */
  public boolean isSetDomain() {
    return this.domain != null;
  }

  public void setDomainIsSet(boolean value) {
    if (!value) {
      this.domain = null;
    }
  }

  public int getCreateTimestamp() {
    return this.createTimestamp;
  }

  public StorageInfo setCreateTimestamp(int createTimestamp) {
    this.createTimestamp = createTimestamp;
    setCreateTimestampIsSet(true);
    return this;
  }

  public void unsetCreateTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATETIMESTAMP_ISSET_ID);
  }

  /** Returns true if field createTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATETIMESTAMP_ISSET_ID);
  }

  public void setCreateTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATETIMESTAMP_ISSET_ID, value);
  }

  public int getLastmodifyTimestamp() {
    return this.lastmodifyTimestamp;
  }

  public StorageInfo setLastmodifyTimestamp(int lastmodifyTimestamp) {
    this.lastmodifyTimestamp = lastmodifyTimestamp;
    setLastmodifyTimestampIsSet(true);
    return this;
  }

  public void unsetLastmodifyTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTMODIFYTIMESTAMP_ISSET_ID);
  }

  /** Returns true if field lastmodifyTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetLastmodifyTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTMODIFYTIMESTAMP_ISSET_ID);
  }

  public void setLastmodifyTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTMODIFYTIMESTAMP_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STORAGE_KEY:
      if (value == null) {
        unsetStorageKey();
      } else {
        setStorageKey((String)value);
      }
      break;

    case ACCESS_ATTRIBUTE:
      if (value == null) {
        unsetAccessAttribute();
      } else {
        setAccessAttribute((AccessAttribute)value);
      }
      break;

    case DOMAIN:
      if (value == null) {
        unsetDomain();
      } else {
        setDomain((String)value);
      }
      break;

    case CREATE_TIMESTAMP:
      if (value == null) {
        unsetCreateTimestamp();
      } else {
        setCreateTimestamp((Integer)value);
      }
      break;

    case LASTMODIFY_TIMESTAMP:
      if (value == null) {
        unsetLastmodifyTimestamp();
      } else {
        setLastmodifyTimestamp((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STORAGE_KEY:
      return getStorageKey();

    case ACCESS_ATTRIBUTE:
      return getAccessAttribute();

    case DOMAIN:
      return getDomain();

    case CREATE_TIMESTAMP:
      return Integer.valueOf(getCreateTimestamp());

    case LASTMODIFY_TIMESTAMP:
      return Integer.valueOf(getLastmodifyTimestamp());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STORAGE_KEY:
      return isSetStorageKey();
    case ACCESS_ATTRIBUTE:
      return isSetAccessAttribute();
    case DOMAIN:
      return isSetDomain();
    case CREATE_TIMESTAMP:
      return isSetCreateTimestamp();
    case LASTMODIFY_TIMESTAMP:
      return isSetLastmodifyTimestamp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StorageInfo)
      return this.equals((StorageInfo)that);
    return false;
  }

  public boolean equals(StorageInfo that) {
    if (that == null)
      return false;

    boolean this_present_storageKey = true && this.isSetStorageKey();
    boolean that_present_storageKey = true && that.isSetStorageKey();
    if (this_present_storageKey || that_present_storageKey) {
      if (!(this_present_storageKey && that_present_storageKey))
        return false;
      if (!this.storageKey.equals(that.storageKey))
        return false;
    }

    boolean this_present_accessAttribute = true && this.isSetAccessAttribute();
    boolean that_present_accessAttribute = true && that.isSetAccessAttribute();
    if (this_present_accessAttribute || that_present_accessAttribute) {
      if (!(this_present_accessAttribute && that_present_accessAttribute))
        return false;
      if (!this.accessAttribute.equals(that.accessAttribute))
        return false;
    }

    boolean this_present_domain = true && this.isSetDomain();
    boolean that_present_domain = true && that.isSetDomain();
    if (this_present_domain || that_present_domain) {
      if (!(this_present_domain && that_present_domain))
        return false;
      if (!this.domain.equals(that.domain))
        return false;
    }

    boolean this_present_createTimestamp = true && this.isSetCreateTimestamp();
    boolean that_present_createTimestamp = true && that.isSetCreateTimestamp();
    if (this_present_createTimestamp || that_present_createTimestamp) {
      if (!(this_present_createTimestamp && that_present_createTimestamp))
        return false;
      if (this.createTimestamp != that.createTimestamp)
        return false;
    }

    boolean this_present_lastmodifyTimestamp = true && this.isSetLastmodifyTimestamp();
    boolean that_present_lastmodifyTimestamp = true && that.isSetLastmodifyTimestamp();
    if (this_present_lastmodifyTimestamp || that_present_lastmodifyTimestamp) {
      if (!(this_present_lastmodifyTimestamp && that_present_lastmodifyTimestamp))
        return false;
      if (this.lastmodifyTimestamp != that.lastmodifyTimestamp)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StorageInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetStorageKey()).compareTo(other.isSetStorageKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStorageKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.storageKey, other.storageKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAccessAttribute()).compareTo(other.isSetAccessAttribute());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccessAttribute()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.accessAttribute, other.accessAttribute);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDomain()).compareTo(other.isSetDomain());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDomain()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.domain, other.domain);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTimestamp()).compareTo(other.isSetCreateTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTimestamp, other.createTimestamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLastmodifyTimestamp()).compareTo(other.isSetLastmodifyTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastmodifyTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastmodifyTimestamp, other.lastmodifyTimestamp);
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
    StringBuilder sb = new StringBuilder("StorageInfo(");
    boolean first = true;

    sb.append("storageKey:");
    if (this.storageKey == null) {
      sb.append("null");
    } else {
      sb.append(this.storageKey);
    }
    first = false;
    if (isSetAccessAttribute()) {
      if (!first) sb.append(", ");
      sb.append("accessAttribute:");
      if (this.accessAttribute == null) {
        sb.append("null");
      } else {
        sb.append(this.accessAttribute);
      }
      first = false;
    }
    if (isSetDomain()) {
      if (!first) sb.append(", ");
      sb.append("domain:");
      if (this.domain == null) {
        sb.append("null");
      } else {
        sb.append(this.domain);
      }
      first = false;
    }
    if (isSetCreateTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("createTimestamp:");
      sb.append(this.createTimestamp);
      first = false;
    }
    if (isSetLastmodifyTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("lastmodifyTimestamp:");
      sb.append(this.lastmodifyTimestamp);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (storageKey == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'storageKey' was not present! Struct: " + toString());
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

  private static class StorageInfoStandardSchemeFactory implements SchemeFactory {
    public StorageInfoStandardScheme getScheme() {
      return new StorageInfoStandardScheme();
    }
  }

  private static class StorageInfoStandardScheme extends StandardScheme<StorageInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StorageInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STORAGE_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.storageKey = iprot.readString();
              struct.setStorageKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ACCESS_ATTRIBUTE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.accessAttribute = AccessAttribute.findByValue(iprot.readI32());
              struct.setAccessAttributeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DOMAIN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.domain = iprot.readString();
              struct.setDomainIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CREATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTimestamp = iprot.readI32();
              struct.setCreateTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // LASTMODIFY_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lastmodifyTimestamp = iprot.readI32();
              struct.setLastmodifyTimestampIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StorageInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.storageKey != null) {
        oprot.writeFieldBegin(STORAGE_KEY_FIELD_DESC);
        oprot.writeString(struct.storageKey);
        oprot.writeFieldEnd();
      }
      if (struct.accessAttribute != null) {
        if (struct.isSetAccessAttribute()) {
          oprot.writeFieldBegin(ACCESS_ATTRIBUTE_FIELD_DESC);
          oprot.writeI32(struct.accessAttribute.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.domain != null) {
        if (struct.isSetDomain()) {
          oprot.writeFieldBegin(DOMAIN_FIELD_DESC);
          oprot.writeString(struct.domain);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_FIELD_DESC);
        oprot.writeI32(struct.createTimestamp);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLastmodifyTimestamp()) {
        oprot.writeFieldBegin(LASTMODIFY_TIMESTAMP_FIELD_DESC);
        oprot.writeI32(struct.lastmodifyTimestamp);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StorageInfoTupleSchemeFactory implements SchemeFactory {
    public StorageInfoTupleScheme getScheme() {
      return new StorageInfoTupleScheme();
    }
  }

  private static class StorageInfoTupleScheme extends TupleScheme<StorageInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StorageInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.storageKey);
      BitSet optionals = new BitSet();
      if (struct.isSetAccessAttribute()) {
        optionals.set(0);
      }
      if (struct.isSetDomain()) {
        optionals.set(1);
      }
      if (struct.isSetCreateTimestamp()) {
        optionals.set(2);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetAccessAttribute()) {
        oprot.writeI32(struct.accessAttribute.getValue());
      }
      if (struct.isSetDomain()) {
        oprot.writeString(struct.domain);
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeI32(struct.createTimestamp);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        oprot.writeI32(struct.lastmodifyTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StorageInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.storageKey = iprot.readString();
      struct.setStorageKeyIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.accessAttribute = AccessAttribute.findByValue(iprot.readI32());
        struct.setAccessAttributeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.domain = iprot.readString();
        struct.setDomainIsSet(true);
      }
      if (incoming.get(2)) {
        struct.createTimestamp = iprot.readI32();
        struct.setCreateTimestampIsSet(true);
      }
      if (incoming.get(3)) {
        struct.lastmodifyTimestamp = iprot.readI32();
        struct.setLastmodifyTimestampIsSet(true);
      }
    }
  }

}

