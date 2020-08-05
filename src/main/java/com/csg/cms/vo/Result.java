package com.csg.cms.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    protected int code;
    protected String msg;
    protected T data;
    protected Map<String, Object> page;

    private Result() {
        this(ResultStatus.SUCCESS);
    }

    public Result(T data, ResultStatus resultStatus) {
        this(resultStatus);
        this.data = data;
    }

    /**
     * 处理分页数据封装
     * @param pageData
     * @param pageable
     * @param resultStatus
     */
    public Result(Page<T> pageData, Pageable pageable, ResultStatus resultStatus) {
        this(resultStatus);

        Map<String, Object> pageMap = new LinkedHashMap<String, Object>();
        pageMap.put("currentPageNum", pageable.getPageNumber() + 1);   //当前页（前端分页从1开始，后台jpa分页从0开始）
        pageMap.put("pageSize", pageable.getPageSize());    //每页显示记录数
        pageMap.put("totalNum", pageData.getTotalElements());   //总记录数
        pageMap.put("totalPages", pageData.getTotalPages());   //总页数

        this.page = pageMap;

        this.data = (T) pageData.getContent();
    }

    public Result(ResultStatus resultStatus) {
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMessage();
    }

    public Result(T data) {
        this();
        this.data = data;
    }


    public static Result success() {
        return new Result();
    }

    public static Result fail(ResultStatus resultStatus){
        return new Result(resultStatus);
    }

    public static Result fallBack() {
        return new Result(ResultStatus.FALLBACK);
    }
}