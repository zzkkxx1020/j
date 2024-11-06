package org.example.jworker.common.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询入参基类
 */
@Data
@ApiModel(value = "公共查询参数")
public class BasicQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页号
     */
    @ApiModelProperty(value = "页号")
    private int pageNumber = 1;
    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    private int pageSize = 10;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String column;
    /**
     * 排序方式 asc/desc
     */
    @ApiModelProperty(value = "排序方式 asc/desc")
    private String order;

    private String token;
}
