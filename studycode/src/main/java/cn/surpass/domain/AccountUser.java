package cn.surpass.domain;

import lombok.Data;

/**
 * mybastis
 * cn.surpass.manyone.domain
 *
 * @author surpass
 * @date 2019/8/14
 */
@Data
public class AccountUser extends Account {
    private String username;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AccountUser{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
