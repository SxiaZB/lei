package com.lei.smart.mapper;

import com.lei.smart.pojo.WebchatSchool;
import com.lei.smart.pojo.WebchatSchoolKey;

public interface WebchatSchoolMapper {
    int deleteByPrimaryKey(WebchatSchoolKey key);

    int insert(WebchatSchool record);

    int insertSelective(WebchatSchool record);

    WebchatSchool selectByPrimaryKey(WebchatSchoolKey key);

    int updateByPrimaryKeySelective(WebchatSchool record);

    int updateByPrimaryKey(WebchatSchool record);
}