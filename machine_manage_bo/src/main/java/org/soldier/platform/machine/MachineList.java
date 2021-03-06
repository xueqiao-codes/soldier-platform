/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.machine;

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

public class MachineList implements org.apache.thrift.TBase<MachineList, MachineList._Fields>, java.io.Serializable, Cloneable, Comparable<MachineList> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MachineList");

  private static final org.apache.thrift.protocol.TField TOTAL_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("totalNum", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MACHINES_MAP_FIELD_DESC = new org.apache.thrift.protocol.TField("machinesMap", org.apache.thrift.protocol.TType.MAP, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MachineListStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MachineListTupleSchemeFactory());
  }

  public int totalNum; // required
  public Map<String,Machine> machinesMap; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_NUM((short)1, "totalNum"),
    MACHINES_MAP((short)2, "machinesMap");

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
        case 1: // TOTAL_NUM
          return TOTAL_NUM;
        case 2: // MACHINES_MAP
          return MACHINES_MAP;
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
  private static final int __TOTALNUM_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_NUM, new org.apache.thrift.meta_data.FieldMetaData("totalNum", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MACHINES_MAP, new org.apache.thrift.meta_data.FieldMetaData("machinesMap", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Machine.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MachineList.class, metaDataMap);
  }

  public MachineList() {
  }

  public MachineList(
    int totalNum,
    Map<String,Machine> machinesMap)
  {
    this();
    this.totalNum = totalNum;
    setTotalNumIsSet(true);
    this.machinesMap = machinesMap;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MachineList(MachineList other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalNum = other.totalNum;
    if (other.isSetMachinesMap()) {
      Map<String,Machine> __this__machinesMap = new HashMap<String,Machine>(other.machinesMap.size());
      for (Map.Entry<String, Machine> other_element : other.machinesMap.entrySet()) {

        String other_element_key = other_element.getKey();
        Machine other_element_value = other_element.getValue();

        String __this__machinesMap_copy_key = other_element_key;

        Machine __this__machinesMap_copy_value = new Machine(other_element_value);

        __this__machinesMap.put(__this__machinesMap_copy_key, __this__machinesMap_copy_value);
      }
      this.machinesMap = __this__machinesMap;
    }
  }

  public MachineList deepCopy() {
    return new MachineList(this);
  }

  @Override
  public void clear() {
    setTotalNumIsSet(false);
    this.totalNum = 0;
    this.machinesMap = null;
  }

  public int getTotalNum() {
    return this.totalNum;
  }

  public MachineList setTotalNum(int totalNum) {
    this.totalNum = totalNum;
    setTotalNumIsSet(true);
    return this;
  }

  public void unsetTotalNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALNUM_ISSET_ID);
  }

  /** Returns true if field totalNum is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalNum() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALNUM_ISSET_ID);
  }

  public void setTotalNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALNUM_ISSET_ID, value);
  }

  public int getMachinesMapSize() {
    return (this.machinesMap == null) ? 0 : this.machinesMap.size();
  }

  public void putToMachinesMap(String key, Machine val) {
    if (this.machinesMap == null) {
      this.machinesMap = new HashMap<String,Machine>();
    }
    this.machinesMap.put(key, val);
  }

  public Map<String,Machine> getMachinesMap() {
    return this.machinesMap;
  }

  public MachineList setMachinesMap(Map<String,Machine> machinesMap) {
    this.machinesMap = machinesMap;
    return this;
  }

  public void unsetMachinesMap() {
    this.machinesMap = null;
  }

  /** Returns true if field machinesMap is set (has been assigned a value) and false otherwise */
  public boolean isSetMachinesMap() {
    return this.machinesMap != null;
  }

  public void setMachinesMapIsSet(boolean value) {
    if (!value) {
      this.machinesMap = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOTAL_NUM:
      if (value == null) {
        unsetTotalNum();
      } else {
        setTotalNum((Integer)value);
      }
      break;

    case MACHINES_MAP:
      if (value == null) {
        unsetMachinesMap();
      } else {
        setMachinesMap((Map<String,Machine>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_NUM:
      return Integer.valueOf(getTotalNum());

    case MACHINES_MAP:
      return getMachinesMap();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_NUM:
      return isSetTotalNum();
    case MACHINES_MAP:
      return isSetMachinesMap();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MachineList)
      return this.equals((MachineList)that);
    return false;
  }

  public boolean equals(MachineList that) {
    if (that == null)
      return false;

    boolean this_present_totalNum = true;
    boolean that_present_totalNum = true;
    if (this_present_totalNum || that_present_totalNum) {
      if (!(this_present_totalNum && that_present_totalNum))
        return false;
      if (this.totalNum != that.totalNum)
        return false;
    }

    boolean this_present_machinesMap = true && this.isSetMachinesMap();
    boolean that_present_machinesMap = true && that.isSetMachinesMap();
    if (this_present_machinesMap || that_present_machinesMap) {
      if (!(this_present_machinesMap && that_present_machinesMap))
        return false;
      if (!this.machinesMap.equals(that.machinesMap))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(MachineList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTotalNum()).compareTo(other.isSetTotalNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalNum, other.totalNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMachinesMap()).compareTo(other.isSetMachinesMap());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMachinesMap()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.machinesMap, other.machinesMap);
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
    StringBuilder sb = new StringBuilder("MachineList(");
    boolean first = true;

    sb.append("totalNum:");
    sb.append(this.totalNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("machinesMap:");
    if (this.machinesMap == null) {
      sb.append("null");
    } else {
      sb.append(this.machinesMap);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'totalNum' because it's a primitive and you chose the non-beans generator.
    if (machinesMap == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'machinesMap' was not present! Struct: " + toString());
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

  private static class MachineListStandardSchemeFactory implements SchemeFactory {
    public MachineListStandardScheme getScheme() {
      return new MachineListStandardScheme();
    }
  }

  private static class MachineListStandardScheme extends StandardScheme<MachineList> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MachineList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalNum = iprot.readI32();
              struct.setTotalNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MACHINES_MAP
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map20 = iprot.readMapBegin();
                struct.machinesMap = new HashMap<String,Machine>(2*_map20.size);
                for (int _i21 = 0; _i21 < _map20.size; ++_i21)
                {
                  String _key22;
                  Machine _val23;
                  _key22 = iprot.readString();
                  _val23 = new Machine();
                  _val23.read(iprot);
                  struct.machinesMap.put(_key22, _val23);
                }
                iprot.readMapEnd();
              }
              struct.setMachinesMapIsSet(true);
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
      if (!struct.isSetTotalNum()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalNum' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, MachineList struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TOTAL_NUM_FIELD_DESC);
      oprot.writeI32(struct.totalNum);
      oprot.writeFieldEnd();
      if (struct.machinesMap != null) {
        oprot.writeFieldBegin(MACHINES_MAP_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.machinesMap.size()));
          for (Map.Entry<String, Machine> _iter24 : struct.machinesMap.entrySet())
          {
            oprot.writeString(_iter24.getKey());
            _iter24.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MachineListTupleSchemeFactory implements SchemeFactory {
    public MachineListTupleScheme getScheme() {
      return new MachineListTupleScheme();
    }
  }

  private static class MachineListTupleScheme extends TupleScheme<MachineList> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MachineList struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.totalNum);
      {
        oprot.writeI32(struct.machinesMap.size());
        for (Map.Entry<String, Machine> _iter25 : struct.machinesMap.entrySet())
        {
          oprot.writeString(_iter25.getKey());
          _iter25.getValue().write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MachineList struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.totalNum = iprot.readI32();
      struct.setTotalNumIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map26 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.machinesMap = new HashMap<String,Machine>(2*_map26.size);
        for (int _i27 = 0; _i27 < _map26.size; ++_i27)
        {
          String _key28;
          Machine _val29;
          _key28 = iprot.readString();
          _val29 = new Machine();
          _val29.read(iprot);
          struct.machinesMap.put(_key28, _val29);
        }
      }
      struct.setMachinesMapIsSet(true);
    }
  }

}

