package domain;



public class trains_stations {
    private String train;
    private String depart_time;
    private String arrive_time;
    private String station_name;
    private Integer id;
    private Integer distance;
    private String stay_time;
    private Integer number_days;
    private String drive_time;

    public trains_stations() {
    }

    public trains_stations(String train, String depart_time, String arrive_time, String station_name) {
        this.train = train;
        this.depart_time = depart_time;
        this.arrive_time = arrive_time;
        this.station_name = station_name;
    }

    public trains_stations(String train, String depart_time, String arrive_time,
                           String station_name, Integer distance, String stay_time, Integer number_days, String drive_time) {
        this.train = train;
        this.depart_time = depart_time;
        this.arrive_time = arrive_time;
        this.station_name = station_name;
        this.distance = distance;
        this.stay_time = stay_time;
        this.number_days = number_days;
        this.drive_time = drive_time;
    }

    @Override
    public String toString() {
        return "trains_stations{" +
                "train='" + train + '\'' +
                ", depart_time='" + depart_time + '\'' +
                ", arrive_time='" + arrive_time + '\'' +
                ", station_name='" + station_name + '\'' +
                ", id=" + id +
                ", distance=" + distance +
                ", stay_time='" + stay_time + '\'' +
                ", number_days=" + number_days +
                ", drive_time='" + drive_time + '\'' +
                '}';
    }

    public Integer getDistance() {
        return distance;
    }

    public String getStay_time() {
        return stay_time;
    }

    public Integer getNumber_days() {
        return number_days;
    }

    public String getDrive_time() {
        return drive_time;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setStay_time(String stay_time) {
        this.stay_time = stay_time;
    }

    public void setNumber_days(Integer number_days) {
        this.number_days = number_days;
    }

    public void setDrive_time(String drive_time) {
        this.drive_time = drive_time;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrain() {
        return train;
    }

    public String getDepart_time() {
        return depart_time;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public String getStation_name() {
        return station_name;
    }

    public Integer getId() {
        return id;
    }
}
