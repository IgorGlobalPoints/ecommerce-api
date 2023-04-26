package ecommerce.utils;

import com.mongodb.client.result.UpdateResult;

public class UpdateVerify {
    public static <T> boolean wasUpdated(UpdateResult result) {
        return result.getModifiedCount() > 0;
    }

    public static <T> boolean wasSaved(UpdateResult result) {
        return result.getModifiedCount() > 0;
    }
}
