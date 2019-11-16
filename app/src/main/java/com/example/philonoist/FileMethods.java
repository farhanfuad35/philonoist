package com.example.philonoist;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

public class FileMethods {

    private static final String userData = "userInfo.txt";

    public static void writes(Context context, String email){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FileMethods.getUserData(), MODE_PRIVATE);
            fileOutputStream.write(email.getBytes());

            //Toast.makeText(this,    "saved "+email +" to " + getFilesDir() +"/" +fileName, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  static String load(Context context){
        FileInputStream fileInputStream = null;
        String email ="";

        try {
            fileInputStream = context.openFileInput (userData);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((email = bufferedReader.readLine()) != null){
                stringBuilder.append(email);
            }
            email = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(email);
        return email;
    }

    public static String getUserData(){
        return userData;
    }

    public static String[] processSubjectString(String subjectString)
    {


        String[] subjects = subjectString.split("\\|");

        Log.i("subjects", subjectString);

        for(String s : subjects)
            Log.i("subjects", s);

        return subjects;
    }
}
