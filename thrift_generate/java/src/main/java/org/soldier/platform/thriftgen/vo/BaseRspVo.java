package org.soldier.platform.thriftgen.vo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 通用响应VO
 */
public class BaseRspVo {

    protected int retCode;
    protected Object data;

    public BaseRspVo() {
        this.retCode = ResultCode.C_SUCCESS.getCode();
    }

    public BaseRspVo(ResultCode resultCode) {
        this.retCode = resultCode.getCode();
        this.data = resultCode.getResult();
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(ResultCode resultCode) {
        this.retCode = resultCode.getCode();
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.toString();
    }
}
