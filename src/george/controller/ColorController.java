package george.controller;

import george.model.Color;
import george.model.Palette;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorController implements Initializable {
    private Color color;
    private Palette palette;

    @FXML
    private Slider sliderRed;

    @FXML
    private Slider sliderGreen;

    @FXML
    private Slider sliderBlue;

    @FXML
    private TextField textFieldRed;

    @FXML
    private TextField textFieldBlue;

    @FXML
    private TextField textFieldGreen;

    @FXML
    private TextField hexColor;

    @FXML
    private Pane paneColor;

    @FXML
    private Pane paneColorPalette1;

    @FXML
    private Pane paneColorPalette2;

    @FXML
    private Pane paneColorPalette3;

    @FXML
    private Pane paneColorPalette4;

    @FXML
    private Pane paneColorPalette5;

    @FXML
    private Canvas myCanvas;
    private GraphicsContext gc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        color = new Color(105, 84, 32);
        palette = new Palette(color);
        updateUI();
        sliderRed.valueProperty()
                .addListener((observable, oldValue, newValue) -> setColor(COLOR.RED, newValue.intValue()));
        textFieldRed.textProperty()
                .addListener((observable, oldValue, newValue) -> setColor(COLOR.RED, Integer.valueOf(newValue)));
        sliderGreen.valueProperty()
                .addListener((observable, oldValue, newValue) -> setColor(COLOR.GREEN, newValue.intValue()));
        textFieldGreen.textProperty()
                .addListener((observable, oldValue, newValue) -> setColor(COLOR.GREEN, Integer.valueOf(newValue)));
        sliderBlue.valueProperty()
                .addListener((observable, oldValue, newValue) -> setColor(COLOR.BLUE, newValue.intValue()));
        textFieldBlue.textProperty()
                .addListener((observable, oldValue, newValue) -> setColor(COLOR.BLUE, Integer.valueOf(newValue)));
        hexColor.setOnKeyPressed(keyEvent -> handleEvent(keyEvent));
        //myCanvas.setOnMousePressed( e -> mousePressed(e) );
        //myCanvas.setOnMouseDragged( e -> mouseDragged(e) );
        //myCanvas.setOnMouseReleased( e -> mouseReleased(e) );
    }

    // when pressed enter checks hexValue and updates UI
    private void handleEvent(KeyEvent keyEvent) {
        if(keyEvent.getCode().getCode()==10){
        String hexValue = hexColor.getText();
        if ( hexValue.matches("^#([0-9A-Fa-f]{6})$")){

            color.setHexValue(hexValue.substring(1));
            color.setRedGreenBlue(hexValue.substring(1));
            updateUI();
        }}
    }

    private void setColor(COLOR rgb, int value) {
        switch (rgb) {
            case RED:
                color.setRed(value);
                break;
            case GREEN:
                color.setGreen(value);
                break;
            case BLUE:
                color.setBlue(value);
                break;
        }
        color.setHexValue(color.getRed(), color.getGreen(), color.getBlue());

        updateUI();
    }

    private enum COLOR {
        RED, GREEN, BLUE;
    }

    private void updateUI() {
        palette.updatePalette();
        sliderRed.setValue(color.getRed());
        sliderGreen.setValue(color.getGreen());
        sliderBlue.setValue(color.getBlue());
        hexColor.setText(color.getHexValue());
        textFieldRed.setText(String.valueOf(color.getRed()));
        textFieldGreen.setText(String.valueOf(color.getGreen()));
        textFieldBlue.setText(String.valueOf(color.getBlue()));
        paneColor.setStyle("-fx-background-color: " + color.getHexValue() + ";");
        paneColorPalette1.setStyle("-fx-background-color: " + palette.getPalette().get(0).getHexValue() + ";");
        paneColorPalette2.setStyle("-fx-background-color: " + palette.getPalette().get(1).getHexValue() + ";");
        paneColorPalette3.setStyle("-fx-background-color: " + palette.getPalette().get(2).getHexValue() + ";");
        paneColorPalette4.setStyle("-fx-background-color: " + palette.getPalette().get(3).getHexValue() + ";");
        paneColorPalette5.setStyle("-fx-background-color: " + palette.getPalette().get(4).getHexValue() + ";");
    }
    public void drawShapes() {
        gc = myCanvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf(color.getHexValue()));
        gc.setStroke(Paint.valueOf(color.getHexValue()));
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);

    }

}
