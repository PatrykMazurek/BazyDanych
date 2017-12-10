package com.example.patryyyk21.bazydanych;

import android.content.Context;

/**
 * Created by Patryyyk21 on 2017-12-06.
 */

public class Person {

    public int id;
    public String name;
    public String lastName;
    public String city;

    public Person(int pId, String pName, String pLastName, String pCity){
        this.id = pId;
        this.name = pName;
        this.lastName = pLastName;
        this.city = pCity;
    }

}
