package cn.com.suosi.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户信息
 * id，ID
 * username，用户名
 * password，密码
 * realname，真实姓名
 * phonenumber，手机号码
 * birthday，出生日期
 * logintime，注册时间
 * ip，登陆IP
 *
 * */

@Data
@Table(name="users")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String realname;
    private String phonenumber;
    private Date birthday;
    private Date logintime;
    private String ip;

    public User(){}

    public User(String username,String password,String realname,String phonenumber,Date birthday){
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
    }

    public User(Long id,String username, String realname, String phonenumber, Date birthday) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
    }
}
