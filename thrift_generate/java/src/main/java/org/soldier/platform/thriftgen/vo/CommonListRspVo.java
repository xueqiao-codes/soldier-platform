package org.soldier.platform.thriftgen.vo;

import java.util.List;

public class CommonListRspVo extends BaseRspVo {

    public CommonListRspVo() {
    }

    private int total;
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
