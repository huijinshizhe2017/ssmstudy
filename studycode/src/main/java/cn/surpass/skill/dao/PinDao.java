package cn.surpass.skill.dao;

import cn.surpass.skill.model.IdsPin;

import java.util.List;

/**
 * ssmstudy
 * cn.surpass.skill.dao
 *
 * @author surpass
 * @date 2020/3/15
 */
public interface PinDao {

    void insertPin(IdsPin pin);

    List<IdsPin> queryPin();
}
