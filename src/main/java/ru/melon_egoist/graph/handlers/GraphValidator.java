package ru.melon_egoist.graph.handlers;

public class GraphValidator {
    public static boolean validateX(String x) {
        try {
            int intX = Integer.parseInt(x);

            return (intX >= -3) && (intX <= 3);
        } catch (Exception ex) {
            System.out.println("Non-number coordinate X was provided" + ex.getMessage());

            return false;
        }
    }

    public static boolean validateY(String y) {
        try {
            int intY = Integer.parseInt(y);

            return (intY >= -2) && (intY <= 5);
        } catch (Exception ex) {
            System.out.println("Non-number coordinate Y was provided" + ex.getMessage());

            return false;
        }
    }

    public static boolean validateR(String r) {
        try {
            int intR = Integer.parseInt(r);

            return (intR >= 1) && (intR <= 5);
        } catch (Exception ex) {
            System.out.println("Non-number coordinate R was provided" + ex.getMessage());

            return false;
        }
    }
}