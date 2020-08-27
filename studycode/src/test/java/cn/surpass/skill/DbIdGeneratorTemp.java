package cn.surpass.skill;

import cn.surpass.skill.dao.IdDao;
import cn.surpass.skill.model.IdModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author SurpassLiang
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/4/17
 */
public class DbIdGeneratorTemp {

    private static final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>();
    private IdDao idDao;

    public DbIdGeneratorTemp(IdDao idDao) {
        this.idDao = idDao;
    }

    public String getNextId(String objectType) {
        long startNum = generateIds(objectType, 1);
        return formatId(objectType, startNum);
    }


    public List<String> getNextIds(String objectType, int count) {
        long startNum = generateIds(objectType, count);
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ids.add(formatId(objectType, startNum + i));
        }
        return ids;
    }


    private long generateIds(String objType, long count) {
        if (!locks.containsKey(objType)) {
            locks.putIfAbsent(objType, new ReentrantLock());
        }
        long result = 1;
        locks.get(objType).lock();
        try {
            String key = objType + ".nextId";
            String lastId = idDao.queryIdByType(key);
            if (lastId != null) {
                result = Long.parseLong(lastId);
                idDao.updateIdByType(new IdModel(key,result + count));
            } else {
                idDao.insertIdByType(new IdModel(key, count + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locks.get(objType).unlock();
            return result;
        }
    }

    private String formatId(String objectType, long id) {
        return String.format(objectType + "%08d", id);
    }
}