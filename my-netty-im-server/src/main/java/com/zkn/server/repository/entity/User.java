package com.zkn.server.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@Data
@Entity
@Table(name ="t_user")
public class User {

    @Id
    private String id;

    private String account;
}
