package com.example.alarm;

import android.app.Application;
import android.util.Log;

public class Globals extends Application {

    private String level="";
    private Boolean ring = true;
    private Boolean started = false;
    private String sveglia = "no";
    private Boolean main_ring = false;
    public String get_level() {
        if (level.equals("")){
            return "EASY";
        }
        else {
            return level;
        }
    }

    public void set_level(String alvl) {
        level = alvl;
    }

    public boolean get_main_ring() {
        return main_ring;
    }

    public void set_main_ring(boolean aring) {
        main_ring = aring;
    }

    public boolean get_ring() {
        return ring;
    }

    public void set_ring(boolean aring) {
        ring = aring;
    }

    public String get_sveglia() {
        return sveglia;
    }

    public void set_sveglia(String asveglia) {
        Log.d("ssssMODIFICA_SVEGLIA",asveglia);
        sveglia = asveglia;
    }

    public boolean get_started() {
        return started;
    }

    public void set_started(boolean ast) {
        started = ast;
    }

}