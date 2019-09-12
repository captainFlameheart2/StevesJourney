package steve.s.journey;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

/**
 *
 * @author jonat
 */
public class Texture implements Paintable {

    private final Display DISPLAY;

    private final boolean INDEPENDENT;

    private final BufferedImage IMAGE;
    private final Vector2D SIZE, HALF_SIZE, MAX_PIXEL_COORDS;

    private final Vector2D POS;
    private final double CAMERA_DIST_MULTIPLIER;

    public Texture(Display display, BufferedImage image, Vector2D pos, double cameraDistMultiplier) {
        DISPLAY = display;

        IMAGE = image;
        SIZE = new Vector2D(IMAGE.getWidth(), IMAGE.getHeight());
        HALF_SIZE = Vector2D.div(SIZE, 2);
        MAX_PIXEL_COORDS = new Vector2D(SIZE.x - 1, SIZE.y - 1);

        POS = new Vector2D(pos);
        CAMERA_DIST_MULTIPLIER = cameraDistMultiplier;

        INDEPENDENT = true;
    }

    public Texture(BufferedImage image, Vector2D pos) {
        DISPLAY = null;
        IMAGE = image;
        SIZE = new Vector2D(IMAGE.getWidth(), IMAGE.getHeight());
        HALF_SIZE = Vector2D.div(SIZE, 2);
        MAX_PIXEL_COORDS = new Vector2D(SIZE.x - 1, SIZE.y - 1);
        POS = pos;
        CAMERA_DIST_MULTIPLIER = 0;

        INDEPENDENT = false;
    }

    public void addToScene() {
        if (INDEPENDENT) {
            Paintable.addMember(this, 0);
        }
    }

    public void removeFromScene() {
        if (INDEPENDENT) {
            Paintable.removeMember(this, 0);
        }
    }

    @Override
    public boolean whitinPaintingDist() {
        return Paintable.rectWithinPaintDist(POS, SIZE, 0, DISPLAY.CAMERA);
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        mainPaintingGraphics(sourceGraphics).drawImage(IMAGE, (int) -HALF_SIZE.x, (int) -HALF_SIZE.y, DISPLAY);
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        if (INDEPENDENT) {
            Graphics2D mainPaintingGraphics = (Graphics2D) sourceGraphics.create();
            Vector2D translation = Vector2D.sub(POS, DISPLAY.CAMERA.POS);
            translation.mult(CAMERA_DIST_MULTIPLIER);
            translation.add(DISPLAY.CAMERA.POS);
            mainPaintingGraphics.translate(translation.x, translation.y);
            return mainPaintingGraphics;
        } else {
            return sourceGraphics;
        }
    }

    @Override
    public Shape mainPaintingShape() {
        return null;
    }

    public int rgbAt(Vector2D point, boolean globalPoint) {
        Vector2D topLeftrelativePoint = new Vector2D(point);
        if (globalPoint) {
            topLeftrelativePoint.sub(POS);
        }
        topLeftrelativePoint.add(HALF_SIZE);
        topLeftrelativePoint.limitComponents(new Vector2D(), MAX_PIXEL_COORDS);
        return IMAGE.getRGB((int) topLeftrelativePoint.x, (int) topLeftrelativePoint.y);
    }

}
