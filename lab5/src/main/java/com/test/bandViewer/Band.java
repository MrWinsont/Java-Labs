package com.test.bandViewer;

public class Band{
    private String name;
    private String country;
    private int year;
    private String info = "";

    public Band(String name, String country, int year, String info){
        this.name = name;
        this.country = country;
        this.year = year;
        this.info = info;
    }

    public String getName(){
        return name; 
    }

    public void SetName(String name){
        this.name = name;
    }
    
    public String getCountry(){
        return country; 
    }

    public void SetCountry(String country){
        this.country = country;
    }
    
    public int getYear(){
        return year; 
    }

    public void SetYear(Integer year){
        this.year = year;
    }
    
    public String getInfo(){
        return info; 
    }

    public void SetInfo(String info){
        this.info = info;
    }
}
