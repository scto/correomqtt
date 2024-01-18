package org.correomqtt;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.correomqtt.business.eventbus.EventBus;
import org.correomqtt.business.eventbus.Subscribe;
import org.correomqtt.business.fileprovider.SettingsProvider;
import org.correomqtt.business.utils.VersionUtils;
import org.correomqtt.gui.views.PreloaderViewController;
import org.correomqtt.gui.window.StageHelper;
import org.correomqtt.plugin.PreloadingProgressEvent;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class CorreoPreloader extends Preloader {

    PreloaderViewController preloaderViewController;
    private Scene scene;
    private Stage preloaderStage;

    public CorreoPreloader() {
        EventBus.register(this);
    }

    @Override
    public void init() throws IOException {
        setLoggerFilePath();

        String cssPath = SettingsProvider.getInstance().getCssPath();

        FXMLLoader loader = new FXMLLoader(PreloaderViewController.class.getResource("preloaderView.fxml"));
        Parent root = loader.load();

        preloaderViewController = loader.getController();
        preloaderViewController.getPreloaderVersionLabel().setText("v" + VersionUtils.getVersion());
        scene = new Scene(root, 500, 300);
        scene.setFill(SettingsProvider.getInstance().getActiveTheme().getBackgroundColor());

        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
    }

    private void setLoggerFilePath() {
        // Set the path for file logging to user directory.
        System.setProperty("correomqtt-logfile", SettingsProvider.getInstance().getLogPath());

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        try (InputStream configStream = CorreoPreloader.class.getResourceAsStream("logger-config.xml")) {
            configurator.setContext(loggerContext);
            configurator.doConfigure(configStream);
        } catch (JoranException | IOException e) {
            System.out.println("Problem configuring logger: " + e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        preloaderStage.setScene(scene);

        StageHelper.enforceFloatingWindow(preloaderStage);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setNode(preloaderViewController.getPreloaderProgressLabel());
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.play();

        preloaderStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }

    @SuppressWarnings("unused")
    public void onProgress(@Subscribe PreloadingProgressEvent event) {
        Platform.runLater(() -> preloaderViewController.getPreloaderStepLabel().setText(event.msg()));
    }

}
