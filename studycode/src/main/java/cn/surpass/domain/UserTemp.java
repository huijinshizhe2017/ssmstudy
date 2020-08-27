package cn.surpass.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mybastis
 * cn.surpassdomain
 *
 * @author surpass
 * @date 2019/8/12
 */
public class UserTemp implements Serializable {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    public UserTemp(String sex, String address) {
        this.sex = sex;
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserTemp{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
