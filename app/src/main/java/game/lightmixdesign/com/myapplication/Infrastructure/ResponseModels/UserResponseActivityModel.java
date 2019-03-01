package game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UserResponseActivityModel {
    public int id;

    public UUID guid;

    @SerializedName("isActive")
    public boolean isActive;

    public String balance;

    public Integer age;

    @SerializedName("eyeColor")
    public UserResponseModel.ColorType eyeColor;

    public String name;

    public String company;

    public String email;

    public String phone;

    public String address;

    public String about;

    public Date registered;

    public double latitude;

    public double longitude;

    public ArrayList<String> tags;

    public ArrayList<Integer> friends;

    @SerializedName("favoriteFruit")
    public UserResponseModel.Figure favoriteFruit;

    public String gender;


    public UserResponseActivityModel(int id, String guid, boolean isActive, String balance, int age, String eyeColor,
                                     String name, String company, String email, String phone, String address,
                                     String about, Date registered, double latitude, double longitude,
                                     ArrayList<Integer> friends, ArrayList<String> tags, String figure, String gender) {
        this.id = id;
        this.guid = UUID.fromString(guid);
        this.isActive = isActive;
        this.balance = balance;
        this.age = age;
        this.eyeColor = UserResponseModel.ColorType.valueOf(eyeColor);
        this.name = name;
        this.company = company;
        this.email = email;
        this.address = address;
        this.registered = registered;
        this.latitude = latitude;
        this.longitude = longitude;
        this.friends = friends;
        this.tags = tags;
        this.favoriteFruit = UserResponseModel.Figure.valueOf(figure);
        this.phone = phone;
        this.gender = gender;
        this.about = about;
    }
}
