package ru.melon_egoist.graph.handlers;

public class GraphHandler {
    private final String x;
    private final String y;
    private final String r;


    public GraphHandler(String x, String y, String r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public String inArea() {
        boolean isXValid = GraphValidator.validateX(x);
        boolean isYValid = GraphValidator.validateY(y);
        boolean isRValid = GraphValidator.validateR(r);

        if (isXValid && isYValid && isRValid) {
             int intX = Integer.parseInt(x);
             int intY = Integer.parseInt(y);
             int intR = Integer.parseInt(r);

            boolean isRectChecked = ((intX <= 0) && (intX >= -intR) && (intY <= 0) && (intY >= -intR/2f));
            boolean isTriangleChecked = ((intX >= 0) && (intX <= intR) && (intY <= 0) && (intY >= -intR/2f) && (intY >= (1/2f*intX - intR/2f)));
            boolean isSectorChecked = ((intX >= 0) && (intX <= intR) && (intY >= 0) && (intY <= intR) && ((intX*intX + intY*intY) <= intR*intR));

            return (isRectChecked || isTriangleChecked || isSectorChecked)? "in Area!": "not in Area(";
        }

        String errorMessage = "Error!";
        if (!isXValid) errorMessage += " |Wrong x| ";
        if (!isYValid) errorMessage += " |Wrong y| ";
        if (!isRValid) errorMessage += " |Wrong r| ";

        return errorMessage;

    }
}
