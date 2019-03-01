package game.lightmixdesign.com.myapplication.Infrastructure.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    public Integer id;

    public String guid;

    public boolean isActive;

    public String balance;

    public Integer age;

    public String eyeColor;

    public String name;

    public String company;

    public String email;

    public String phone;

    public String address;

    public String about;

    public Date registered;

    public double latitude;

    public double longitude;

    public RealmList<Integer> friends;

    public RealmList<String> tags;

    public String figure;

    public String gender;

    public User() {

    }

    public User(Integer id, UUID guid, boolean isActive, String balance, Integer age,
                UserResponseModel.ColorType eyeColor, String name, String company, String email,
                String phone, String address, String about, Date registered, double latitude,
                double longitude, ArrayList<Integer> friends, ArrayList<String> tags,
                UserResponseModel.Figure figure, String gender) {

        this.id = id;
        this.guid = guid.toString();
        this.isActive = isActive;
        this.balance = balance;
        this.age = age;
        this.eyeColor = eyeColor.name();
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.about = about;
        this.registered = registered;
        this.latitude = latitude;
        this.longitude = longitude;
        this.friends = new RealmList<>(friends.toArray(new Integer[0]));
        this.tags = new RealmList<>(tags.toArray(new String[0]));
        this.figure = figure.name();
        this.gender = gender;
    }
}