/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.coffeeshopstudio.meksheets.models.Mek;

public class FileOperations {
    public static void writeFile(Context context, Mek obj) {
        Gson gson = new Gson();

        FileOutputStream outputStream;

        if (obj.getFileName().equals(""))
            obj.setFileName(obj.getName() + Calendar.getInstance().getTimeInMillis() + ".json");

        String input = gson.toJson(obj);
        try {
            outputStream = context.openFileOutput(obj.getFileName(), Context.MODE_PRIVATE);
            outputStream.write(input.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Mek readFile(Context context, String fileName) {
        FileInputStream fis;
        try {
            fis = context.openFileInput(fileName);

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            String json = sb.toString();
            Gson gson = new Gson();
            return gson.fromJson(json, Mek.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteFile(Context context, String fileName) {
        context.deleteFile(fileName);
    }

    public static List<Mek> getJsonFiles(Context context) {
        List<Mek> meks = new ArrayList<>();

        File files = context.getFilesDir();
        for (File file : files.listFiles()) {
            if (file.getName().endsWith(".json")) {
                Mek mek = readFile(context, file.getName());
                if (mek != null)
                    meks.add(mek);
            }
        }

        return meks;
    }
}
