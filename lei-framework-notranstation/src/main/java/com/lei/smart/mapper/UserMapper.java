package com.lei.smart.mapper;

import com.lei.smart.DataSource;
import com.lei.smart.DataSources;
import com.lei.smart.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);
    @DataSource(value = DataSources.MASTER)
    int insert(User record);
    int insertSelective(User record);
    @DataSource(value = DataSources.SLAVE)
    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}