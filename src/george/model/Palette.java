package george.model;

import java.util.ArrayList;
import java.util.Random;

public class Palette {
    private final Color color;
    private final ArrayList<Color> palette;

    public Palette(Color color) {

        this.color = color;

        palette = new ArrayList<>(5);
        palette.add(new Color(0, 0, 0));
        palette.add(new Color(0, 0, 0));
        palette.add(new Color(0, 0, 0));
        palette.add(new Color(0, 0, 0));
        palette.add(new Color(0, 0, 0));

        generatePalette();
    }

    private void generatePalette() {

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        Random random = new Random();

        for (int i = 0; i < palette.size(); i++) {

            Color color = palette.get(i);
            color.setRed((random.nextInt(256) + red) / 2);
            color.setGreen((random.nextInt(256) + green) / 2);
            color.setBlue((random.nextInt(256) + blue) / 2);
        }
    }

    public ArrayList<Color> getPalette() {
        return palette;
    }

    public void updatePalette() {
        generatePalette();
    }

}
