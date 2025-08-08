package com.nbb.jta.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 胡鹏
 */
@TableName("dict_type")
@Data
public class DictTypeDO {

    private Long id;

    private String dictType;

    private String dictName;
}
