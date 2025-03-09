package org.example;


import static org.example.Calculator.*;
import static org.example.Compare.*;


public class Main {
    public static void main(String[] args) {

        System.out.println(sum(10.5f, 20.1d));
        System.out.println(subtract(200L, 30.4));
        System.out.println(multiply(3, 90.3f));
        System.out.println(divide(90, 3000L));

        Integer[] arr1 = {1, 2, 3};
        Integer[] arr2 = {1, 2, 3};

        String[] arr3 = {"Вася", "Петя"};
        String[] arr4 = {"Маня", "Таня"};

        System.out.println("------------------");
        System.out.println("arr1 и arr2 одинаковые? " + Compare.compareArrays(arr1, arr2));
        System.out.println("arr3 и arr4 одинаковые? " + Compare.compareArrays(arr3, arr4));

        Pair<Integer, String> pair1 = new Pair<>(1000, "Kokos");
        System.out.println("First: " + pair1.getFirst());
        System.out.println("Second: " + pair1.getSecond());
        System.out.println(pair1);

        System.out.println(new Pair(10, "Framework"));

    }
}