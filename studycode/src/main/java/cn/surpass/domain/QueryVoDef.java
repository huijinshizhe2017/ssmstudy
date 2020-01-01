package cn.surpass.domain;


import cn.surpass.domain.User;
import lombok.Data;

import java.util.List;

/**
 * mybastis
 * cn.surpass.curd.domain
 *
 * @author surpass
 * @date 2019/8/13
 */
@Data
public class QueryVoDef {

    private User user;

    private List<Integer> ids;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "QueryVoDef{" +
                "user=" + user +
                ", ids=" + ids +
                '}';
    }
}
