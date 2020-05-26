package domain;

public class trains {
//    static int addone = 1;
//     int idtrains;

    public trains() {
    }

    public trains(String type, String code, String depart_station, String arrive_station,String train_name) {
        this.type = type;
        this.code = code;
        this.depart_station = depart_station;
        this.arrive_station = arrive_station;
        this.train_name = train_name;
    }

    @Override
    public String toString() {
        return "trains{" +
                "type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", depart_station='" + depart_station + '\'' +
                ", arrive_station='" + arrive_station + '\'' +
                ", depart_time='" + depart_time + '\'' +
                ", train_name='" + train_name + '\'' +
                '}';
    }

    public String type;
    public String code;
    public String depart_station;
    public String arrive_station;
    public String depart_time;
    public String train_name;

    public void setType(String type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDepart_station(String depart_station) {
        this.depart_station = depart_station;
    }

    public void setArrive_station(String arrive_station) {
        this.arrive_station = arrive_station;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getDepart_station() {
        return depart_station;
    }

    public String getArrive_station() {
        return arrive_station;
    }

    public String getDepart_time() {
        return depart_time;
    }

    public String getTrain_name() {
        return train_name;
    }
}
