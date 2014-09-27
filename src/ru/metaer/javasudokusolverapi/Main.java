package ru.metaer.javasudokusolverapi;

public class Main {
    public static void main(String[] args) {
        String inputString = "";
        if (args.length != 0) {
            inputString = args[0];
        }
        System.out.println(SudokuApi.getSolution(inputString));
    }
}
