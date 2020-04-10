package george.model;

import java.util.regex.Pattern;

public class Color {
    private static final short MIN_VALUE = 0;
    private static final short MAX_VALUE = 255;

    private final static char[] VALUES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };
    /*
    * Red value(0-255)
    */
    private int red;
    /*
     * Blue value(0-255)
     */
    private int blue;
    /*
     * Green value(0-255)
     */
    private int green;
    /*
    * Hex value
     */
    private char[] hexValue;

    public Color(int red, int green, int blue){
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setHexValue(red, green, blue);
    }
    public Color(String hexValue){
        setHexValue(hexValue);
        setRedGreenBlue(hexValue);
    }

    public String getHexValue() {
        return "#" + new String(hexValue);
    }

    public void setHexValue(String hexValue) {
        if(hexValue!=null&&isCorrectHexValue(hexValue)){
            char[] charHexValue = hexValue.toCharArray();
            this.hexValue = charHexValue;
        } else{
            throw new IllegalArgumentException("Hex color is incorrect");
        }

    }
    //Test if number between limits
    private boolean isCorrectColorValue(int colorValue) {
        return MIN_VALUE <= colorValue && colorValue <= MAX_VALUE;
    }
    //Test if hexValue is correct
    private boolean isCorrectHexValue(String hexValue) {
        return Pattern.matches("([0-9A-Fa-f]{6})$", hexValue);
    }

    public int getRed() {
        return red;
    }
    private void setHexValue(){
        this.setHexValue(this.red, this.green, this.blue);
    }
    public void setRed(int red) {
        if(isCorrectColorValue(red)){
            this.red = red;
            this.setHexValue();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        if(isCorrectColorValue(blue)){
            this.blue = blue;
            this.setHexValue();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        if(isCorrectColorValue(green)){
            this.green = green;
            this.setHexValue();
        } else {
            throw new IllegalArgumentException();
        }
    }
    public void setHexValue(int red, int green, int blue){
        char[] charHexValue = new char[6];
        charHexValue[0] = VALUES[red / 16];
        charHexValue[1] = VALUES[red % 16];
        charHexValue[2] = VALUES[green / 16];
        charHexValue[3] = VALUES[green % 16];
        charHexValue[4] = VALUES[blue / 16];
        charHexValue[5] = VALUES[blue % 16];
        this.hexValue = charHexValue;
    }
    public void setRedGreenBlue(String hexValue){
        int red = Integer.parseInt(hexValue.substring(0,2),16);
        if(isCorrectColorValue(red)){
            this.red = red;
        }
        int green = Integer.parseInt(hexValue.substring(2,4),16);
        if(isCorrectColorValue(green)){
            this.green = green;
        }
        int blue = Integer.parseInt(hexValue.substring(4),16);
        if(isCorrectColorValue(blue)){
            this.blue = blue;
        }
    }
    @Override
    public String toString() {
        return "[value=" + getHexValue() + ", r=" + getRed() + ", g=" + getGreen() + ", b=" + getBlue() + "]";
    }

}
