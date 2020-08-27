package cn.surpass.skill.model;

/**
 * ssmstudy
 * cn.surpass.skill.model
 *
 * @author surpass
 * @date 2020/3/15
 */
public class IdsPin {
    private Integer id;
    private IdsConnector conn;
    private String pinCode;
    private String direction;
    private String type;
    private String code;
    private String signalContent;
    private String loadCharacter;
    private String loadVoltage;
    private String loadCurrent;
    private String twinTwist;
    private String shield;
    private String shieldGround;
    private String color;
    private String linenumber;
    private String remark;
    private Integer isIgnore;
    private String fileListId;
    private String mainAttrid;
    private Integer pinLevel;
    private String lastreplacedpinid;
    private String fromAttrid;
    private String toAttrid;
    private Integer prioLevel;
    private String primaryAttr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public IdsConnector getConn() {
        return conn;
    }

    public void setConn(IdsConnector conn) {
        this.conn = conn;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSignalContent() {
        return signalContent;
    }

    public void setSignalContent(String signalContent) {
        this.signalContent = signalContent;
    }

    public String getLoadCharacter() {
        return loadCharacter;
    }

    public void setLoadCharacter(String loadCharacter) {
        this.loadCharacter = loadCharacter;
    }

    public String getLoadVoltage() {
        return loadVoltage;
    }

    public void setLoadVoltage(String loadVoltage) {
        this.loadVoltage = loadVoltage;
    }

    public String getLoadCurrent() {
        return loadCurrent;
    }

    public void setLoadCurrent(String loadCurrent) {
        this.loadCurrent = loadCurrent;
    }

    public String getTwinTwist() {
        return twinTwist;
    }

    public void setTwinTwist(String twinTwist) {
        this.twinTwist = twinTwist;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getShieldGround() {
        return shieldGround;
    }

    public void setShieldGround(String shieldGround) {
        this.shieldGround = shieldGround;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLinenumber() {
        return linenumber;
    }

    public void setLinenumber(String linenumber) {
        this.linenumber = linenumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsIgnore() {
        return isIgnore;
    }

    public void setIsIgnore(Integer isIgnore) {
        this.isIgnore = isIgnore;
    }

    public String getFileListId() {
        return fileListId;
    }

    public void setFileListId(String fileListId) {
        this.fileListId = fileListId;
    }

    public String getMainAttrid() {
        return mainAttrid;
    }

    public void setMainAttrid(String mainAttrid) {
        this.mainAttrid = mainAttrid;
    }

    public Integer getPinLevel() {
        return pinLevel;
    }

    public void setPinLevel(Integer pinLevel) {
        this.pinLevel = pinLevel;
    }

    public String getLastreplacedpinid() {
        return lastreplacedpinid;
    }

    public void setLastreplacedpinid(String lastreplacedpinid) {
        this.lastreplacedpinid = lastreplacedpinid;
    }

    public String getFromAttrid() {
        return fromAttrid;
    }

    public void setFromAttrid(String fromAttrid) {
        this.fromAttrid = fromAttrid;
    }

    public String getToAttrid() {
        return toAttrid;
    }

    public void setToAttrid(String toAttrid) {
        this.toAttrid = toAttrid;
    }

    public Integer getPrioLevel() {
        return prioLevel;
    }

    public void setPrioLevel(Integer prioLevel) {
        this.prioLevel = prioLevel;
    }

    public String getPrimaryAttr() {
        return primaryAttr;
    }

    public void setPrimaryAttr(String primaryAttr) {
        this.primaryAttr = primaryAttr;
    }

    @Override
    public String toString() {
        return "IdsPin{" +
                "connId='" + conn.getId() + '\'' +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }
}
