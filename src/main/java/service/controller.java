package service;

import dao.ITicketsDao;
import domain.order;
import domain.tickets;
import domain.user;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class controller {
    boolean login = false;
    user usr;
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private ITicketsDao ticketsDao;
    public String depart;
    public String arri;
    public String trains;
    private Scanner cmdIn;
    private List<tickets> TicketsList;

    public static void main(String[] args) throws IOException {
        controller cr = new controller();
        cr.control();
        cr.dectory();

    }

    @Override
    public String toString() {
        return "you ticket{" +
                "depart='" + depart + '\'' +
                ", arri='" + arri + '\'' +
                ", trains='" + trains + '\'' +
                '}';
    }

    public controller() throws IOException {
        init();
    }

    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        ticketsDao = session.getMapper(ITicketsDao.class);
        cmdIn = new Scanner(System.in);
    }


    public void dectory() throws IOException {
        session.commit();
        session.close();
        in.close();

    }

    void control() throws IOException {
        controller c = new controller();

        while (true) {
            show();
            switch (cmdIn.nextInt()) {
                case 1:
                    Register();
                    break;
                case 2:
                    Login();
                    break;
                case 3:
                    Order();
                    break;
                case 4:
                    FindTickets();
                    break;
                case 5:
                    admin();
                    break;
                case 6:
                    ReturnTickets();
                    break;
                case 7:
                    return;
            }
            session.commit();
        }
    }

    void show() {
        System.out.println("Please input:\n" +
                "1: register\t\t\t" +
                "2: login\n" +
                "3: make a order\t\t\t" +
                "4: find tickets\n" +
                "5: admin\t\t\t" +
                "6: return tickets\n" +
                "7: quit");
    }

    void requestDepAndArri() {
        System.out.println("please input depart station and arrive station: \n");
        System.out.println("depart: ");
        depart = cmdIn.next();
        System.out.println("arri: ");
        arri = cmdIn.next();

    }

    void reqTrains() {
        System.out.println("please input the trans: \n");
        System.out.println("depart: ");
        depart = cmdIn.next();
        System.out.println("arri: ");
        arri = cmdIn.next();
    }

    void findTicketsByStation() {
        TicketsList = ticketsDao.findTicket(depart, arri);
        System.out.println("this are the list fo trains that you can chose");
        for (tickets t : TicketsList) {
            System.out.println(t);
        }

    }

    private void Register() {
        System.out.println("Enter the id userName phonenumber and pwd: ");
        usr = new user(cmdIn.next(), cmdIn.next(), cmdIn.next(), false, cmdIn.next());
        if (ticketsDao.findUserById(usr.getID_card_num()) != null && ticketsDao.findUserById(usr.getID_card_num()).getUsername() != null) {
            System.out.println("the id has been used! ");
            return;
        }
        ticketsDao.saveUser(usr);
        System.out.println("register succeed! ");
        login = true;
    }

    private void Login() {
        System.out.println("please lonin: \n id: ");
        String id = cmdIn.next();
        System.out.println("pass_word: ");
        String pwd = cmdIn.next();
        user tmp = ticketsDao.findUserById(id);
        if (tmp != null && tmp.getPass_word().equals(pwd)) {
            usr = tmp;
            System.out.println("login succeed! ");
            login = true;
        } else {
            System.out.println("wrong pwd! ");
        }
    }

    public void Order() {
        if (!login) {
            Login();
        }
        if (TicketsList != null && !TicketsList.isEmpty()) {
            for (tickets t : TicketsList) {
                System.out.println(t);
            }
            System.out.println("chose one ticket by id: ");
            int id = cmdIn.nextInt();
            tickets tk;
            for (tickets t : TicketsList) {
                if (t.getId() == id) {
                    tk = t;
                    changeDB(tk);
                    return;
                }
            }

        } else {
            FindTickets();
        }

    }

    private void changeDB(tickets virtualTK) {

       /*
        tickets tureTicket = ticketsDao.findTicketsById(virtualTK.getId());
        if (tureTicket.getDepart_station().equals(virtualTK.getDepart_station())//直接和起点和终点站对应
                && tureTicket.getArrive_station().equals(virtualTK.getArrive_station())) {
            tureTicket.setRest_tickets(tureTicket.getRest_tickets() - 1);
            tureTicket.setTickets_orderd(tureTicket.getTickets_orderd() + 1);
            ticketsDao.updateTickets(tureTicket);
        } else {

            tickets mybeSameTickets = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(),
                    virtualTK.getDepart_station(), virtualTK.getArrive_station(), virtualTK.getSeat_type());
            if (mybeSameTickets != null && mybeSameTickets.getId() != null) {//之前拆分过站点,正好有对应的
                mybeSameTickets.setTickets_orderd(mybeSameTickets.getTickets_orderd() + 1);//让其占用加一;
                ticketsDao.updateTickets(mybeSameTickets);//commit;
                if (tureTicket.getDepart_station().equals(virtualTK.getDepart_station())) {
                    tickets anoTicket = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(),
                            virtualTK.getDepart_station(), virtualTK.getArrive_station(), virtualTK.getSeat_type());
                    if (anoTicket != null && anoTicket.getId() != null) {//因为拆分过另一块也可能找得到.
                        anoTicket.setRest_tickets(anoTicket.getRest_tickets() + 1);
                        ticketsDao.updateTickets(anoTicket);

                        tureTicket.setRest_tickets(tureTicket.getRest_tickets() - 1);
                        ticketsDao.updateTickets(tureTicket);
                    } else {
                        ticketsDao.saveTickets(new tickets(0, virtualTK.getSeat_type(), virtualTK.getTrain()
                                , 1, null, virtualTK.getArrive_station(),
                                tureTicket.getArrive_station(), 0));
                    }
                }
            } else if (tureTicket.getDepart_station().equals(virtualTK.getDepart_station())) {
                tickets ano2 = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(), virtualTK.getDepart_station(), virtualTK.getArrive_station()
                        , virtualTK.getSeat_type());
                virtualTK.setRest_tickets(0);
                virtualTK.setTickets_orderd(1);
                ticketsDao.saveTickets(virtualTK);
                ticketsDao.saveTickets(new tickets(virtualTK.getTickets_price(), virtualTK.getSeat_type(),
                        virtualTK.getTrain(), 1, null, virtualTK.getArrive_station(),
                        tureTicket.getArrive_station(), 0));
            }
        }
        tickets finalTK = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(), virtualTK.getDepart_station(), virtualTK.getArrive_station(), virtualTK.getSeat_type());
        if (finalTK == null) {
            virtualTK.setTickets_orderd(1);
            virtualTK.setRest_tickets(0);
            ticketsDao.saveTickets(virtualTK);
        }
        finalTK = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(), virtualTK.getDepart_station(), virtualTK.getArrive_station(), virtualTK.getSeat_type());
        System.out.println("it s the ticket:  ");
        System.out.println(finalTK);
        order od = new order(null, "OK", 0, usr.getID_card_num(), finalTK.getId());
        ticketsDao.saveOrder(od);
        */
        tickets tureTicket = ticketsDao.findTicketsById(virtualTK.getId());//找到拥有该id的真实ticket
        if (tureTicket.getDepart_station().equals(virtualTK.getDepart_station())//直接和起点和终点站对应
                && tureTicket.getArrive_station().equals(virtualTK.getArrive_station())) {
            tureTicket.setRest_tickets(tureTicket.getRest_tickets() - 1);
            tureTicket.setTickets_orderd(tureTicket.getTickets_orderd() + 1);
            ticketsDao.updateTickets(tureTicket);
        } else {
            tureTicket.setRest_tickets(tureTicket.getRest_tickets() - 1);//减少全程车次的票数量
            ticketsDao.updateTickets(tureTicket);

            if (!virtualTK.getDepart_station().equals(tureTicket.getDepart_station())) {//增加票前面的剩余车次
                tickets bef = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(),
                        tureTicket.getDepart_station(),
                        virtualTK.getDepart_station(),
                        virtualTK.getSeat_type());
                if (bef == null || bef.getId() == null) {
                    ticketsDao.saveTickets(new tickets(0,
                            virtualTK.getSeat_type(),
                            virtualTK.getTrain(),
                            0, null,
                            tureTicket.getDepart_station(),
                            virtualTK.getDepart_station(),
                            1));
                }else {
                    bef.setRest_tickets(bef.getRest_tickets()+1);
                    ticketsDao.updateTickets(bef);
                }
            }

            if (!virtualTK.getArrive_station().equals(tureTicket.getArrive_station())) {//增加票后面的剩余车次
                tickets aft = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(),
                        virtualTK.getArrive_station(),
                        tureTicket.getArrive_station(),
                        virtualTK.getSeat_type());
                if (aft == null) {
                    ticketsDao.saveTickets(new tickets(0,
                            virtualTK.getSeat_type(),
                            virtualTK.getTrain(),
                            0, null,
                            virtualTK.getArrive_station(),
                            tureTicket.getArrive_station(),
                            1));
                }else {
                    aft.setRest_tickets(aft.getRest_tickets()+1);
                    ticketsDao.updateTickets(aft);
                }
            }

            tickets mid = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(),
                    virtualTK.getDepart_station(),
                    virtualTK.getArrive_station(),
                    virtualTK.getSeat_type());
            if(mid==null){
                virtualTK.setRest_tickets(0);
                virtualTK.setTickets_orderd(1);
                ticketsDao.saveTickets(virtualTK);
            }else {
                mid.setTickets_orderd(mid.getTickets_orderd()+1);
                ticketsDao.updateTickets(mid);
            }
        }
        tickets finalTK = ticketsDao.findTicketsByUniqueKey(virtualTK.getTrain(),virtualTK.getDepart_station(),
                virtualTK.getArrive_station(),virtualTK.getSeat_type());//搜索存到DB的真实票
        System.out.println("it s the information of the final ticket:  ");
        System.out.println(finalTK);
        order od = new order(null, "OK", 0, usr.getID_card_num(), finalTK.getId());
        ticketsDao.saveOrder(od);
        showOrders();
    }
    public void showOrders(){
        List<order> list = ticketsDao.findOrdersByUser(usr.getID_card_num());
        System.out.println("the information of your orders");
        for (order odr:list){
            System.out.println(odr);
        }
    }

    public void FindTickets() {
        requestDepAndArri();
        findTicketsByStation();
    }

    public void admin() {
        if (!login) {
            Login();
        }
        if (!usr.isAdmin()) {
            System.out.println("not admin");
            return;
        }
        System.out.println("the name of station to remove: ");
        String str = cmdIn.next();
        ticketsDao.delete(str);
    }

    public void ReturnTickets() {

    }
}
