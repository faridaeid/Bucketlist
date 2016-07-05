package edu.aucegypt.bucketlist;

/**
 * Created by Farida on 04-Jul-16.
 */
public class User {

    private String fullName;
    private String image;
    private boolean privacy;
    private String ID;


    public User(){};

    public User(String fullName, String ID)
    {
        this.fullName = fullName;
        privacy = false;
        this.ID = ID;
    }

    public String getFullName() {return fullName;}

    public boolean getPrivacy() {return privacy;}




}
