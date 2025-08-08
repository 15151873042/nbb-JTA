package com.nbb.jta.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.nbb.jta.domain.DictTypeDO;
import com.nbb.jta.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 胡鹏
 */

@RestController
public class TestController {

    @Autowired
    DictTypeService dictTypeService;

    @RequestMapping("/list")
    public PageDTO<DictTypeDO> list() {
        PageDTO<DictTypeDO> page = dictTypeService.page();
        return page;
    }

    @RequestMapping("/insert")
    public String insert() {
        int count = dictTypeService.insert();
        return "success";
    }

}
