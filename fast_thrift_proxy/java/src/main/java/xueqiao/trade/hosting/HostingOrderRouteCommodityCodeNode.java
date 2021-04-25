/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting;

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

public class HostingOrderRouteCommodityCodeNode implements org.apache.thrift.TBase<HostingOrderRouteCommodityCodeNode, HostingOrderRouteCommodityCodeNode._Fields>, java.io.Serializable, Cloneable, Comparable<HostingOrderRouteCommodityCodeNode> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingOrderRouteCommodityCodeNode");

  private static final org.apache.thrift.protocol.TField SLED_COMMODITY_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("sledCommodityCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RELATED_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("relatedInfo", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingOrderRouteCommodityCodeNodeStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingOrderRouteCommodityCodeNodeTupleSchemeFactory());
  }

  public String sledCommodityCode; // optional
  public HostingOrderRouteRelatedInfo relatedInfo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SLED_COMMODITY_CODE((short)1, "sledCommodityCode"),
    RELATED_INFO((short)2, "relatedInfo");

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
        case 1: // SLED_COMMODITY_CODE
          return SLED_COMMODITY_CODE;
        case 2: // RELATED_INFO
          return RELATED_INFO;
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
  private _Fields optionals[] = {_Fields.SLED_COMMODITY_CODE,_Fields.RELATED_INFO};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SLED_COMMODITY_CODE, new org.apache.thrift.meta_data.FieldMetaData("sledCommodityCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RELATED_INFO, new org.apache.thrift.meta_data.FieldMetaData("relatedInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingOrderRouteRelatedInfo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingOrderRouteCommodityCodeNode.class, metaDataMap);
  }

  public HostingOrderRouteCommodityCodeNode() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingOrderRouteCommodityCodeNode(HostingOrderRouteCommodityCodeNode other) {
    if (other.isSetSledCommodityCode()) {
      this.sledCommodityCode = other.sledCommodityCode;
    }
    if (other.isSetRelatedInfo()) {
      this.relatedInfo = new HostingOrderRouteRelatedInfo(other.relatedInfo);
    }
  }

  public HostingOrderRouteCommodityCodeNode deepCopy() {
    return new HostingOrderRouteCommodityCodeNode(this);
  }

  @Override
  public void clear() {
    this.sledCommodityCode = null;
    this.relatedInfo = null;
  }

  public String getSledCommodityCode() {
    return this.sledCommodityCode;
  }

  public HostingOrderRouteCommodityCodeNode setSledCommodityCode(String sledCommodityCode) {
    this.sledCommodityCode = sledCommodityCode;
    return this;
  }

  public void unsetSledCommodityCode() {
    this.sledCommodityCode = null;
  }

  /** Returns true if field sledCommodityCode is set (has been assigned a value) and false otherwise */
  public boolean isSetSledCommodityCode() {
    return this.sledCommodityCode != null;
  }

  public void setSledCommodityCodeIsSet(boolean value) {
    if (!value) {
      this.sledCommodityCode = null;
    }
  }

  public HostingOrderRouteRelatedInfo getRelatedInfo() {
    return this.relatedInfo;
  }

  public HostingOrderRouteCommodityCodeNode setRelatedInfo(HostingOrderRouteRelatedInfo relatedInfo) {
    this.relatedInfo = relatedInfo;
    return this;
  }

  public void unsetRelatedInfo() {
    this.relatedInfo = null;
  }

  /** Returns true if field relatedInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetRelatedInfo() {
    return this.relatedInfo != null;
  }

  public void setRelatedInfoIsSet(boolean value) {
    if (!value) {
      this.relatedInfo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SLED_COMMODITY_CODE:
      if (value == null) {
        unsetSledCommodityCode();
      } else {
        setSledCommodityCode((String)value);
      }
      break;

    case RELATED_INFO:
      if (value == null) {
        unsetRelatedInfo();
      } else {
        setRelatedInfo((HostingOrderRouteRelatedInfo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SLED_COMMODITY_CODE:
      return getSledCommodityCode();

    case RELATED_INFO:
      return getRelatedInfo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SLED_COMMODITY_CODE:
      return isSetSledCommodityCode();
    case RELATED_INFO:
      return isSetRelatedInfo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingOrderRouteCommodityCodeNode)
      return this.equals((HostingOrderRouteCommodityCodeNode)that);
    return false;
  }

  public boolean equals(HostingOrderRouteCommodityCodeNode that) {
    if (that == null)
      return false;

    boolean this_present_sledCommodityCode = true && this.isSetSledCommodityCode();
    boolean that_present_sledCommodityCode = true && that.isSetSledCommodityCode();
    if (this_present_sledCommodityCode || that_present_sledCommodityCode) {
      if (!(this_present_sledCommodityCode && that_present_sledCommodityCode))
        return false;
      if (!this.sledCommodityCode.equals(that.sledCommodityCode))
        return false;
    }

    boolean this_present_relatedInfo = true && this.isSetRelatedInfo();
    boolean that_present_relatedInfo = true && that.isSetRelatedInfo();
    if (this_present_relatedInfo || that_present_relatedInfo) {
      if (!(this_present_relatedInfo && that_present_relatedInfo))
        return false;
      if (!this.relatedInfo.equals(that.relatedInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingOrderRouteCommodityCodeNode other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSledCommodityCode()).compareTo(other.isSetSledCommodityCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledCommodityCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledCommodityCode, other.sledCommodityCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRelatedInfo()).compareTo(other.isSetRelatedInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRelatedInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.relatedInfo, other.relatedInfo);
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
    StringBuilder sb = new StringBuilder("HostingOrderRouteCommodityCodeNode(");
    boolean first = true;

    if (isSetSledCommodityCode()) {
      sb.append("sledCommodityCode:");
      if (this.sledCommodityCode == null) {
        sb.append("null");
      } else {
        sb.append(this.sledCommodityCode);
      }
      first = false;
    }
    if (isSetRelatedInfo()) {
      if (!first) sb.append(", ");
      sb.append("relatedInfo:");
      if (this.relatedInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.relatedInfo);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (relatedInfo != null) {
      relatedInfo.validate();
    }
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

  private static class HostingOrderRouteCommodityCodeNodeStandardSchemeFactory implements SchemeFactory {
    public HostingOrderRouteCommodityCodeNodeStandardScheme getScheme() {
      return new HostingOrderRouteCommodityCodeNodeStandardScheme();
    }
  }

  private static class HostingOrderRouteCommodityCodeNodeStandardScheme extends StandardScheme<HostingOrderRouteCommodityCodeNode> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingOrderRouteCommodityCodeNode struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SLED_COMMODITY_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sledCommodityCode = iprot.readString();
              struct.setSledCommodityCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RELATED_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.relatedInfo = new HostingOrderRouteRelatedInfo();
              struct.relatedInfo.read(iprot);
              struct.setRelatedInfoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingOrderRouteCommodityCodeNode struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.sledCommodityCode != null) {
        if (struct.isSetSledCommodityCode()) {
          oprot.writeFieldBegin(SLED_COMMODITY_CODE_FIELD_DESC);
          oprot.writeString(struct.sledCommodityCode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.relatedInfo != null) {
        if (struct.isSetRelatedInfo()) {
          oprot.writeFieldBegin(RELATED_INFO_FIELD_DESC);
          struct.relatedInfo.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingOrderRouteCommodityCodeNodeTupleSchemeFactory implements SchemeFactory {
    public HostingOrderRouteCommodityCodeNodeTupleScheme getScheme() {
      return new HostingOrderRouteCommodityCodeNodeTupleScheme();
    }
  }

  private static class HostingOrderRouteCommodityCodeNodeTupleScheme extends TupleScheme<HostingOrderRouteCommodityCodeNode> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingOrderRouteCommodityCodeNode struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSledCommodityCode()) {
        optionals.set(0);
      }
      if (struct.isSetRelatedInfo()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSledCommodityCode()) {
        oprot.writeString(struct.sledCommodityCode);
      }
      if (struct.isSetRelatedInfo()) {
        struct.relatedInfo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingOrderRouteCommodityCodeNode struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.sledCommodityCode = iprot.readString();
        struct.setSledCommodityCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.relatedInfo = new HostingOrderRouteRelatedInfo();
        struct.relatedInfo.read(iprot);
        struct.setRelatedInfoIsSet(true);
      }
    }
  }

}

