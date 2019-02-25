package game.lightmixdesign.com.myapplication.Infrastructure.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private int id;

    private UUID guid;

    private boolean isActive;

    private String balance;

    private int age;

    public UserResponseModel.ColorType eyeColor;

    private String name;

    private String company;

    private String email;

    private String phone;

    private String address;

    private String about;

    private Date registered;

    private double latitude;

    private double longitude;

    @Ignore
    private ArrayList<Friend> friends;

    @Ignore
    private ArrayList<Friend> tags;
}
