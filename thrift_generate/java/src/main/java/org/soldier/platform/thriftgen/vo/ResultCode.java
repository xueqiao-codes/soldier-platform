package org.soldier.platform.thriftgen.vo;

public enum ResultCode {

    // 通用结果码
    C_SUCCESS("请求成功", 1),
    C_FAIL("请求失败", -1001),
    C_RESOURCE_NONEXIST("资源不存在",-1002),
    C_BAD_PARAM("参数错误",-1003),
    ;

    private String result;
    private int code;

    ResultCode(String result, int code) {
        this.result = result;
        this.code = code;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

}
