/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.dal_set.dao;

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

public class DalSetTable implements org.apache.thrift.TBase<DalSetTable, DalSetTable._Fields>, java.io.Serializable, Cloneable, Comparable<DalSetTable> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DalSetTable");

  private static final org.apache.thrift.protocol.TField PREFIX_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("prefixName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SLICE_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("sliceNum", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField FILL_ZERO_FIELD_DESC = new org.apache.thrift.protocol.TField("fillZero", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("desc", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestamp", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestamp", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DalSetTableStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DalSetTableTupleSchemeFactory());
  }

  public String prefixName; // required
  public int sliceNum; // optional
  public boolean fillZero; // optional
  public String desc; // optional
  public int createTimestamp; // optional
  public int lastmodifyTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PREFIX_NAME((short)1, "prefixName"),
    SLICE_NUM((short)2, "sliceNum"),
    FILL_ZERO((short)3, "fillZero"),
    DESC((short)4, "desc"),
    CREATE_TIMESTAMP((short)5, "createTimestamp"),
    LASTMODIFY_TIMESTAMP((short)6, "lastmodifyTimestamp");

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
        case 1: // PREFIX_NAME
          return PREFIX_NAME;
        case 2: // SLICE_NUM
          return SLICE_NUM;
        case 3: // FILL_ZERO
          return FILL_ZERO;
        case 4: // DESC
          return DESC;
        case 5: // CREATE_TIMESTAMP
          return CREATE_TIMESTAMP;
        case 6: // LASTMODIFY_TIMESTAMP
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
  private static final int __SLICENUM_ISSET_ID = 0;
  private static final int __FILLZERO_ISSET_ID = 1;
  private static final int __CREATETIMESTAMP_ISSET_ID = 2;
  private static final int __LASTMODIFYTIMESTAMP_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SLICE_NUM,_Fields.FILL_ZERO,_Fields.DESC,_Fields.CREATE_TIMESTAMP,_Fields.LASTMODIFY_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PREFIX_NAME, new org.apache.thrift.meta_data.FieldMetaData("prefixName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SLICE_NUM, new org.apache.thrift.meta_data.FieldMetaData("sliceNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FILL_ZERO, new org.apache.thrift.meta_data.FieldMetaData("fillZero", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.DESC, new org.apache.thrift.meta_data.FieldMetaData("desc", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("createTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DalSetTable.class, metaDataMap);
  }

  public DalSetTable() {
  }

  public DalSetTable(
    String prefixName)
  {
    this();
    this.prefixName = prefixName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DalSetTable(DalSetTable other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPrefixName()) {
      this.prefixName = other.prefixName;
    }
    this.sliceNum = other.sliceNum;
    this.fillZero = other.fillZero;
    if (other.isSetDesc()) {
      this.desc = other.desc;
    }
    this.createTimestamp = other.createTimestamp;
    this.lastmodifyTimestamp = other.lastmodifyTimestamp;
  }

  public DalSetTable deepCopy() {
    return new DalSetTable(this);
  }

  @Override
  public void clear() {
    this.prefixName = null;
    setSliceNumIsSet(false);
    this.sliceNum = 0;
    setFillZeroIsSet(false);
    this.fillZero = false;
    this.desc = null;
    setCreateTimestampIsSet(false);
    this.createTimestamp = 0;
    setLastmodifyTimestampIsSet(false);
    this.lastmodifyTimestamp = 0;
  }

  public String getPrefixName() {
    return this.prefixName;
  }

  public DalSetTable setPrefixName(String prefixName) {
    this.prefixName = prefixName;
    return this;
  }

  public void unsetPrefixName() {
    this.prefixName = null;
  }

  /** Returns true if field prefixName is set (has been assigned a value) and false otherwise */
  public boolean isSetPrefixName() {
    return this.prefixName != null;
  }

  public void setPrefixNameIsSet(boolean value) {
    if (!value) {
      this.prefixName = null;
    }
  }

  public int getSliceNum() {
    return this.sliceNum;
  }

  public DalSetTable setSliceNum(int sliceNum) {
    this.sliceNum = sliceNum;
    setSliceNumIsSet(true);
    return this;
  }

  public void unsetSliceNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SLICENUM_ISSET_ID);
  }

  /** Returns true if field sliceNum is set (has been assigned a value) and false otherwise */
  public boolean isSetSliceNum() {
    return EncodingUtils.testBit(__isset_bitfield, __SLICENUM_ISSET_ID);
  }

  public void setSliceNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SLICENUM_ISSET_ID, value);
  }

  public boolean isFillZero() {
    return this.fillZero;
  }

  public DalSetTable setFillZero(boolean fillZero) {
    this.fillZero = fillZero;
    setFillZeroIsSet(true);
    return this;
  }

  public void unsetFillZero() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FILLZERO_ISSET_ID);
  }

  /** Returns true if field fillZero is set (has been assigned a value) and false otherwise */
  public boolean isSetFillZero() {
    return EncodingUtils.testBit(__isset_bitfield, __FILLZERO_ISSET_ID);
  }

  public void setFillZeroIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FILLZERO_ISSET_ID, value);
  }

  public String getDesc() {
    return this.desc;
  }

  public DalSetTable setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  public void unsetDesc() {
    this.desc = null;
  }

  /** Returns true if field desc is set (has been assigned a value) and false otherwise */
  public boolean isSetDesc() {
    return this.desc != null;
  }

  public void setDescIsSet(boolean value) {
    if (!value) {
      this.desc = null;
    }
  }

  public int getCreateTimestamp() {
    return this.createTimestamp;
  }

  public DalSetTable setCreateTimestamp(int createTimestamp) {
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

  public DalSetTable setLastmodifyTimestamp(int lastmodifyTimestamp) {
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
    case PREFIX_NAME:
      if (value == null) {
        unsetPrefixName();
      } else {
        setPrefixName((String)value);
      }
      break;

    case SLICE_NUM:
      if (value == null) {
        unsetSliceNum();
      } else {
        setSliceNum((Integer)value);
      }
      break;

    case FILL_ZERO:
      if (value == null) {
        unsetFillZero();
      } else {
        setFillZero((Boolean)value);
      }
      break;

    case DESC:
      if (value == null) {
        unsetDesc();
      } else {
        setDesc((String)value);
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
    case PREFIX_NAME:
      return getPrefixName();

    case SLICE_NUM:
      return Integer.valueOf(getSliceNum());

    case FILL_ZERO:
      return Boolean.valueOf(isFillZero());

    case DESC:
      return getDesc();

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
    case PREFIX_NAME:
      return isSetPrefixName();
    case SLICE_NUM:
      return isSetSliceNum();
    case FILL_ZERO:
      return isSetFillZero();
    case DESC:
      return isSetDesc();
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
    if (that instanceof DalSetTable)
      return this.equals((DalSetTable)that);
    return false;
  }

  public boolean equals(DalSetTable that) {
    if (that == null)
      return false;

    boolean this_present_prefixName = true && this.isSetPrefixName();
    boolean that_present_prefixName = true && that.isSetPrefixName();
    if (this_present_prefixName || that_present_prefixName) {
      if (!(this_present_prefixName && that_present_prefixName))
        return false;
      if (!this.prefixName.equals(that.prefixName))
        return false;
    }

    boolean this_present_sliceNum = true && this.isSetSliceNum();
    boolean that_present_sliceNum = true && that.isSetSliceNum();
    if (this_present_sliceNum || that_present_sliceNum) {
      if (!(this_present_sliceNum && that_present_sliceNum))
        return false;
      if (this.sliceNum != that.sliceNum)
        return false;
    }

    boolean this_present_fillZero = true && this.isSetFillZero();
    boolean that_present_fillZero = true && that.isSetFillZero();
    if (this_present_fillZero || that_present_fillZero) {
      if (!(this_present_fillZero && that_present_fillZero))
        return false;
      if (this.fillZero != that.fillZero)
        return false;
    }

    boolean this_present_desc = true && this.isSetDesc();
    boolean that_present_desc = true && that.isSetDesc();
    if (this_present_desc || that_present_desc) {
      if (!(this_present_desc && that_present_desc))
        return false;
      if (!this.desc.equals(that.desc))
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
  public int compareTo(DalSetTable other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPrefixName()).compareTo(other.isSetPrefixName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrefixName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.prefixName, other.prefixName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSliceNum()).compareTo(other.isSetSliceNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSliceNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sliceNum, other.sliceNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFillZero()).compareTo(other.isSetFillZero());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFillZero()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fillZero, other.fillZero);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDesc()).compareTo(other.isSetDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.desc, other.desc);
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
    StringBuilder sb = new StringBuilder("DalSetTable(");
    boolean first = true;

    sb.append("prefixName:");
    if (this.prefixName == null) {
      sb.append("null");
    } else {
      sb.append(this.prefixName);
    }
    first = false;
    if (isSetSliceNum()) {
      if (!first) sb.append(", ");
      sb.append("sliceNum:");
      sb.append(this.sliceNum);
      first = false;
    }
    if (isSetFillZero()) {
      if (!first) sb.append(", ");
      sb.append("fillZero:");
      sb.append(this.fillZero);
      first = false;
    }
    if (isSetDesc()) {
      if (!first) sb.append(", ");
      sb.append("desc:");
      if (this.desc == null) {
        sb.append("null");
      } else {
        sb.append(this.desc);
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
    if (prefixName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'prefixName' was not present! Struct: " + toString());
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

  private static class DalSetTableStandardSchemeFactory implements SchemeFactory {
    public DalSetTableStandardScheme getScheme() {
      return new DalSetTableStandardScheme();
    }
  }

  private static class DalSetTableStandardScheme extends StandardScheme<DalSetTable> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DalSetTable struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PREFIX_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.prefixName = iprot.readString();
              struct.setPrefixNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SLICE_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sliceNum = iprot.readI32();
              struct.setSliceNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FILL_ZERO
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.fillZero = iprot.readBool();
              struct.setFillZeroIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.desc = iprot.readString();
              struct.setDescIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTimestamp = iprot.readI32();
              struct.setCreateTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // LASTMODIFY_TIMESTAMP
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DalSetTable struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.prefixName != null) {
        oprot.writeFieldBegin(PREFIX_NAME_FIELD_DESC);
        oprot.writeString(struct.prefixName);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSliceNum()) {
        oprot.writeFieldBegin(SLICE_NUM_FIELD_DESC);
        oprot.writeI32(struct.sliceNum);
        oprot.writeFieldEnd();
      }
      if (struct.isSetFillZero()) {
        oprot.writeFieldBegin(FILL_ZERO_FIELD_DESC);
        oprot.writeBool(struct.fillZero);
        oprot.writeFieldEnd();
      }
      if (struct.desc != null) {
        if (struct.isSetDesc()) {
          oprot.writeFieldBegin(DESC_FIELD_DESC);
          oprot.writeString(struct.desc);
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

  private static class DalSetTableTupleSchemeFactory implements SchemeFactory {
    public DalSetTableTupleScheme getScheme() {
      return new DalSetTableTupleScheme();
    }
  }

  private static class DalSetTableTupleScheme extends TupleScheme<DalSetTable> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DalSetTable struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.prefixName);
      BitSet optionals = new BitSet();
      if (struct.isSetSliceNum()) {
        optionals.set(0);
      }
      if (struct.isSetFillZero()) {
        optionals.set(1);
      }
      if (struct.isSetDesc()) {
        optionals.set(2);
      }
      if (struct.isSetCreateTimestamp()) {
        optionals.set(3);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetSliceNum()) {
        oprot.writeI32(struct.sliceNum);
      }
      if (struct.isSetFillZero()) {
        oprot.writeBool(struct.fillZero);
      }
      if (struct.isSetDesc()) {
        oprot.writeString(struct.desc);
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeI32(struct.createTimestamp);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        oprot.writeI32(struct.lastmodifyTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DalSetTable struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.prefixName = iprot.readString();
      struct.setPrefixNameIsSet(true);
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.sliceNum = iprot.readI32();
        struct.setSliceNumIsSet(true);
      }
      if (incoming.get(1)) {
        struct.fillZero = iprot.readBool();
        struct.setFillZeroIsSet(true);
      }
      if (incoming.get(2)) {
        struct.desc = iprot.readString();
        struct.setDescIsSet(true);
      }
      if (incoming.get(3)) {
        struct.createTimestamp = iprot.readI32();
        struct.setCreateTimestampIsSet(true);
      }
      if (incoming.get(4)) {
        struct.lastmodifyTimestamp = iprot.readI32();
        struct.setLastmodifyTimestampIsSet(true);
      }
    }
  }

}

