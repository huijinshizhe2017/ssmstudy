package cn.surpass.skill.model;

/**
 * @Description
 * @Author SurpassLiang
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/4/17
 */
public class IdModel {
    private String id;
    private String type;

    public IdModel(String type, Long id) {
        this.id = String.valueOf(id);
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
