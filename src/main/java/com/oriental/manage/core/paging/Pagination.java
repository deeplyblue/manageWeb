package com.oriental.manage.core.paging;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by lupf on 2016/4/21.
 */
@Data
@EqualsAndHashCode
public class Pagination<T> {

    // 传参或指定
    private int pageNum; // 当前页号, 采用自然数计数 1,2,3,...
    private int pageSize; // 页面大小:一个页面显示多少个数据

    // 需要从数据库中查找出
    private long rowCount;// 数据总数：一共有多少个数据
    private List<T> list;

    // 可以根据上面属性：pageNum,pageSize,rowCount计算出
    private int pageCount; // 页面总数
    private int startRow;// 当前页面开始行, 第一行是0行
    private int first = 1;// 第一页 页号
    private int last;// 最后页 页号
    private int next;// 下一页 页号
    private int prev;// 前页 页号
    private int start;// 页号式导航, 起始页号
    private int end;// 页号式导航, 结束页号
    private int numCount = 10;// 页号式导航, 最多显示页号数量为numCount+1;这里显示11页。
    private T queryBean;//保存查询条件

    public void setPageNum(int pageNum) {
        if (pageNum <= 0) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

    public Pagination(T bean) {

    }


    public Pagination(int pageSize, int pageNum, long rowCount) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.rowCount = rowCount;

        this.pageCount = (int) Math.ceil((double) rowCount / pageSize);
        this.pageNum = Math.min(this.pageNum, pageCount);
        this.pageNum = Math.max(1, this.pageNum);

        this.startRow = (this.pageNum - 1) * pageSize;
        this.last = this.pageCount;
        this.next = Math.min(this.pageCount, this.pageNum + 1);
        this.prev = Math.max(1, this.pageNum - 1);

        // 计算page 控制
        start = Math.max(this.pageNum - numCount / 2, first);
        end = Math.min(start + numCount, last);
        if (end - start < numCount) {
            start = Math.max(end - numCount, 1);
        }

    }

    public Pagination() {
    }

    /**
     * 通过每页条数,页码计算起始行号
     *
     * @param size 每页条数
     * @param num  页码
     * @return 起始行号
     */
    public static int calculateStartRow(int size, int num) {
        return (num - 1) * size;
    }

    public int getStartRow() {
        return this.pageSize * (this.pageNum - 1);
    }
}
