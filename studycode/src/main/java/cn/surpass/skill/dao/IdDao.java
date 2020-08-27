package cn.surpass.skill.dao;

import cn.surpass.skill.model.IdModel;

public interface IdDao {

    String queryIdByType(String type);

    void insertIdByType(IdModel idModel);

    void updateIdByType(IdModel idModel);
}
