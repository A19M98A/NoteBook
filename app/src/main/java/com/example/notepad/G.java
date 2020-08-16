package com.example.notepad;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Random;

public class G extends Application {
    public static Context context;

    public static LayoutInflater inflater;
    public static Activity currentActivity;

    public static ArrayList<Notes> nodes = new ArrayList<Notes>();

    public static Random rnd = new Random();

    public static DBHelper database;

    public static SharedPreferences settings;
    public static SharedPreferences.Editor editor;

    public static String UserName;
    public static String PassWord;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        inflater = (LayoutInflater) getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public static void UpdateNotes(){
        nodes.clear();
        database.getAllNotes();
//        ArrayAdapter adapter = new AdapterNode(nodes);
//        adapter.notifyDataSetChanged();
    }
    public static void getData(String text){
        nodes.clear();
        database.getData(text);
//        ArrayAdapter adapter = new AdapterNode(nodes);
//        adapter.notifyDataSetChanged();
    }
}
