package com.hi.transformer.horoscops.manager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hi.transformer.horoscops.zodiac.Horoscope;
import com.hi.transformer.horoscops.zodiac.Zodiac;

public class Manager {

    private List<Date> inputDates;
    private List<String> zodiacList;
    private List<Horoscope> horoscopes;

    public Manager(List<String> inputDates) {
        this.inputDates = convertInput(inputDates);
        this.zodiacList = prepareZodiac();
        this.horoscopes = prepareHoroscopes();
    }

    public List<String> getSigns() {
        return zodiacList;
    }

    public List<Horoscope> getHoroscopes() {
        return horoscopes;
    }

    private List<Horoscope> prepareHoroscopes() {
        RestClient restClient = new RestClient();
        List<Horoscope> horos = new ArrayList<Horoscope>();
        for(int i = 0; i < zodiacList.size(); i++) {
            String sign = zodiacList.get(i);
            if(sign != null && sign != null) {
                Horoscope horoscope = null;
                try {
                    horoscope = restClient.getHoroscope(sign);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(horoscope != null) {
                    horos.add(horoscope);
                } else {
                    horos.add(new Horoscope());
                }
            } else {
                horos.add(new Horoscope());
            }
        }
        return horos;
    }

    private List<String> prepareZodiac() {
        Zodiac zodiac = new Zodiac();
        List<String> signs = new ArrayList<String>();
        for(Date date : inputDates) {
            String sign = zodiac.getSign(date);
            if(sign != null) {
                signs.add(sign);
            } else {
                signs.add("");
            }
        }
        return signs;
    }

    private List<Date> convertInput(List<String> inputs) {
        List<Date> inputDates = new ArrayList<Date>();
        for(String in : inputs) {
            Date date = convertToDate(in);
            if(date != null) {
                inputDates.add(date);
            }
        }
        return inputDates;
    }

    private Date convertToDate(String in) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(in);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
