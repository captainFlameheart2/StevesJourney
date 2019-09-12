package steve.s.journey;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

/**
 *
 * @author jonat
 */
public class Display extends JPanel implements Runnable {

    private static int updateCount;
    private static double paintingExtrapolation;

    public final RenderingHints RENDERING_HINTS;

    public static boolean pausable;

    public final Camera CAMERA = new Camera(this, new Vector2D());

    public final MouseHandler MOUSE_HANDLER = new MouseHandler(this);
    public final KeyHandler KEY_HANDLER = new KeyHandler(this);

    public final StartScreen START_SCREEN = new StartScreen(this);
    public final MenuScreenStorage MENU_SCREEN_STORAGE = new MenuScreenStorage(this);
    public final AudioPlayerStorage AUDIO_PLAYER_STORAGE = new AudioPlayerStorage();

    final HubWorld HUB_WORLD = new HubWorld(this);

    public Display() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        RENDERING_HINTS = TransitionWall.mainMenuTransitionRenderingHints();

        Updatable.addMember(CAMERA, Camera.UPDATING_ORDER);

        addMouseListener(MOUSE_HANDLER);
        addKeyListener(KEY_HANDLER);
        setFocusable(true);

        new LaunchCredits(this);
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        int lag = 0;

        while (Thread.currentThread() != null) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastTime;
            lag += elapsedTime;

            while (lag >= Updatable.NS_PER_UPDATE) {
                update();
                lag -= Updatable.NS_PER_UPDATE;
            }
            paintingExtrapolation = lag / Updatable.NS_PER_UPDATE;

            repaint();

            lastTime = currentTime;
        }
    }

    private void update() {
        MOUSE_HANDLER.cursor = Cursor.getDefaultCursor();
        Updatable.modifyMemberLists();
        Updatable.updateAll();
        updateCount++;
        setCursor(MOUSE_HANDLER.cursor);
    }

    public static int getUpdateCount() {
        return updateCount;
    }

    public static double getPaintingExtrapolation() {
        return paintingExtrapolation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D modifiedG = modifiedGraphics(g);

        Paintable.modifyMemberLists();
        Paintable.paintAllWhitingPaintingDist(modifiedG);
    }

    private Graphics2D modifiedGraphics(Graphics sourceGraphics) {
        Graphics2D modifiedGraphics = (Graphics2D) sourceGraphics;
        modifiedGraphics.addRenderingHints(RENDERING_HINTS);
        Vector2D coordinateSystemCentering = halfSize();
        modifiedGraphics.translate(coordinateSystemCentering.x, coordinateSystemCentering.y);
        CAMERA.modifyGraphics(modifiedGraphics);
        return modifiedGraphics;
    }

    public Vector2D halfSize() {
        return new Vector2D(getWidth() / 2, getHeight() / 2);
    }

//    double paintDist() {
//        return halfSize().mag();
//    }

}
