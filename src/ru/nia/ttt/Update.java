package ru.nia.ttt;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Update {
    private String uuid;
    private String user;
    private String message;

    private Calendar startedAt;
    private Calendar finishedAt;
    private Double hours;

    private static final String dateFormatString = "yyyy-MM-dd'T'HH:mm:ssZ"; // 2011-01-29T00:02:08+06:00
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString, Locale.US);

    public static Calendar parseDate(String string) throws ParseException {
        if("null".equals(string)) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(string));
        return cal;
    }

    public static Double parseDouble(String string) throws ParseException {
        if("null".equals(string)) {
            return null;
        }

        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            throw new ParseException("Unparseable double: " + string, 0);
        }
    }

    public Update(JSONObject jsonObject) throws JSONException, ParseException {
        uuid = jsonObject.getString("uuid");
        user = jsonObject.getJSONObject("user").getString("nickname");
        message = jsonObject.getString("human_message");

        startedAt = parseDate(jsonObject.getString("started_at"));
        finishedAt = parseDate(jsonObject.getString("finished_at"));

        hours = parseDouble(jsonObject.getString("hours"));
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Calendar getStartedAt() {
        return startedAt;
    }

    public Calendar getFinishedAt() {
        return finishedAt;
    }

    public Double getHours() {
        return hours;
    }
}
