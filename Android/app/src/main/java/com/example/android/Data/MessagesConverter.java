package com.example.android.Data;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class MessagesConverter {

    @TypeConverter
    public Messages storedStringToLanguages(String value) {
        if(value == null)
            return null;
        List<String> msgs = Arrays.asList(value.split("\\s*~\\s*")); //used ~ cause may be another ',' in the msg string
        return new Messages(msgs);
    }

    @TypeConverter
    public String languagesToStoredString(Messages cl) {
        if(cl == null)
            return null;
        String value = "";

        for (String lang :cl.getMsgs())
            value += lang + "~";

        return value;
    }

}
