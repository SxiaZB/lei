package com.lei.smart.slavemapper;

import com.lei.smart.pojo.User;

public interface UserSlaveMapper {
    int deleteByPrimaryKey(Long id);
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}