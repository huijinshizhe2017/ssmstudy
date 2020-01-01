package cn.surpass.domain;

import cn.surpass.domain.User;
import lombok.Data;

import java.io.Serializable;

/**
 * mybastis
 * cn.surpass.manyone.domain
 *
 * @author surpass
 * @date 2019/8/14
 */
@Data
public class Account implements Serializable {
    private Integer id;
    private Integer uid;
    private Double money;

    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", uid=" + uid +
                ", money=" + money +
                ", user=" + user +
                '}';
    }
}
