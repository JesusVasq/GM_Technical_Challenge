package com.jesusvasquez.gmtechnical;

public class Commit {
    public String name;
    public String hash;
    public String message;

    public Commit(String name, String hash, String message){
        this.name = name;
        this.hash = hash;
        this.message = message;
    }

    @Override
    public String toString(){
        return name + " " + hash + " " + message;
    }
}
