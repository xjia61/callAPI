package com.company;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.parser.JSONParser;
import scala.compat.java8.MakesParallelStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main {


    public static HashMap<String, Integer>
    sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list
                = new LinkedList<Map.Entry<String, Integer> >(
                hm.entrySet());

        // Sort the list using lambda expression
        Collections.sort(
                list,
                (i1,
                 i2) -> i2.getValue().compareTo(i1.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp
                = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }



    public static void main(String[] args) {
	// write your code here
        try{
            URL url =new URL("https://jsonmock.hackerrank.com/api/articles?page=2");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");

            if(conn.getResponseCode()!=200){
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            String str="";

            while((output=br.readLine())!=null){
                //System.out.println(output);
                str+=output;

            }



            Gson g= new Gson();
            Article sample1= g.fromJson(str,Article.class);
            //System.out.println(sample1.getPage()); test sample1
            HashMap<String, Integer> map= new HashMap<>();
            for(Data d:sample1.getData()){
            /*
            If both the title and the story title of an article are null, then we ignore such an article.
            Otherwise, let the name of an article be its title if the title is not null.
            Otherwise, if the title is null, let the name be the story_title of the article.
             */
                String s;
                if((s=d.getTitle())!=null){
                    map.put(s,d.getNum_comments());
                }else if((s=d.getStory_title())!=null){
                    map.put(s,d.getNum_comments());
                }
            }

            int limit = 2;//Set a limit value
            System.out.println(limit);
            System.out.println("\nSample Output\n");


            HashMap<String,Integer> res=sortByValue(map);
            for(Map.Entry<String,Integer> en:res.entrySet()){
                if(limit>0){
                    System.out.println(en.getKey());
                    limit--;
                }else{
                    break;
                }


            }
            limit=2;

            System.out.println("===========================");
            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
            Collections.sort(list, new ValueThenKeyComparator<String, Integer>());

            while(limit>0){
                System.out.println(list.get(limit).getKey());

                limit--;
            }



            conn.disconnect();

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }



    }
}
