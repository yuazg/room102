package douban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class main {

    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
       //String link [] = new String [50];
       spider spd = new spider ();
       String a = "https://book.douban.com/tag/编程?";
       String z = "type=S";
       spd.webc(a + z);
       for(int i = 1; i < 50; i++)
       {
           spd.webc(a + "start=" + String.valueOf(i * 20) + "&" + z);
       }
       
       Collections.sort(spd.lst, new SortbyRate());
       
       try
       {
           File file = new File("C:/Users/YUAN/Documents/booklist.csv");
           FileWriter fileWriter = new FileWriter(file);
           fileWriter.write("序号" + ", " + "书名" + ", " + "评价人数" + ", " + "作者" + ", " + "出版社" + ", " + "出版日期" + ", " + "价格" + "\n");
           for(int i = 0; i < 40/*spd.lst.size()*/; i++)
           {
               fileWriter.write(i+1 + ", " + spd.lst.get(i).get(0) + ", " + spd.lst.get(i).get(1) + ", " + spd.lst.get(i).get(2) + ", " + spd.lst.get(i).get(3) + ", " + spd.lst.get(i).get(4) + ", " + spd.lst.get(i).get(5) + ", " + spd.lst.get(i).get(6) + "\n");
               fileWriter.flush();
           }
           
           
           fileWriter.close();
       }
       catch (IOException e)
       {
           e.printStackTrace();
       }
       System.out.println(spd.lst.size());
       
       
    }
    
    
    

}

class SortbyRate implements Comparator {
    public int compare(Object l1, Object l2) {
        ArrayList<String> s1 =  (ArrayList<String>) l1;
        ArrayList<String> s2 =  (ArrayList<String>) l2;
        if (Double.parseDouble(s1.get(1)) > Double.parseDouble(s2.get(1)))
            return -1;
        else
            return 1;
    }
}
