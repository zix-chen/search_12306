import dao.ITicketsDao;
import domain.stations;
import domain.tickets;
import domain.trains;
import domain.trains_stations;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class test {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private ITicketsDao ticketsDao;

    @Before
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        ticketsDao = session.getMapper(ITicketsDao.class);
    }


    @After
    public void dectory() throws IOException {
        session.commit();
        session.close();
        in.close();
    }

    @Test
    public void t() {
        tickets tk = new tickets(0,"f","D1",0,null,"fdf","ff",1);
        ticketsDao.saveTickets(tk);
    }
    @Test
    public void tt(){
        ticketsDao.delete("夹心子");
    }
    @Test
    public void findTicketsByStation() {
        List<String> strs = ticketsDao.findTrainsByDepartAndArrive("北京", "沈阳");

        System.out.println("this are the list fo trains that you can chose");
        HashSet<trains> set = new HashSet<>();
        for (String i : strs) {
            trains tmp = ticketsDao.findTrainsByCode(i);
            System.out.println(tmp);
            set.add(tmp);
        }

    }

    @Test
    public void findTickets() {
        List<tickets> strs = ticketsDao.findTicket("北京", "沈阳");
        for (tickets t : strs) {
            System.out.println(t);
        }
    }

    @Test
    public void testInsert() {
        stations s = new stations("f", "f", "f", "f", "f", Integer.MAX_VALUE);
        ticketsDao.saveStations(s);
    }

    @Test
    public void testInsertTrain() {
        trains s = new trains("f", "f", "fsae", "分", "而非");
        ticketsDao.saveTrains(s);
    }

    @Test
    public void testInsertTrain_s() {
        trains_stations ts = new trains_stations("f", "2018-1-1 11:12",
                "2018-01-01 11:11", "2147483647");
        ticketsDao.saveTrains_stations(ts);
    }

    @Test
    public void testfindAll() {
        List<stations> list = ticketsDao.findAllStations();
        for (stations station : list) {
            System.out.println(station);
        }
    }

    @Test
    public void testfindAllT_s() {
        List<trains_stations> list = ticketsDao.findAllTrasin_s();
        for (trains_stations ts : list) {
            System.out.println(ts);
        }
    }



    @Test
    public void Read() throws IOException {

        Scanner fileIn = new Scanner(Paths.get("/home/zix/projects/SQL ——project/src/main/resources/train_list.js"), "UTF-8");

        String[] arr = fileIn.nextLine().replace(" ", "").split("\"},\\{\"");

        arr[0] = arr[0].substring(arr[0].indexOf(":[{") + 4);
        arr[arr.length - 1] = arr[arr.length - 1].substring(0, arr[arr.length - 1].length() - 5);
        System.out.println(arr[arr.length - 1]);
        trains[] trainsArr = new trains[arr.length];
        for (int i = 0; i < arr.length; i++) {
            trainsArr[i] = new trains("", arr[i].substring(arr[i].indexOf("code") + 7, arr[i].indexOf("(")),
                    arr[i].substring(arr[i].indexOf("(") + 1, arr[i].indexOf("-")),
                    arr[i].substring(arr[i].indexOf("-") + 1, arr[i].indexOf(")")),
                    arr[i].substring(arr[i].lastIndexOf("train_no") + 11));
            trainsArr[i].type = "" + trainsArr[i].code.charAt(0);
        }
//        System.out.println(trainsArr[0].toString());
        /*PrintWriter printWriter = new PrintWriter("/home/zix/projects/SQL ——project/src/main/resources/nonono"
        ,"UTF-8");
        printWriter.println();*/
        fileIn.close();
        /*String str = "";
        for (int i = 0; i < trainsArr.length; i++) {
            if(trainsArr[i].type.equals("D")){
//                str+="http://shike.gaotie.cn/checi.asp?checi="+trainsArr[i].trainId+"\n";
                printWriter.println("http://shike.gaotie.cn/checi.asp?checi="+trainsArr[i].trainId);
            }
        }*/
        /*for (int i = 0; i < trainsArr.length; i++) {
            if(trainsArr[i].depart_station.charAt(0)=='南'){
                System.out.println(trainsArr[i].depart_station);
            }
        }*/
        /*Scanner statonIn = new Scanner(Paths.get("/home/zix/projects/SQL ——project/src/main/resources/station_name.js"),
                "UTF-8");
        String temp = statonIn.nextLine().replace(" ", "");
        temp = temp.substring(temp.indexOf("\'") + 1, temp.lastIndexOf("\'"));
        String[] arrStation = temp.split("@");
        stations[] stationArr = new stations[arrStation.length - 1];
        for (int i = 1; i < arrStation.length; i++) {
            String[] t = arrStation[i].split("\\|");
            stationArr[i - 1] = new stations(t[0], t[1], t[2], t[3], t[4], Integer.valueOf(t[5]));
        }

        for (int i = 0; i < stationArr.length; i++) {
            ticketsDao.saveStations(stationArr[i]);
        }
        for (int i = 0; i < trainsArr.length; i++) {
            ticketsDao.saveTrains(trainsArr[i]);
        }*/
        /**
         * 插入tickets我爬到的,
         */
        List<String> trainCodeSet = ticketsDao.findAllTrainCode();
        List<String> list = ticketsDao.findAllTrainCode();
        for (int i = 0; i < trainsArr.length; i++) {
            if (trainCodeSet.contains(trainsArr[i].code)) {
                System.out.println(trainsArr[i]);
                tickets ticket = new tickets(100, "一等座", trainsArr[i].code, 10, null, trainsArr[i].depart_station, trainsArr[i].arrive_station, 0);
                ticketsDao.saveTickets(ticket);

                ticket.setSeat_type("二等座");
                ticket.setRest_tickets(50);
                ticketsDao.saveTickets(ticket);
            }
        }


        return;
    }

    @Test
    public void read2() throws IOException {
        Scanner prein = new Scanner(Paths.get("/home/zix/IdeaProjects/SQL_project_final/src/main/resources/WithOutDupLine.txt")
                , "UTF-8");
        Scanner in = new Scanner(Paths.get("/home/zix/IdeaProjects/SQL_project_final/src/main/resources/trains.csv"),
                "UTF-8");
        HashSet<trains_stations> set = new HashSet<>();
        in.nextLine();
        String str = in.nextLine();
        String strPre = "";
        while (str != "\0") {
            String[] temp = str.split(",");
            if (str.charAt(0) == '1' && str.charAt(1) == ',') {
                strPre = prein.nextLine();
            }

            if (temp[3].equals("始发站")) {
                temp[3] = temp[4];
            } else if (temp[4].equals("终点站")) {
                temp[4] = temp[3];
            }
            trains_stations ts = new trains_stations(strPre.substring(strPre.lastIndexOf('=') + 1),
                    "2020-5-18 " + temp[3],
                    "2020-5-18 " + temp[4], temp[2].substring(0,temp[2].length()-1), Integer.valueOf(temp[8]), temp[5], Integer.valueOf(temp[6]), temp[7]);
         /*   if(ts.getStation_name().charAt(0)=='北'){
                System.out.println(ts);
            }*/
            ticketsDao.saveTrains_stations(ts);
//        set.add(ts);
            str = in.nextLine();
            while ((str.charAt(0) == '/' || str.charAt(0) == '\"')) {
                str = in.nextLine();
                if (!in.hasNext()) {

                    return;
                }
            }
        }
    }

    /*@Test
    public void read3(){
       List<trains_stations> list = ticketsDao.findAllTrasin_s();
       for (trains_stations ts:list){
//           System.out.println(ts.getStation_name().charAt(ts.getStation_name().length()-1));
           if(ts.getStation_name().charAt(ts.getStation_name().length()-1)=='站'){
               System.out.println(ts.getStation_name());
               ts.setStation_name(ts.getStation_name().substring(0,ts.getStation_name().length()-1));
               ticketsDao.saveTrains_stations(ts);
           }
//           ticketsDao.saveTrains_stations(ts);
       }
    }*/
}