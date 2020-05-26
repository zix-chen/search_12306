package domain;

public class order {
    private Integer idorders;
    private String create_date;
    private String orders_status;
    private double orders_price;
    private String user;
    private Integer tickets_id;

    public void setIdorders(Integer idorders) {
        this.idorders = idorders;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setOrders_status(String orders_status) {
        this.orders_status = orders_status;
    }

    public void setOrders_price(double orders_price) {
        this.orders_price = orders_price;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTickets_id(Integer tickets_id) {
        this.tickets_id = tickets_id;
    }

    public Integer getIdorders() {
        return idorders;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getOrders_status() {
        return orders_status;
    }

    public double getOrders_price() {
        return orders_price;
    }

    public String getUser() {
        return user;
    }

    public Integer getTickets_id() {
        return tickets_id;
    }

    public order() {
    }

    public order(String create_date, String orders_status, double orders_price, String user, Integer tickets_id) {
        this.create_date = create_date;
        this.orders_status = orders_status;
        this.orders_price = orders_price;
        this.user = user;
        this.tickets_id = tickets_id;
    }

    @Override
    public String toString() {
        return "order{" +
                "idorders=" + idorders +
                ", create_date='" + create_date + '\'' +
                ", orders_status='" + orders_status + '\'' +
                ", orders_price=" + orders_price +
                ", user='" + user + '\'' +
                ", tickets_id=" + tickets_id +
                '}';
    }
}
