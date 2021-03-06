package timetable;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DURATION;

/**
 *
 * @author Tobias
 */
public class MenuBackgroundPane extends Pane implements Hideable {

    FadeTransition fadeIn;
    FadeTransition fadeOut;

    boolean hidden = true;

    public MenuBackgroundPane(AnchorPane parent) {
        fadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION), this);
        fadeIn.setToValue(1);
        fadeOut = new FadeTransition(Duration.millis(ANIMATION_DURATION), this);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> setVisible(false));

        this.getStyleClass().add("menuBackgroundPane");
        updateColor();
        this.setVisible(false);

        parent.getChildren().add(this);
        parent.setTopAnchor(this, 0d);
        parent.setRightAnchor(this, 0d);
        parent.setBottomAnchor(this, 0d);
        parent.setLeftAnchor(this, 0d);
    }

    public void show() {
        hidden = false;
        this.setVisible(true);
        fadeIn.play();
    }

    @Override
    public void hide() {
        if (!hidden) {
            hidden = true;
            fadeOut.play();
        }
    }

    @Override
    public void cancel() {
        if (!hidden) {
            hidden = true;
            setVisible(false);
        }
    }

    public void updateColor() {
        setStyle("-fx-background-color:" + GUI.semiTransparent);
    }

}
