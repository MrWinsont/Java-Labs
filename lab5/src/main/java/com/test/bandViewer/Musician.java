package com.test.bandViewer;

public class Musician {
    private String name;
    private String instrument = "";

    public Musician(String name, String instrument){
        this.name = name;
        this.instrument = instrument;
    }

    public String getName(){
        return name;
    }

    public void SetName(String name){
        this.name = name;
    }

    public String getInstrument(){
        return instrument;
    }

    public void SetInstrument(String instrument){
        this.instrument = instrument;
    }

}
