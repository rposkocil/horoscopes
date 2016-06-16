package com.hi.transformer.horoscops;

import com.hi.transformer.horoscops.manager.Manager;
import com.hi.transformer.horoscops.zodiac.Horoscope;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> inputData = new ArrayList<String>();
        inputData.add("1988-07-30");
        inputData.add("1982-03-05");
        inputData.add("1989-12-24");

        Manager manager = new Manager(inputData);
        List<String> signs = manager.getSigns();
        List<Horoscope> horoscopes = manager.getHoroscopes();

        for(int i = 0; i < inputData.size(); i++) {
            System.out.println(inputData.get(i) + " : " + signs.get(i) + " : " + horoscopes.get(i).getHoroscope());
        }
    }
}
