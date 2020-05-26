package domain;



public class stations {


    private String simpName;
    private String chineseName;
    private String codeName;
    private String pingYing;
    private String pyCode;
    private Integer id;

    public stations() {
    }

    public void setSimpName(String simpName) {
        this.simpName = simpName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setPingYing(String pingYing) {
        this.pingYing = pingYing;
    }

    public void setPyCode(String pyCode) {
        this.pyCode = pyCode;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public stations(String simpName, String chineseName, String codeName, String pingYing, String pyCode, Integer id) {
        this.simpName = simpName;
        this.chineseName = chineseName;
        this.codeName = codeName;
        this.pingYing = pingYing;
        this.pyCode = pyCode;
        this.id = id;
    }

    public String getSimpName() {
        return simpName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getPingYing() {
        return pingYing;
    }

    public String getPyCode() {
        return pyCode;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "stations{" +
                "simpName='" + simpName + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", codeName='" + codeName + '\'' +
                ", pingYing='" + pingYing + '\'' +
                ", pyCode='" + pyCode + '\'' +
                ", id=" + id +
                '}';
    }
}
