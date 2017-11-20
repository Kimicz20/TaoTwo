package com.taotao.pojo;

import java.util.List;

/**
 * Created by geek on 2017/6/12.
 */

/**
 *  分页查询json数据对应pojo类
 */
public class EUIDataGridResult {

    private long total;
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
