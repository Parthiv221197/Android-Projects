package com.example.caps;

import java.util.List;
import java.util.Map;
import java.util.Random;

import ca.roumani.i2c.Country;
import ca.roumani.i2c.CountryDB;

public class Game {
    private CountryDB db;
    private static int n = 241;
    public String city;
    public String country;
    public int score;
    public int questionNumber;
    public String nowquestion;
    public String anstype;
    public int makesound;

    public Game()
    {
        this.db = new CountryDB();
    }

    public static String QuestionSelector(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(2);
        if(randomInt == 0){
            return "country";
        }
        else{
            return "capital";
        }
    }

    public String qa()
    {
        List<String> capitals = db.getCapitals();
        Map<String, Country> data = db.getData();
        Random random = new Random();
        int index = (random.nextInt(n));
        this.city = capitals.get(index);
        Country ref = data.get(city);
        this.country  = ref.getName();

        String select = QuestionSelector();
        if(select.equals("country")){
            this.nowquestion  = "what is the capital of "+ country+" ?";
            this.anstype = "city";
            return this.nowquestion;
        }
        else{
            this.nowquestion = city +" is the capital of?";
            this.anstype = "country";

            return this.nowquestion;
        }
    }

    public void TorF(String playerans){
        if (playerans.equalsIgnoreCase(city)||playerans.equalsIgnoreCase(country)){

            this.score++;
            this.questionNumber++;
            this.makesound =1;
        }
        else{
            this.questionNumber++;
            this.makesound =0;
        }
    }

    public static void main(String[] args)
    {
        Game testGame = new Game();
        System.out.println(testGame.qa());

    }
}
