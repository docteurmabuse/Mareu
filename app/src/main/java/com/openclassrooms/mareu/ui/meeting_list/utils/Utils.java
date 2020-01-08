package com.openclassrooms.mareu.ui.meeting_list.utils;

import android.graphics.Color;

import com.openclassrooms.mareu.model.Meeting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Utils {
    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static boolean isValideDate(String date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        try {
            Date convertedDate = formatter.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNotValidTime(List<Meeting> meetings, String mDateString, String placeSelected) throws ParseException {
        boolean validTime = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);
        for (Meeting meeting : meetings) {
            Date date2 = sdf.parse(mDateString);
            Date date1 = meeting.getmDate();
            String mPlace = meeting.getmPlace();
            assert date2 != null;
            long differenceinMn = Math.abs((date2.getTime() - date1.getTime()) / 60000);
            if (mPlace.equals(placeSelected)) {
                if (differenceinMn >= 45) {
                    validTime = true;
                } else {
                    validTime = false;
                    break;
                }
            } else {
                validTime = true;
            }
        }
        return !validTime;
    }
}
