package cn.surpass.domain;

import cn.surpass.domain.User;
import lombok.Data;

/**
 * mybastis
 * cn.surpass.curd.domain
 *
 * @author surpass
 * @date 2019/8/13
 */
@Data
public class QueryVo {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
