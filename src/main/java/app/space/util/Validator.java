package main.java.app.space.util;

import java.util.Arrays;

public class Validator {
    public static final int MIN_SPACE_PRICE = 1;
    public static final int MAX_SPACE_PRICE = 100000;
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 30;

    public static String onName(String name) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            return "(!) Name must be 1—30 symbols long";
        }

        return "";
    }

    public static String onPrice(String price) {
        if (!price.matches("[1-9][0-9]*")) {
            return "(!) Price must be a non-zero integer number";
        } else {
            try {
                int parsedPrice = Integer.parseInt(price);
                if (parsedPrice < MIN_SPACE_PRICE || parsedPrice > MAX_SPACE_PRICE) {
                    return "(!) Price must be in range ["
                            + MIN_SPACE_PRICE + ", "
                            + MAX_SPACE_PRICE + "].";
                }
            } catch (NumberFormatException e) {
                return "(!) Invalid price.";
            }
        }

        return "";
    }

    public static String onSpaceType(String type) {
        if (!Arrays.asList("open", "private", "room").contains(type.toLowerCase())) {
            return "(!) Invalid space type. Available space types: Open, Private, Room.";
        }

        return "";
    }

    public static String onId(String id) {
        // id.length() > 8 to avoid Integer of out range
        if (!id.matches("[0-9]+") || id.length() > 8) {
            return "(!) Space ID must consist of only numbers and must contain less 8 numbers";
        }

        return "";
    }

    public static String onDateNumber(String date) {
        if(!date.matches("([1-9]|[1-2][0-9]|3[0-1])")) return "(!) Invalid Date";

        return "";
    }

    public static String onHourNumber(String date) {
        if(!date.matches("([0-9]|1[0-9]|2[0-3])")) return "(!) Invalid Hour";

        return "";
    }

    public static String onStartEndHours(String startHour, String endHour) {
        if (!Validator.onHourNumber(startHour).isBlank() || !Validator.onHourNumber(endHour).isBlank())
            return "(!) Invalid Start or/and End Hour";
        if (Integer.parseInt(startHour) >= Integer.parseInt(endHour))
            return "(!) End Hour must be bigger than Start Hour";

        return "";
    }

}
