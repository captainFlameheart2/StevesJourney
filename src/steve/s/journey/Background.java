package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class Background {

    public final ArrayList<Texture> TEXTURES = new ArrayList<>();

    public void addTexturesToScene() {
        for (Texture t : TEXTURES) {
            t.addToScene();
        }
    }

    public void removeTexturesFromScene() {
        for (Texture t : TEXTURES) {
            t.removeFromScene();
        }
    }

}
