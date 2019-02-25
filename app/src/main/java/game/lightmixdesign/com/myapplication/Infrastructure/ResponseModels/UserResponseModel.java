package game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UserResponseModel {
    public int id;

    public UUID guid;

    public boolean isActive;

    public String balance;

    public int age;

    @SerializedName("eyeColor")
    public ColorType eyeColor;

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

    public ArrayList<FriendResponseModel> friends;

    @SerializedName("favoriteFruit")
    public Figure favoriteFruit;

    public String gender;

    @JsonAdapter(Figure.Serializer.class)
    public enum Figure {
        apple("apple"),
        banana("banana"),
        strawberry("strawberry"),
        none("");

        private final String text;

        Figure(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        private static Figure getTypeMessageByText(String text) {
            for (Figure typeMessage : values())
                if (typeMessage.toString().equals(text))
                    return typeMessage;

            return none;
        }

        private static class Serializer implements JsonSerializer<Figure>, JsonDeserializer<Figure> {
            @Override
            public JsonElement serialize(Figure src, Type typeOfSrc, JsonSerializationContext context) {
                return context.serialize(src.text);
            }

            @Override
            public Figure deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                try {
                    return Figure.getTypeMessageByText(json.getAsString());
                } catch (JsonParseException e) {
                    return Figure.none;
                }
            }
        }
    }

    @JsonAdapter(ColorType.Serializer.class)
    public enum ColorType {
        brown("brown"),
        blue("blue"),
        green("green"),
        none("");

        private final String text;

        ColorType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        private static ColorType getTypeMessageByText(String text) {
            for (ColorType typeMessage : values())
                if (typeMessage.toString().equals(text))
                    return typeMessage;

            return none;
        }

        private static class Serializer implements JsonSerializer<ColorType>, JsonDeserializer<ColorType> {
            @Override
            public JsonElement serialize(ColorType src, Type typeOfSrc, JsonSerializationContext context) {
                return context.serialize(src.text);
            }

            @Override
            public ColorType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                try {
                    return ColorType.getTypeMessageByText(json.getAsString());
                } catch (JsonParseException e) {
                    return ColorType.none;
                }
            }
        }
    }
}
