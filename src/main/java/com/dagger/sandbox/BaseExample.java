package com.dagger.sandbox;

public abstract class BaseExample implements Runnable {

    void printSelf() {
        System.out.println("\n" + getClass().getSimpleName());
    }

    void printDivider() {
        System.out.println("--");
    }

    void printLocal(Object object) {
        printLocal(object, null);
    }

    void printLocal(Object object, String info) {
        print(object, "Local", info);
    }

    void printLocal(Class clazz, String message) {
        print(clazz, "Local", null, message);
    }

    void printField(Object object) {
        printField(object, null);
    }

    void printField(Object object, String info) {
        print(object, "Field", info);
    }

    void printField(Class clazz, String message) {
        print(clazz, "Field", null, message);
    }

    void print(Object object, String type, String info) {
        print(object.getClass(), type, info, getHashMessage(object));
    }

    void print(Class clazz, String type, String info, String message) {
        System.out.println(type
                + " " + clazz.getSimpleName()
                + (info == null ? " " : " (" + info + ") ")
                + message
        );
    }

    String getHashMessage(Object object) {
        return "hash is " + Integer.toHexString(object.hashCode());
    }
}
