package cn.com.suosi.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户信息
 */

@Data
@Table(name = "users")
public class User {
    /**
     * id，用户ID
     */
    @Id
    private Long id;
    /**
     * username，用户名
     */
    private String username;
    /**
     * password，密码
     */
    private String password;
    /**
     * realname，真实姓名
     */
    private String realname;
    /**
     * phonenumber，手机号码
     */
    private String phonenumber;
    /**
     * birthday，出生日期
     */
    private Date birthday;
    /**
     * logintime，注册时间
     */
    private Date logintime;
    /**
     * ip，登陆IP
     */
    private String ip;

    public User() {
    }

    public User(Long id, String username, String realname, String phonenumber, Date birthday) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
    }

    public User(String username, String password, String realname, String phonenumber, Date birthday) {
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
    }

}
