package com.zkn.server.repository;

import com.zkn.server.repository.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
public interface UserDao extends CrudRepository<User, String> {

    /**
     * 根据账号获取用户ID
     * @param account 账号
     * @return 用户id
     */
    Optional<User> findByAccount(String account);
}
