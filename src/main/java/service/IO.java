package service;

import domain.stations;
import domain.trains;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class IO {
    static void Read() throws IOException {

            Scanner fileIn = new Scanner(Paths.get("/home/zix/projects/SQL ——project/src/main/resources/train_list.js"),"UTF-8");

            String[] arr = fileIn.nextLine().replace(" ","").split("\"},\\{\"");

            arr[0] = arr[0].substring(arr[0].indexOf(":[{")+4);
            arr[arr.length-1] = arr[arr.length-1].substring(0,arr[arr.length-1].length()-5);
        System.out.println(arr[arr.length-1]);
        trains[] trainsArr = new trains[arr.length];
        for (int i = 0; i < arr.length; i++) {
            trainsArr[i] = new trains("",arr[i].substring(arr[i].indexOf("code")+7,arr[i].indexOf("(")),
                    arr[i].substring(arr[i].indexOf("(")+1,arr[i].indexOf("-")),
                    arr[i].substring(arr[i].indexOf("-")+1,arr[i].indexOf(")")),
                    arr[i].substring(arr[i].lastIndexOf("train_no")+11));
            trainsArr[i].type=""+trainsArr[i].code.charAt(0);
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
        Scanner statonIn = new Scanner(Paths.get("/home/zix/projects/SQL ——project/src/main/resources/station_name.js"),
        "UTF-8");
        String temp = statonIn.nextLine().replace(" ","");
        temp = temp.substring(temp.indexOf("\'")+1,temp.lastIndexOf("\'"));
        String[] arrStation = temp.split("@");
        stations [] stationArr = new stations[arrStation.length-1];
        for (int i = 1; i < arrStation.length; i++) {
            String[] t = arrStation[i].split("\\|");
            stationArr[i-1] = new stations(t[0],t[1],t[2],t[3],t[4], Integer.valueOf(t[5]));
        }


        return;
    }

    public static void main(String[] args) throws IOException {
        Read();
    }
}
