package com.munchymc.punishmentplugin.util;

public class Logger {
    public static void printTitle(String title){
        System.out.println(Header.center(title));
    }

    public static void log(String toLog){
        System.out.println(toLog);
    }
}
