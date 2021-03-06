/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.soldier.platform.attr;

public class AttrReporter {
  public static int requireKey(ReporterType type, String metric, StringMap tags) {
    return AttrReporterJNI.requireKey(type.swigValue(), metric, StringMap.getCPtr(tags), tags);
  }

  public static void inc(ReporterType type, int key, long value) {
    AttrReporterJNI.inc(type.swigValue(), key, value);
  }

  public static void set(ReporterType type, int key, long value) {
    AttrReporterJNI.set(type.swigValue(), key, value);
  }

  public static void average(ReporterType type, int key, long value) {
    AttrReporterJNI.average(type.swigValue(), key, value);
  }

  public static void keep(ReporterType type, int key, long value) {
    AttrReporterJNI.keep(type.swigValue(), key, value);
  }

}
