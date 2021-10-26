package app;

import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;


public class FirstScreen extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Make a Bingo");
        VBox root = new VBox();
        int size=700;
        Scene scene=new Scene(root, size, size+50);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        GridPane grid = new GridPane();
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++) {
                StackPane square = new StackPane();

                square.getStyleClass().add("border");
                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                square.getChildren().add(textArea);
                grid.add(square,j,i);
            }
        }
        HBox bottomBar = new HBox();
        Button changeColor = new Button("Save to File");
        changeColor.setMinSize(size+50,50);
        changeColor.setOnAction(e -> {
                screenshotToPNG(grid,primaryStage);
        });
        bottomBar.getChildren().add(changeColor);
        root.getChildren().addAll(grid,bottomBar);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static BufferedImage screenshotToPNG(Node node,Stage stage) {
        SnapshotParameters param = new SnapshotParameters();
        param.setDepthBuffer(true);
        WritableImage snapshot = node.snapshot(param, null);
        BufferedImage img = SwingFXUtils.fromFXImage(snapshot, null);
        ImageView imageView = new ImageView();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.png"));

            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot,
                            null), "png", file);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        //the final image sent to the PDJpeg
        return img;
    }
}