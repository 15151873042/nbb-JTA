package com.nbb.jta.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.nbb.jta.domain.DictTypeDO;
import com.nbb.jta.mapper.business1.DictType1Mapper;
import com.nbb.jta.mapper.business2.DictType2Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 胡鹏
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    private DictType1Mapper dictType1Mapper;
    @Resource
    private DictType2Mapper dictType2Mapper;


    @Override
    public PageDTO<DictTypeDO> page() {
//        return dictTypeMapper.selectPage(new PageDTO<>(), new LambdaQueryWrapper<>());
        return null;
    }

    @Override
    public int insert() {
        DictTypeDO dictTypeDO = new DictTypeDO();
        dictTypeDO.setId(IdUtil.getSnowflakeNextId());
        dictTypeDO.setDictName("用户类型");
        dictTypeDO.setDictType("userType");
        int result1 = dictType1Mapper.insert(dictTypeDO);
        DictTypeDO dictTypeDO2 = new DictTypeDO();
        dictTypeDO2.setId(IdUtil.getSnowflakeNextId());
        dictTypeDO2.setDictName("用户类型");
        dictTypeDO2.setDictType("userType");
        int result2 = dictType2Mapper.insert(dictTypeDO2);
        return result1;
    }
}
