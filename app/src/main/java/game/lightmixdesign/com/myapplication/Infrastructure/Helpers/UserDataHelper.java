package game.lightmixdesign.com.myapplication.Infrastructure.Helpers;

import com.gc.linq.LinQ;

import java.util.ArrayList;
import java.util.List;

import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.FriendResponseModel;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseActivityModel;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;

public class UserDataHelper {

    public static ArrayList<User> convertUserResponseToUsers(ArrayList<UserResponseModel> users) {
        ArrayList<User> result = new ArrayList<>();

        for (UserResponseModel user : users) {
            List<Object> friendsObj = new LinQ<FriendResponseModel>().FROM(user.friends).SELECT("id").exec();
            result.add(new User(user.id, user.guid, user.isActive, user.balance, user.age, user.eyeColor,
                    user.name, user.company, user.email, user.phone, user.address, user.about, user.registered,
                    user.longitude, user.latitude, mapIds(friendsObj), user.tags, user.favoriteFruit,
                    user.gender));
        }

        return result;
    }

    public static UserResponseActivityModel convertUserToUserResponse(User user) {
        ArrayList<String> tags = new ArrayList<>(user.tags);
        ArrayList<Integer> friends = new ArrayList<>(user.friends);

        return new UserResponseActivityModel(user.id, user.guid, user.isActive, user.balance, user.age, user.eyeColor,
                user.name, user.company, user.email, user.phone, user.address, user.about, user.registered,
                user.latitude, user.longitude, friends, tags, user.figure, user.gender);
    }

    private static ArrayList<Integer> mapIds(List<Object> list) {
        ArrayList<Integer> ids = new ArrayList<>();

        if (list != null && !list.isEmpty()) {
            for (Object object : list) {
                ids.add((Integer) object);
            }
        }

        return ids;
    }
}
