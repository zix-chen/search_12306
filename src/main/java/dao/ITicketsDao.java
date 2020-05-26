package dao;

import domain.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ITicketsDao {
    @Select("select * from stations")
    @Results(id = "stationMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "code_name", property = "codeName"),
            @Result(column = "simp_name", property = "simpName"),
            @Result(column = "py_code", property = "pyCode"),
            @Result(column = "chinese_name", property = "chineseName"),
            @Result(column = "ping_ying", property = "pingYing")
    })
    List<stations> findAllStations();

    @Select("select * from trains_stations")
    List<trains_stations> findAllTrasin_s();

    @Insert("insert into stations (code_name, simp_name, ping_ying, id, py_code, chinese_name) VALUES (#{codeName},#{simpName}," +
            " #{pingYing},#{id},#{pyCode},#{chineseName}) ON DUPLICATE KEY UPDATE code_name=#{codeName}")
    void saveStations(stations station);

    @Insert("insert into trains (code, train_name, depart_time, depart_station, arrive_station) " +
            "VALUES (#{code},#{train_name},#{depart_time},#{depart_station},#{arrive_station}) " +
            "ON DUPLICATE KEY UPDATE code=#{code}")
    void saveTrains(trains train);

    @Insert("insert into trains_stations (train, depart_time, arrive_time, station_name," +
            " distance, stay_time, number_days, drive_time) VALUES (#{train},#{depart_time}," +
            "#{arrive_time},#{station_name},#{distance},#{stay_time},#{number_days},#{drive_time})" +
            "on duplicate key update station_name=#{station_name}")
    void saveTrains_stations(trains_stations t_s);

    @Insert("insert into tickets (tickets_price, seat_type, train, rest_tickets, depart_time," +
            " depart_station, arrive_station, tickets_orderd) VALUES (" +
            "#{tickets_price}, #{seat_type}, #{train}, #{rest_tickets}, #{depart_time}" +
            " ,#{depart_station}, #{arrive_station},  #{tickets_orderd})" )
//            "on duplicate key update tickets_price=#{tickets_price}")
    void saveTickets(tickets ticket);

    @Select("select distinct train from trains_stations group by train;")
    List<String> findAllTrainCode();

    /**
     * 有点错,没考虑余票
     *
     * @param dp
     * @param arri
     * @return
     */
    @Select("select t1.train from trains_stations t1  join trains_stations t2 where t1.distance<t2.distance " +
            "and t1.train=t2.train and t1.station_name like '%${dp}%' and t2.station_name like '%${ss}%';")
    List<String> findTrainsByDepartAndArrive(@Param("dp") String dp, @Param("ss") String arri);

    /**
     * 正确找法
     *
     * @param dp
     * @param arri
     * @return
     */
    @Select("select distinct tickets.id,\n" +
            "                tickets_price,\n" +
            "                seat_type,\n" +
            "                a.train,\n" +
            "                rest_tickets,\n" +
            "                a.t1time depart_time,\n" +
            "                t1name   depart_station,\n" +
            "                t2name   arrive_station,\n" +
            "                0        tickerts_orderd\n" +
            "from tickets\n" +
            "         join trains_stations ts on tickets.train = ts.train\n" +
            "         join\n" +
            "     (select t1.distance     t1t,\n" +
            "             t2.distance     t2t,\n" +
            "             t1.train,\n" +
            "             t1.station_name t1name,\n" +
            "             t2.station_name t2name,\n" +
            "             t1.depart_time  t1time,\n" +
            "             t2.depart_time  t2time\n" +
            "      from trains_stations t1\n" +
            "               join trains_stations t2\n" +
            "      where t1.distance < t2.distance\n" +
            "        and t1.train = t2.train\n" +
            "        and t1.station_name like '%${dp}%'\n" +
            "        and t2.station_name like '%${ss}%') a\n" +
            "         join trains_stations ts2 on tickets.train = ts2.train and tickets.train=a.train\n" +
            "        and tickets.rest_tickets>0\n" +
            "    and ts.station_name = a.t1name and ts2.station_name = a.t2name and ts.distance <= t1t and ts2.distance >= t2t;")
    List<tickets> findTicket(@Param("dp") String dp, @Param("ss") String arri);


    @Select("select * from trains where code=#{value}")
    trains findTrainsByCode(String i);

    @Select("select * from users where ID_card_num=#{value};")
    user findUserById(String id);

    @Insert("insert into users (ID_card_num, usersname, phone_number, admin," +
            " pass_word)values (#{ID_card_num}, #{username}, #{phone_number}, #{admin}, #{pass_word});")
    void saveUser(user usr);

    @Select("select * from tickets where id =#{value};")
    tickets findTicketsById(Integer id);

    @Update("update tickets SET rest_tickets=#{rest_tickets},tickets_orderd=#{tickets_orderd} where id=#{id};")
    void updateTickets(tickets ticket);

    @Select("select *\n" +
            "from tickets\n" +
            "where train = #{train}\n" +
            "  and depart_station = #{depart_station}" +
            "and arrive_station = #{arrive_station}\n" +
            "  and seat_type = #{seat_type};")
    tickets findTicketsByUniqueKey(@Param("train") String train,@Param("depart_station")String dep,
                                   @Param("arrive_station")String arrt,@Param("seat_type")String seat);

  /*  @Delete("delete from #{a} where #{b}=#{c};")
    void delete(@Param("a") String a,@Param("b") String b,@Param("c") String c);*/

    @Delete("delete from stations where chinese_name=#{c};")
    void delete(@Param("c") String c);

    @Insert("insert into orders (create_date, orders_status, orders_price, user, tickets_id)\n" +
            "VALUES (now(),#{orders_status},#{orders_price},#{user},#{tickets_id});")
    void saveOrder(order od);

    @Select("select * from orders  where user=#{value};")
    List<order> findOrdersByUser(String name);

}
