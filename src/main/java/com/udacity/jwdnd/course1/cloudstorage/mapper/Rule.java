package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface Rule {

    int delete(Integer itemId);
    int deleteAll();
    List<?> getAll(Integer userId);
    int insert(Object add);
    <T> T getItemByItemId(Integer itemId);
    int update(Object update);

}
