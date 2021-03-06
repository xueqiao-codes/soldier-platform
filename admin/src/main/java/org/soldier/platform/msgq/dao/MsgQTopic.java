/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.msgq.dao;

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

public class MsgQTopic implements org.apache.thrift.TBase<MsgQTopic, MsgQTopic._Fields>, java.io.Serializable, Cloneable, Comparable<MsgQTopic> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MsgQTopic");

  private static final org.apache.thrift.protocol.TField TOPIC_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("topicName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TOPIC_CLUSTER_FIELD_DESC = new org.apache.thrift.protocol.TField("topicCluster", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TOPIC_DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("topicDesc", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TOPIC_PROPERTIES_FIELD_DESC = new org.apache.thrift.protocol.TField("topicProperties", org.apache.thrift.protocol.TType.MAP, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestamp", org.apache.thrift.protocol.TType.I32, (short)10);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestamp", org.apache.thrift.protocol.TType.I32, (short)11);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MsgQTopicStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MsgQTopicTupleSchemeFactory());
  }

  public String topicName; // optional
  public String topicCluster; // optional
  public String topicDesc; // optional
  public Map<String,String> topicProperties; // optional
  public int createTimestamp; // optional
  public int lastmodifyTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOPIC_NAME((short)1, "topicName"),
    TOPIC_CLUSTER((short)2, "topicCluster"),
    TOPIC_DESC((short)3, "topicDesc"),
    TOPIC_PROPERTIES((short)4, "topicProperties"),
    CREATE_TIMESTAMP((short)10, "createTimestamp"),
    LASTMODIFY_TIMESTAMP((short)11, "lastmodifyTimestamp");

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
        case 1: // TOPIC_NAME
          return TOPIC_NAME;
        case 2: // TOPIC_CLUSTER
          return TOPIC_CLUSTER;
        case 3: // TOPIC_DESC
          return TOPIC_DESC;
        case 4: // TOPIC_PROPERTIES
          return TOPIC_PROPERTIES;
        case 10: // CREATE_TIMESTAMP
          return CREATE_TIMESTAMP;
        case 11: // LASTMODIFY_TIMESTAMP
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
  private _Fields optionals[] = {_Fields.TOPIC_NAME,_Fields.TOPIC_CLUSTER,_Fields.TOPIC_DESC,_Fields.TOPIC_PROPERTIES,_Fields.CREATE_TIMESTAMP,_Fields.LASTMODIFY_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOPIC_NAME, new org.apache.thrift.meta_data.FieldMetaData("topicName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TOPIC_CLUSTER, new org.apache.thrift.meta_data.FieldMetaData("topicCluster", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TOPIC_DESC, new org.apache.thrift.meta_data.FieldMetaData("topicDesc", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TOPIC_PROPERTIES, new org.apache.thrift.meta_data.FieldMetaData("topicProperties", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.CREATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("createTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MsgQTopic.class, metaDataMap);
  }

  public MsgQTopic() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MsgQTopic(MsgQTopic other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTopicName()) {
      this.topicName = other.topicName;
    }
    if (other.isSetTopicCluster()) {
      this.topicCluster = other.topicCluster;
    }
    if (other.isSetTopicDesc()) {
      this.topicDesc = other.topicDesc;
    }
    if (other.isSetTopicProperties()) {
      Map<String,String> __this__topicProperties = new HashMap<String,String>(other.topicProperties);
      this.topicProperties = __this__topicProperties;
    }
    this.createTimestamp = other.createTimestamp;
    this.lastmodifyTimestamp = other.lastmodifyTimestamp;
  }

  public MsgQTopic deepCopy() {
    return new MsgQTopic(this);
  }

  @Override
  public void clear() {
    this.topicName = null;
    this.topicCluster = null;
    this.topicDesc = null;
    this.topicProperties = null;
    setCreateTimestampIsSet(false);
    this.createTimestamp = 0;
    setLastmodifyTimestampIsSet(false);
    this.lastmodifyTimestamp = 0;
  }

  public String getTopicName() {
    return this.topicName;
  }

  public MsgQTopic setTopicName(String topicName) {
    this.topicName = topicName;
    return this;
  }

  public void unsetTopicName() {
    this.topicName = null;
  }

  /** Returns true if field topicName is set (has been assigned a value) and false otherwise */
  public boolean isSetTopicName() {
    return this.topicName != null;
  }

  public void setTopicNameIsSet(boolean value) {
    if (!value) {
      this.topicName = null;
    }
  }

  public String getTopicCluster() {
    return this.topicCluster;
  }

  public MsgQTopic setTopicCluster(String topicCluster) {
    this.topicCluster = topicCluster;
    return this;
  }

  public void unsetTopicCluster() {
    this.topicCluster = null;
  }

  /** Returns true if field topicCluster is set (has been assigned a value) and false otherwise */
  public boolean isSetTopicCluster() {
    return this.topicCluster != null;
  }

  public void setTopicClusterIsSet(boolean value) {
    if (!value) {
      this.topicCluster = null;
    }
  }

  public String getTopicDesc() {
    return this.topicDesc;
  }

  public MsgQTopic setTopicDesc(String topicDesc) {
    this.topicDesc = topicDesc;
    return this;
  }

  public void unsetTopicDesc() {
    this.topicDesc = null;
  }

  /** Returns true if field topicDesc is set (has been assigned a value) and false otherwise */
  public boolean isSetTopicDesc() {
    return this.topicDesc != null;
  }

  public void setTopicDescIsSet(boolean value) {
    if (!value) {
      this.topicDesc = null;
    }
  }

  public int getTopicPropertiesSize() {
    return (this.topicProperties == null) ? 0 : this.topicProperties.size();
  }

  public void putToTopicProperties(String key, String val) {
    if (this.topicProperties == null) {
      this.topicProperties = new HashMap<String,String>();
    }
    this.topicProperties.put(key, val);
  }

  public Map<String,String> getTopicProperties() {
    return this.topicProperties;
  }

  public MsgQTopic setTopicProperties(Map<String,String> topicProperties) {
    this.topicProperties = topicProperties;
    return this;
  }

  public void unsetTopicProperties() {
    this.topicProperties = null;
  }

  /** Returns true if field topicProperties is set (has been assigned a value) and false otherwise */
  public boolean isSetTopicProperties() {
    return this.topicProperties != null;
  }

  public void setTopicPropertiesIsSet(boolean value) {
    if (!value) {
      this.topicProperties = null;
    }
  }

  public int getCreateTimestamp() {
    return this.createTimestamp;
  }

  public MsgQTopic setCreateTimestamp(int createTimestamp) {
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

  public MsgQTopic setLastmodifyTimestamp(int lastmodifyTimestamp) {
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
    case TOPIC_NAME:
      if (value == null) {
        unsetTopicName();
      } else {
        setTopicName((String)value);
      }
      break;

    case TOPIC_CLUSTER:
      if (value == null) {
        unsetTopicCluster();
      } else {
        setTopicCluster((String)value);
      }
      break;

    case TOPIC_DESC:
      if (value == null) {
        unsetTopicDesc();
      } else {
        setTopicDesc((String)value);
      }
      break;

    case TOPIC_PROPERTIES:
      if (value == null) {
        unsetTopicProperties();
      } else {
        setTopicProperties((Map<String,String>)value);
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
    case TOPIC_NAME:
      return getTopicName();

    case TOPIC_CLUSTER:
      return getTopicCluster();

    case TOPIC_DESC:
      return getTopicDesc();

    case TOPIC_PROPERTIES:
      return getTopicProperties();

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
    case TOPIC_NAME:
      return isSetTopicName();
    case TOPIC_CLUSTER:
      return isSetTopicCluster();
    case TOPIC_DESC:
      return isSetTopicDesc();
    case TOPIC_PROPERTIES:
      return isSetTopicProperties();
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
    if (that instanceof MsgQTopic)
      return this.equals((MsgQTopic)that);
    return false;
  }

  public boolean equals(MsgQTopic that) {
    if (that == null)
      return false;

    boolean this_present_topicName = true && this.isSetTopicName();
    boolean that_present_topicName = true && that.isSetTopicName();
    if (this_present_topicName || that_present_topicName) {
      if (!(this_present_topicName && that_present_topicName))
        return false;
      if (!this.topicName.equals(that.topicName))
        return false;
    }

    boolean this_present_topicCluster = true && this.isSetTopicCluster();
    boolean that_present_topicCluster = true && that.isSetTopicCluster();
    if (this_present_topicCluster || that_present_topicCluster) {
      if (!(this_present_topicCluster && that_present_topicCluster))
        return false;
      if (!this.topicCluster.equals(that.topicCluster))
        return false;
    }

    boolean this_present_topicDesc = true && this.isSetTopicDesc();
    boolean that_present_topicDesc = true && that.isSetTopicDesc();
    if (this_present_topicDesc || that_present_topicDesc) {
      if (!(this_present_topicDesc && that_present_topicDesc))
        return false;
      if (!this.topicDesc.equals(that.topicDesc))
        return false;
    }

    boolean this_present_topicProperties = true && this.isSetTopicProperties();
    boolean that_present_topicProperties = true && that.isSetTopicProperties();
    if (this_present_topicProperties || that_present_topicProperties) {
      if (!(this_present_topicProperties && that_present_topicProperties))
        return false;
      if (!this.topicProperties.equals(that.topicProperties))
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
  public int compareTo(MsgQTopic other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTopicName()).compareTo(other.isSetTopicName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopicName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topicName, other.topicName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTopicCluster()).compareTo(other.isSetTopicCluster());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopicCluster()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topicCluster, other.topicCluster);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTopicDesc()).compareTo(other.isSetTopicDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopicDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topicDesc, other.topicDesc);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTopicProperties()).compareTo(other.isSetTopicProperties());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopicProperties()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topicProperties, other.topicProperties);
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
    StringBuilder sb = new StringBuilder("MsgQTopic(");
    boolean first = true;

    if (isSetTopicName()) {
      sb.append("topicName:");
      if (this.topicName == null) {
        sb.append("null");
      } else {
        sb.append(this.topicName);
      }
      first = false;
    }
    if (isSetTopicCluster()) {
      if (!first) sb.append(", ");
      sb.append("topicCluster:");
      if (this.topicCluster == null) {
        sb.append("null");
      } else {
        sb.append(this.topicCluster);
      }
      first = false;
    }
    if (isSetTopicDesc()) {
      if (!first) sb.append(", ");
      sb.append("topicDesc:");
      if (this.topicDesc == null) {
        sb.append("null");
      } else {
        sb.append(this.topicDesc);
      }
      first = false;
    }
    if (isSetTopicProperties()) {
      if (!first) sb.append(", ");
      sb.append("topicProperties:");
      if (this.topicProperties == null) {
        sb.append("null");
      } else {
        sb.append(this.topicProperties);
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

  private static class MsgQTopicStandardSchemeFactory implements SchemeFactory {
    public MsgQTopicStandardScheme getScheme() {
      return new MsgQTopicStandardScheme();
    }
  }

  private static class MsgQTopicStandardScheme extends StandardScheme<MsgQTopic> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MsgQTopic struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOPIC_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.topicName = iprot.readString();
              struct.setTopicNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOPIC_CLUSTER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.topicCluster = iprot.readString();
              struct.setTopicClusterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOPIC_DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.topicDesc = iprot.readString();
              struct.setTopicDescIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TOPIC_PROPERTIES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map30 = iprot.readMapBegin();
                struct.topicProperties = new HashMap<String,String>(2*_map30.size);
                for (int _i31 = 0; _i31 < _map30.size; ++_i31)
                {
                  String _key32;
                  String _val33;
                  _key32 = iprot.readString();
                  _val33 = iprot.readString();
                  struct.topicProperties.put(_key32, _val33);
                }
                iprot.readMapEnd();
              }
              struct.setTopicPropertiesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // CREATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTimestamp = iprot.readI32();
              struct.setCreateTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 11: // LASTMODIFY_TIMESTAMP
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MsgQTopic struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.topicName != null) {
        if (struct.isSetTopicName()) {
          oprot.writeFieldBegin(TOPIC_NAME_FIELD_DESC);
          oprot.writeString(struct.topicName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.topicCluster != null) {
        if (struct.isSetTopicCluster()) {
          oprot.writeFieldBegin(TOPIC_CLUSTER_FIELD_DESC);
          oprot.writeString(struct.topicCluster);
          oprot.writeFieldEnd();
        }
      }
      if (struct.topicDesc != null) {
        if (struct.isSetTopicDesc()) {
          oprot.writeFieldBegin(TOPIC_DESC_FIELD_DESC);
          oprot.writeString(struct.topicDesc);
          oprot.writeFieldEnd();
        }
      }
      if (struct.topicProperties != null) {
        if (struct.isSetTopicProperties()) {
          oprot.writeFieldBegin(TOPIC_PROPERTIES_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.topicProperties.size()));
            for (Map.Entry<String, String> _iter34 : struct.topicProperties.entrySet())
            {
              oprot.writeString(_iter34.getKey());
              oprot.writeString(_iter34.getValue());
            }
            oprot.writeMapEnd();
          }
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

  private static class MsgQTopicTupleSchemeFactory implements SchemeFactory {
    public MsgQTopicTupleScheme getScheme() {
      return new MsgQTopicTupleScheme();
    }
  }

  private static class MsgQTopicTupleScheme extends TupleScheme<MsgQTopic> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MsgQTopic struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTopicName()) {
        optionals.set(0);
      }
      if (struct.isSetTopicCluster()) {
        optionals.set(1);
      }
      if (struct.isSetTopicDesc()) {
        optionals.set(2);
      }
      if (struct.isSetTopicProperties()) {
        optionals.set(3);
      }
      if (struct.isSetCreateTimestamp()) {
        optionals.set(4);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetTopicName()) {
        oprot.writeString(struct.topicName);
      }
      if (struct.isSetTopicCluster()) {
        oprot.writeString(struct.topicCluster);
      }
      if (struct.isSetTopicDesc()) {
        oprot.writeString(struct.topicDesc);
      }
      if (struct.isSetTopicProperties()) {
        {
          oprot.writeI32(struct.topicProperties.size());
          for (Map.Entry<String, String> _iter35 : struct.topicProperties.entrySet())
          {
            oprot.writeString(_iter35.getKey());
            oprot.writeString(_iter35.getValue());
          }
        }
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeI32(struct.createTimestamp);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        oprot.writeI32(struct.lastmodifyTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MsgQTopic struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.topicName = iprot.readString();
        struct.setTopicNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.topicCluster = iprot.readString();
        struct.setTopicClusterIsSet(true);
      }
      if (incoming.get(2)) {
        struct.topicDesc = iprot.readString();
        struct.setTopicDescIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TMap _map36 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.topicProperties = new HashMap<String,String>(2*_map36.size);
          for (int _i37 = 0; _i37 < _map36.size; ++_i37)
          {
            String _key38;
            String _val39;
            _key38 = iprot.readString();
            _val39 = iprot.readString();
            struct.topicProperties.put(_key38, _val39);
          }
        }
        struct.setTopicPropertiesIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createTimestamp = iprot.readI32();
        struct.setCreateTimestampIsSet(true);
      }
      if (incoming.get(5)) {
        struct.lastmodifyTimestamp = iprot.readI32();
        struct.setLastmodifyTimestampIsSet(true);
      }
    }
  }

}

