package com.nbb.jta.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.nbb.jta.domain.DictTypeDO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 胡鹏
 */
public interface DictTypeService {

    PageDTO<DictTypeDO> page();

    @Transactional
    int insert();
}
