package first_vsc_maven_test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public final class App {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String full = "█";
    public static final String high = "▓";
    public static final String medium = "▒";
    public static final String low = "░";

    private static ArrayList<ArrayList<Integer>> picture = new ArrayList<>();
    private static BufferedImage img;
    private static HashMap<Integer, String> colorHash = new HashMap<>();
    private static HashMap<Integer, Integer> colorGroupHash = new HashMap<>();
    private static ArrayList<Integer> colors = new ArrayList<>();
    private App() {
    }

    public static void loadRaster(String path) {
        BufferedImage imgBuff = null;
        try {
            imgBuff = ImageIO.read(new File(path));
        } catch (IOException e) {
        }
        img = resize(imgBuff, 80, 40);

        for (int y = 0; y < img.getHeight(); y++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int x = 0; x < img.getWidth(); x++) {
                tmp.add(0);
            }
            picture.add(tmp);
        }

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                picture.get(y).set(x, img.getRGB(x, y));
            }
        }

        fillColors();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //int tmp = findClosestColor(picture.get(y).get(x));
                int tmp = findClosestColorByHue(picture.get(y).get(x));

                /*float[] tmpArr = brightnessArray(tmp);
                tmp = returnColorInt(tmpArr[0], tmpArr[1], tmpArr[2]);*/

                picture.get(y).set(x, tmp);
            }
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static double distance(int a, int b) {
        int redA = (a >> 16) & 0xFF;
        int greenA = (a >> 8) & 0xFF;
        int blueA = a & 0xFF;
        int redB = (b >> 16) & 0xFF;
        int greenB = (b >> 8) & 0xFF;
        int blueB = b & 0xFF;
        return Math.sqrt(Math.pow(redA - redB, 2) + Math.pow(greenA - greenB, 2) + Math.pow(blueA - blueB, 2));
    }

    public static void fillColors() {
        fillColor(0); // black
        fillColor(16711680); // red
        fillColor(65280); // green
        fillColor(255); // blue
        fillColor(65535); // cyan
        fillColor(16711935); // purple
        fillColor(16776960); // yellow
        fillColor(16777215); // white
    }

    public static void fillColor(int colorInt) {
        if (colorInt == 16777215) {
            int full = 16777215; System.out.println(full); //System.out.println();
            colors.add(full); 
            colorHash.put(full, App.full);
            colorGroupHash.put(full, full);
        } else if (colorInt == 0) {
            int full = 0; System.out.println(full);
            int high = 4210752; System.out.println(high);
            int medium = 8421504; System.out.println(medium);
            int low = 12566463; System.out.println(low); //System.out.println();
            colors.add(full);
            colorHash.put(full, App.full);
            colorGroupHash.put(full, full);
            colors.add(high);
            colorHash.put(high, App.high);
            colorGroupHash.put(high, full);
            colors.add(medium);
            colorHash.put(medium, App.medium); 
            colorGroupHash.put(medium, full);
            colors.add(low);
            colorHash.put(low, App.low);
            colorGroupHash.put(low, full);
        } else {
            float[] tmpArr;
            tmpArr = brightnessArray(colorInt);
            int full = returnColorInt(tmpArr[0], tmpArr[1], tmpArr[2]); //System.out.println(full); //System.out.println("h " + tmpArr[0] + " s " + tmpArr[1] + " b " + tmpArr[2] + " " + full);
            int high = returnColorInt(tmpArr[0], tmpArr[3], tmpArr[2]); //System.out.println(high); //System.out.println("h " + tmpArr[0] + " s " + tmpArr[3] + " b " + tmpArr[2] + " " + high);
            int medium = returnColorInt(tmpArr[0], tmpArr[4], tmpArr[2]); //System.out.println(medium); //System.out.println("h " + tmpArr[0] + " s " + tmpArr[4] + " b " + tmpArr[2] + " " + medium);
            int low = returnColorInt(tmpArr[0], tmpArr[5], tmpArr[2]); //System.out.println(low); System.out.println(); //System.out.println("h " + tmpArr[0] + " s " + tmpArr[5] + " b " + tmpArr[2] + " " + low); System.out.println();
            colors.add(full);
            colorHash.put(full, App.full);
            colorGroupHash.put(full, full);
            colors.add(high);
            colorHash.put(high, App.high);
            colorGroupHash.put(high, full);
            colors.add(medium);
            colorHash.put(medium, App.medium); 
            colorGroupHash.put(medium, full);
            colors.add(low);
            colorHash.put(low, App.low);
            colorGroupHash.put(low, full); 
        }        
    }

    public static int returnColorInt(float h, float s, float b) {
        int rgb = (Color.HSBtoRGB((float) h + (float) 0.5, (float) b, (float) s) + 1)*(-1);
        if (s < 0) {
           rgb = (Color.HSBtoRGB((float) h + (float) 0.5, (float) s, (float) (1 + b)) + 1)*(-1);
        } 

        return rgb;
    }

    public static float[] brightnessArray(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        // Convert RGB to HSB
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];

        float[] tmp = new float[6];
        tmp[0] = hue;
        tmp[1] = saturation;
        tmp[2] = brightness;
        tmp[3] = (float) (saturation - 0.25);
        tmp[4] = (float) (saturation - 0.50);
        tmp[5] = (float) (saturation - 0.75);

        return tmp;
    }

    public static int findClosestColorByHue(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        // Convert RGB to HSB
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];

        float nearest = hue;
        int rgb = color;

        //System.out.println("h " + hue + "s " + saturation + "b " + brightness);

        if ((saturation <= 0.1) && (brightness >= 0.9)) { //System.out.println("saturation <= 0.1) && (brightness >= 0.9");
            nearest = 0; saturation = 0; brightness = 0;
            rgb = (Color.HSBtoRGB((float) nearest + (float) 0.5, (float) brightness, (float) saturation) + 1)*(-1);
        } else if ((saturation <= 0.1) && (brightness <= 0.1)){ //System.out.println("saturation <= 0.1) && (brightness <= 0.1");
            nearest = 0; saturation = 1; brightness = 0;
            rgb = (Color.HSBtoRGB((float) nearest + (float) 0.5, (float) brightness, (float) saturation) + 1)*(-1); 
        } else {  //System.out.println("color");
            rgb = findClosestColor(color);
        }        

        return rgb;
    }

    public static int findClosestColor(int color) {
        int test = color;
        int nearest = color;
        double distance = Double.MAX_VALUE;

        for (int _color : colors) {
            if (distance(test, _color) < distance) {
                distance = distance(test, _color);
                nearest = _color;
            }
        }

        return nearest;
    }

    public static void fillScreen() { //System.out.println(colorGroupHash);
        for (int y = 0; y < picture.size(); y++) {
            for (int x = 0; x < picture.get(0).size(); x++) { 
                if (colorGroupHash.get(picture.get(y).get(x)) == 0) {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_BLACK + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                } else if (colorGroupHash.get(picture.get(y).get(x)) == 16711680) {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_RED + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                } else if (colorGroupHash.get(picture.get(y).get(x)) == 65280) {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_GREEN + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                } else if (colorGroupHash.get(picture.get(y).get(x)) == 255) {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_BLUE + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                } else if (colorGroupHash.get(picture.get(y).get(x)) == 65535) {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_CYAN + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                } else if (colorGroupHash.get(picture.get(y).get(x)) == 16711935) {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_PURPLE + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                } else if (colorGroupHash.get(picture.get(y).get(x)) == 16776960) {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_YELLOW + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                } else {
                    System.out.print(
                            ANSI_WHITE_BACKGROUND + ANSI_WHITE + colorHash.get(picture.get(y).get(x)) + ANSI_RESET);
                }

            }
        } //System.out.print(picture);
    }

    /**
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        loadRaster(args[0]);
        fillScreen();
    }
}
