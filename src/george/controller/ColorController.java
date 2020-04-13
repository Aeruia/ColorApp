package george.controller;

import application.Main;
import george.model.Color;
import george.model.Palette;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColorController implements Initializable {
    private Color color;
    private Palette palette;
    private GraphicsContext gc;
    private Stage stage;

    @FXML
    private Slider sliderRed;

    @FXML
    private Slider sliderGreen;

    @FXML
    private Slider sliderBlue;

    @FXML
    private Slider sliderWidth;

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

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        color = new Color(214, 193, 193);
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
        buttonClear.setOnAction(actionEvent -> {
            this.refresh();
        });
        buttonSave.setOnAction(actionEvent -> {
            this.saveImage();
        });
    }
    private void refresh() {
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
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

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
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
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
        myCanvas.setOnMousePressed(mouseEvent -> {
            initDraw();
            gc.beginPath();
            gc.moveTo(mouseEvent.getX(), mouseEvent.getY());
            gc.stroke();
        });
        myCanvas.setOnMouseDragged(mouseEvent -> {
            initDraw();
            gc.lineTo(mouseEvent.getX(), mouseEvent.getY());
            gc.stroke();
        });
    }
    private void initDraw(){
        gc.setFill(Paint.valueOf(palette.getPalette().get(0).getHexValue()));
        gc.setStroke(Paint.valueOf(color.getHexValue()));
        gc.setLineWidth(sliderWidth.getValue());
    }
    private void saveImage(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(this.stage);

        if(file != null){
            try {
                WritableImage writableImage = new WritableImage((int)gc.getCanvas().getWidth(), (int)gc.getCanvas().getHeight());
                myCanvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
