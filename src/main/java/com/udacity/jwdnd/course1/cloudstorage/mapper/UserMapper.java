package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends Rule {

    String getUserByUsernameSql = "SELECT * FROM USERS WHERE  username= #{username}";
    String insertByUserObjSql = "INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})";
    String deleteByIdSql = "DELETE FROM USERS WHERE userid = #{userId}";
    String deleteAllSql = "DELETE FROM USERS";

    @Select(getUserByUsernameSql)
    User getUser(String username);

    @Override
    @Delete(deleteByIdSql)
    int delete(Integer userId);

    @Override
    @Delete((deleteAllSql))
    int deleteAll();

}
