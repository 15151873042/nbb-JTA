package com.nbb.jta.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author 胡鹏
 */
public interface DictTypeService {

    @Transactional
    int insert();
}
