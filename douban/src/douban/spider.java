package douban;

//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class spider {
    
    ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
    
    void webc (String url)
    {
        
        BufferedReader in = null;
        try
        {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            //File file = new File("C:/Users/YUAN/Documents/output3.txt");
            //FileWriter fileWriter = new FileWriter(file);
            String title = "";
            String author = "";
            String rate = "";
            String reader = "";
            String publisher = "";
            String date = "";
            String price = "";
            //boolean item = false;
            boolean info = false;

            //String pattern_1 = ".*<li class=" + "\"" + "subject-item" + "\"" + ">";
            String pattern_2 = "\\s*<span style=\"font-size:12px;\">.*\\W*.*</span>";
           // String pattern_3 = "\\s*<div class=" + "\"" + "info" + "\"" +">";
            String pattern_4 = "\\s*<a href=.*title=(.|\\W)*"; 
            //String pattern_5 = ".*</a>";
            while ((line = in.readLine()) != null)
            {
                
                if(Pattern.matches(pattern_4, line))
                {
                    //System.out.println(line);
                    int start = 0;
                    int end = 0;
                    for(int i = 0; i < line.length() - 7; i++ )
                    {
                        if(line.substring(i, i + 7).equals("title=\""))
                        {
                            start = i + 7;
                        }
                    }
                    for(int i = line.length() - 1; i >= 0; i--)
                    {
                        if(line.charAt(i) == '\"')
                        {
                            end = i;
                            break;
                        }
                        
                    }
                    title = line.substring(start, end); 
                    System.out.println(title);
                }
            
        
                else if (Pattern.matches(pattern_2, line))
                {
                    int start = 0;
                    int end = 0;
                    for(int i = 0; i < line.length(); i++)
                    {
                        if(line.charAt(i) == '>')
                            start = i + 1;
                        if(line.charAt(i) == '<' && line.charAt(i + 1) == '/')
                        {
                            end = i;
                            break;
                        }
                    }
                    title += line.substring(start, end);
                    System.out.println(title);
                }
                
                
                else if(Pattern.matches("\\s*<span class=\"rating_nums\">\\d\\.\\d</span>\\s*", line))
                {
                    int st = 0;
                    while(st < line.length())
                    {
                        if(line.charAt(st) == '>')
                            break;
                        else
                            st++;
                    }
                    rate = line.substring(st + 1, st + 4);
                    System.out.println(rate);
                }
            
                else if (Pattern.matches("\\s*\\(\\W*\\d*人评价\\).*", line))
                {
                    int st = 0;
                    while(st < line.length())
                    {
                        if(line.charAt(st) == '(')
                            break;
                        else
                            st++;
                    }
                    st = st + 1;
                    while (st < line.length())
                    {
                        if(line.charAt(st) >= '0' && line.charAt(st) <= '9')
                            break;
                        else
                        {
                            st++;
                        }
                    }
                    int end = st;
                    while(end < line.length())
                    {
                        if(line.charAt(end) >= '0' && line.charAt(end) <= '9')
                        {
                            end++;
                        }
                        else
                            break;
                    }
                    reader = line.substring(st, end);
                    System.out.println(reader + "人评价");
                    int readnum = Integer.valueOf(reader).intValue();
                    if(readnum >= 1000)
                    {
                        ArrayList<String> entry = new ArrayList<String>();
                        entry.add(title);
                        entry.add(rate);
                        entry.add(reader);
                        entry.add(author);
                        entry.add(publisher);
                        entry.add(date);
                        entry.add(price);
                        lst.add(entry);
                        System.out.println(title + ", " + rate + ", " + reader + ", " + author +", " + publisher + ", " + date + ", " + price);
                        title = "";
                        rate = "";
                        reader = "";
                        author = "";
                        publisher = "";
                        date = "";
                        price = "";
                    }
                }
               
                

                else 
                {
                    if (Pattern.matches(".*<div class=\"pub\">", line))
                        info = true;
                    else if(info)
                    {
                        if(!Pattern.matches("\\s*", line))
                        {
                            int index_1 = 0;
                            int index_2 = 0;
                            int index_3 = 0;
                            for(int i = line.length() - 1; i >= 0; i--)
                            {
                                if(line.charAt(i) == '/')
                                {
                                    if(index_1 == 0)
                                        index_1 = i;
                                    else if(index_2 == 0)
                                        index_2 = i;
                                    else if (index_3 == 0)
                                    {
                                        index_3 = i;
                                        break; 
                                    }   
                                }
                            }
                            price = line.substring(index_1 + 2, line.length());
                            System.out.println(price);
                            date = line.substring(index_2 + 2, index_1 - 1);
                            System.out.println(date);
                            publisher = line.substring(index_3 + 2, index_2 - 1);
                            System.out.println(publisher);
                            int st = 0;
                            for(int i = 0; i < line.length(); i++)
                            {
                                if(line.charAt(i) == ' ')
                                    st++;
                                else
                                    break;
                            }
                            for(int i = 0; i < line.length(); i++)
                            {
                                if(line.charAt(i) == '/')
                                {
                                    author = line.substring(st, i);
                                    System.out.println(author);
                                    break;
                                }
                            }
                            for(int i = 0; i < author.length() - 5; i++)
                            {
                                if(author.substring(i, i + 5).equals("&#39;"))
                                {
                                    author = author.substring(0, i) + "\'" + author.substring(i + 5);
                                    break;
                                }
                                
                            }
                            info = false;
                        }
                    }
                }
            
            }
               
            
            
            
                
            
        }
        catch (Exception e)
        {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(in != null)
                {
                    in.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }
}
