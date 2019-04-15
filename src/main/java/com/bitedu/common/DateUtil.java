package com.bitedu.common;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class DateUtil {

    public List<String> getAllDate(String begin, String end)  {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> res = new ArrayList<>();
        try {
            Calendar cbegin = Calendar.getInstance();
            cbegin.setTime(sdf.parse(begin));

            Calendar cend = Calendar.getInstance();
            cend.setTime(sdf.parse(end));


            while(!cend.getTime().equals(cbegin.getTime())){
                res.add(sdf.format(cbegin.getTime()));
                cbegin.add(Calendar.DATE, 1);

            }
            res.add(sdf.format(cend.getTime()));

        }catch (Exception e){
            e.printStackTrace();

        }
        return res;

    }

    public int getDifferDays(String begin, String end){


        int res = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            res = (int)((sdf.parse(end).getTime()-sdf.parse(begin).getTime())/(1000*3600*24));

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

}
