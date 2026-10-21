package com.cegepst;

public class Main {
    public static void main(String[] args){
        double PrixInitial = 5.99;
        double TPS = 0.05;
        double TVQ = 0.09975;
        double PrixTotal = 6.887;
        String Prix = "Prix Initial = 5.99";
        String Taxe = "TPS = ";
        String Taxe2 = "TVQ = ";
        String Prix1 = "Prix Total = ";
        System.out.println(Prix);
        System.out.println(Taxe+PrixInitial*TPS);
        System.out.println(Taxe2+PrixInitial*TVQ);
        System.out.println(Prix1+PrixInitial+(PrixInitial*TPS+PrixInitial*TVQ));
    }
}
