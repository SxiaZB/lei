package com.lei.smart.mapper;

import com.lei.smart.TableSplit;
import com.lei.smart.pojo.User;

@TableSplit(value="lei_user",strategy="user")
public interface UserMapper {
    int deleteByPrimaryKey(Long id);
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}