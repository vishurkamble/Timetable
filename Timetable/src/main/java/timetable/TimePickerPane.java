package timetable;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import timetable.Datatypes.SimpleTime;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;
import static timetable.GUI.FOCUS_ANIMATION_OFFSET_FACTOR;

/**
 *
 * @author Tobias
 */
public class TimePickerPane extends SomePane {

    AnchorPane backgroundPane;
    AnchorPane hourPane;
    JFXButton hourButton;
    Line hourPreviewLine;
    Circle hourPreviewCircle;
    Circle hourPreviewCircleHole;
    Label[] hourLabels;
    Pane hourPaneOverlay;

    AnchorPane minutePane;
    JFXButton minuteButton;
    Line minutePreviewLine;
    Circle minutePreviewCircle;
    Circle minutePreviewCircleHole;
    Label[] minuteLabels;
    Pane minutePaneOverlay;

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    EventHandler<Event> onShow;
    Button showEvent = new Button();

    EventHandler<Event> onHide;
    Button hideEvent = new Button();

    public TimePickerPane(Pane parent) {
        super(parent);

        setWidthFactor(3);
        setHeightFactor(1.05);

        SlideIn = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideIn.setFromY(ANIMATION_DISTANCE);
        SlideIn.setToY(0);

        FadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        SlideOut = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideOut.setToY(ANIMATION_DISTANCE);

        FadeOut = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeOut.setToValue(0);

        show = new ParallelTransition(getPane());
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(getPane());
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);

        backgroundPane = new AnchorPane();
        backgroundPane.setPrefSize(500, 500);
        backgroundPane.getStyleClass().add("topRoundedPane");

        //hourPane
        hourPane = new AnchorPane();
        hourPane.getStyleClass().add("notRoundedPane");

        hourButton = new JFXButton("hours");
        hourButton.getStyleClass().add("roundedTopLeftButton");
        hourButton.setMinSize(0, 0);
        hourButton.setPrefSize(500, 150);
        hourButton.setOnAction(event -> {
            hourPane.toFront();
        });

        hourPreviewLine = new Line();
        hourPreviewLine.setStroke(Color.web(GUI.primaryColor));
        hourPreviewLine.setStrokeLineCap(StrokeLineCap.ROUND);

        hourPreviewCircle = new Circle();
        hourPreviewCircle.setFill(Color.web(GUI.primaryColor));

        hourPreviewCircleHole = new Circle();
        hourPreviewCircleHole.setFill(Color.web(GUI.backgroundColor));
        hourPreviewCircleHole.setVisible(false);

        hourLabels = new Label[24];
        for (int i = 0; i < hourLabels.length; i++) {
            hourLabels[i] = new Label(String.format("%02d", i));
        }

        hourPaneOverlay = new Pane();
        hourPane.getChildren().addAll(hourPreviewLine, hourPreviewCircle, hourPreviewCircleHole);
        hourPane.getChildren().addAll(hourLabels);
        hourPane.getChildren().add(hourPaneOverlay);
        hourPane.setTopAnchor(hourPaneOverlay, 0d);
        hourPane.setRightAnchor(hourPaneOverlay, 0d);
        hourPane.setBottomAnchor(hourPaneOverlay, 0d);
        hourPane.setLeftAnchor(hourPaneOverlay, 0d);

        backgroundPane.getChildren().add(hourPane);
        backgroundPane.setTopAnchor(hourPane, 0d);
        backgroundPane.setRightAnchor(hourPane, 0d);
        backgroundPane.setBottomAnchor(hourPane, 0d);
        backgroundPane.setLeftAnchor(hourPane, 0d);

        //minutePane
        minutePane = new AnchorPane();
        minutePane.getStyleClass().add("notRoundedPane");

        minuteButton = new JFXButton("minutes");
        minuteButton.getStyleClass().add("roundedTopRightButton");
        minuteButton.setMinSize(0, 0);
        minuteButton.setPrefSize(500, 150);
        minuteButton.setOnAction(event -> {
            minutePane.toFront();
        });

        minutePreviewLine = new Line();
        minutePreviewLine.setStroke(Color.web(GUI.primaryColor));
        minutePreviewLine.setStrokeLineCap(StrokeLineCap.ROUND);

        minutePreviewCircle = new Circle();
        minutePreviewCircle.setFill(Color.web(GUI.primaryColor));

        minutePreviewCircleHole = new Circle();
        minutePreviewCircleHole.setFill(Color.web(GUI.backgroundColor));
        minutePreviewCircleHole.setVisible(false);

        minuteLabels = new Label[12];
        for (int i = 0; i < minuteLabels.length; i++) {
            minuteLabels[i] = new Label(String.format("%02d", i * 5));
        }

        minutePaneOverlay = new Pane();

        minutePane.getChildren().addAll(minutePreviewLine, minutePreviewCircle, minutePreviewCircleHole);
        minutePane.getChildren().addAll(minuteLabels);
        minutePane.getChildren().add(minutePaneOverlay);
        minutePane.setTopAnchor(minutePaneOverlay, 0d);
        minutePane.setRightAnchor(minutePaneOverlay, 0d);
        minutePane.setBottomAnchor(minutePaneOverlay, 0d);
        minutePane.setLeftAnchor(minutePaneOverlay, 0d);

        backgroundPane.getChildren().add(minutePane);
        backgroundPane.setTopAnchor(minutePane, 0d);
        backgroundPane.setRightAnchor(minutePane, 0d);
        backgroundPane.setBottomAnchor(minutePane, 0d);
        backgroundPane.setLeftAnchor(minutePane, 0d);

        getPane().getChildren().remove(getDone());
        getPane().add(hourButton, 0, 0, 1, 1);
        getPane().add(minuteButton, 1, 0, 1, 1);
        getPane().add(backgroundPane, 0, 1, 2, 1);
        getPane().add(getDone(), 0, 2, 2, 1);

        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(14.3);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(71.4);
        RowConstraints r3 = new RowConstraints();
        r3.setPercentHeight(14.3);
        getPane().getRowConstraints().addAll(r1, r2, r3);
    }

    public void showOnCoordinates(double x, double y, JFXButton source, SimpleTime simpleTime) {
        setSource(source);

        int size = getPane().getChildren().size();

        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h, simpleTime);
    }

    public void show(JFXButton source, SimpleTime simpleTime) {
        setSource(source);

        int size = getPane().getChildren().size();

        double x = source.getLayoutX() + 1;
        double y = source.getLayoutY() + 1;
        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h, simpleTime);
    }

    private void show(double x, double y, double w, double h, SimpleTime simpleTime) {
        int size = getPane().getChildren().size();

        setHidden(false);

        getPane().setPrefWidth(w * getWidthFactor());
        getPane().setPrefHeight(h * size * getHeightFactor());
        getPane().setLayoutX(x);
        getPane().setLayoutY(y);

        arrangeComponents(w * getWidthFactor(), h * size * getHeightFactor(), simpleTime);

        getDone().setFont(new Font(getSource().getHeight() * getFontFactor()));

        if (onShow != null) {
            showEvent.fire();
        }

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(ANIMATION_DURATION * FOCUS_ANIMATION_OFFSET_FACTOR),
                n -> getPane().getChildren().get(0).requestFocus()));
        focus.play();

        Timeline reposition = new Timeline(new KeyFrame(Duration.millis(1), n -> {
            repositon();
        }));
        reposition.play();

        getPane().setVisible(true);
        show.play();
    }

    private void repositon() {

        double x = getPane().getLayoutX();
        double y = getPane().getLayoutY();
        double w = getPane().getWidth();
        double h = getPane().getHeight();

        if (x + w > getParent().getWidth()) {
            x = getParent().getWidth() - w;
            getPane().setLayoutX(x);
        }
        if (y + h > getParent().getHeight()) {
            y = getParent().getHeight() - h;
            getPane().setLayoutY(y);

        }
    }

    public void setOnShow(EventHandler<Event> onShow) {
        if (this.onShow != null) {
            showEvent.removeEventHandler(EventType.ROOT, this.onShow);
        }
        this.onShow = onShow;
        showEvent.addEventHandler(EventType.ROOT, onShow);
    }

    @Override
    public void hide() {
        if (isHidden() == false) {
            setHidden(true);

            if (onHide != null) {
                hideEvent.fire();
            }

            getSource().requestFocus();

            new Timeline(
                    new KeyFrame(Duration.millis(ANIMATION_DURATION), n -> getPane().setVisible(false))
            ).play();

            hide.play();
        }
    }

    @Override
    public void cancel() {
        setHidden(true);
        getPane().setVisible(false);
    }

    public void setOnHide(EventHandler<Event> onHide) {
        if (this.onHide != null) {
            hideEvent.removeEventHandler(EventType.ROOT, this.onHide);
        }
        this.onHide = onHide;
        hideEvent.addEventHandler(EventType.ROOT, onHide);
    }

    public void arrangeComponents(double w, double h, SimpleTime simpleTime) {
        hourPane.toFront();

        //hourPane
        hourButton.setText(simpleTime.formatHours());
        hourButton.setFont(new Font(getSource().getHeight() * 0.3));

        double hourRadius;
        if (simpleTime.getHours() < 12) {
            hourRadius = w * 0.4;
        } else {
            hourRadius = w * 0.25;
        }
        double hourAngle = Math.PI * 2d * ((double) simpleTime.getHours() / 12d) - Math.PI / 2;

        hourPreviewLine.setStrokeWidth(w / 100);
        hourPreviewLine.setStartX(w / 2);
        hourPreviewLine.setStartY(w / 2);
        hourPreviewLine.setEndX(Math.cos(hourAngle) * hourRadius + w / 2);
        hourPreviewLine.setEndY(Math.sin(hourAngle) * hourRadius + w / 2);

        hourPreviewCircle.setRadius(w / 12);
        hourPreviewCircle.setCenterX(Math.cos(hourAngle) * hourRadius + w / 2);
        hourPreviewCircle.setCenterY(Math.sin(hourAngle) * hourRadius + w / 2);

        for (int i = 0; i < hourLabels.length; i++) {
            double angle = Math.PI * 2d * ((double) i / 12) - Math.PI / 2;
            double radius;
            if (i < 12) {
                radius = w * 0.4;
                hourLabels[i].setFont(new Font(getSource().getHeight() * 0.2));
            } else {
                radius = w * 0.25;
                hourLabels[i].setFont(new Font(getSource().getHeight() * 0.15));
            }
            if(simpleTime.getHours() == i){
                hourLabels[i].setTextFill(Color.web(GUI.backgroundColor));
            }else{
                hourLabels[i].setTextFill(Color.web(GUI.foregroundColor));
            }
            hourLabels[i].setLayoutX(Math.cos(angle) * radius + w / 2 - hourLabels[i].getWidth() / 2);
            hourLabels[i].setLayoutY(Math.sin(angle) * radius + w / 2 - hourLabels[i].getHeight() / 2);
        }

        //minutePane
        minuteButton.setFont(new Font(getSource().getHeight() * 0.3));
        minuteButton.setText(simpleTime.formatMinutes());

        double minuteAngle = Math.PI * 2d * ((double) simpleTime.getMinutes() / 60d) - Math.PI / 2;

        minutePreviewLine.setStrokeWidth(w / 100);
        minutePreviewLine.setStartX(w / 2);
        minutePreviewLine.setStartY(w / 2);
        minutePreviewLine.setEndX(Math.cos(minuteAngle) * w * 0.4 + w / 2);
        minutePreviewLine.setEndY(Math.sin(minuteAngle) * w * 0.4 + w / 2);

        minutePreviewCircle.setCenterX(Math.cos(minuteAngle) * w * 0.4 + w / 2);
        minutePreviewCircle.setCenterY(Math.sin(minuteAngle) * w * 0.4 + w / 2);
        minutePreviewCircle.setRadius(w / 12);

        if (simpleTime.getMinutes() % 5 == 0) {
            minutePreviewCircleHole.setVisible(false);
        } else {
            minutePreviewCircleHole.setVisible(true);
            minutePreviewCircleHole.setRadius(w / 80);
            minutePreviewCircleHole.setCenterX(Math.cos(minuteAngle) * w * 0.4 + w / 2);
            minutePreviewCircleHole.setCenterY(Math.sin(minuteAngle) * w * 0.4 + w / 2);
        }

        for (int i = 0; i < minuteLabels.length; i++) {
            double angle = Math.PI * 2d * ((double) i / minuteLabels.length) - Math.PI / 2;
            double radius = w * 0.4;
            minuteLabels[i].setFont(new Font(getSource().getHeight() * 0.2));
            minuteLabels[i].applyCss();
            minuteLabels[i].setLayoutX(Math.cos(angle) * radius + w / 2 - minuteLabels[i].getWidth() / 2);
            minuteLabels[i].setLayoutY(Math.sin(angle) * radius + w / 2 - minuteLabels[i].getHeight() / 2);
        }
    }

}