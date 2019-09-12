package steve.s.journey;

import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class MenuScreenStorage {

    public final MenuScreen HOME_MENU_SCREEN,
            CREATION_MENU_SCREEN,
            HUB_CREATION_SPECIFICATION_MENU_SCREEN,
            HUB_CREATION_MENU_SCREEN,
            PAUSE_MENU_SCREEN,
            SETTINGS_MENU_SCREEN;

    public MenuScreenStorage(Display display) {
        Button homeButton = new Button(display, Button.ID_HOME);
        Button backButton = new Button(display, Button.ID_BACK);

        ArrayList<Button> homeMenuScreenButtons = new ArrayList<>();
        homeMenuScreenButtons.add(new Button(display, Button.ID_PLAY));
        homeMenuScreenButtons.add(new Button(display, Button.ID_CREATE));
        homeMenuScreenButtons.add(new Button(display, Button.ID_QUIT));
        homeMenuScreenButtons.add(new Button(display, Button.ID_SETTINGS));
        HOME_MENU_SCREEN = new MenuScreen(display, homeMenuScreenButtons, null);

        ArrayList<Button> creationMenuScreenButtons = new ArrayList<>();
        creationMenuScreenButtons.add(new Button(display, Button.ID_MAP));
        creationMenuScreenButtons.add(new Button(display, Button.ID_HUB_WORLD));
        creationMenuScreenButtons.add(homeButton);
        CREATION_MENU_SCREEN = new MenuScreen(
                display,
                creationMenuScreenButtons,
                new MenuScreenQuestion(display, "What Do You Want To Create?", new Vector2D(0, -300))
        );

        ArrayList<Button> hubCreationSpecificationMenuScreenButtons = new ArrayList<>();
        hubCreationSpecificationMenuScreenButtons.add(new Button(display, Button.ID_FROM_SCRATCH));
        hubCreationSpecificationMenuScreenButtons.add(new Button(display, Button.ID_FROM_OTHER_HUB_WORLD));
        hubCreationSpecificationMenuScreenButtons.add(backButton);
        HUB_CREATION_SPECIFICATION_MENU_SCREEN = new MenuScreen(
                display,
                hubCreationSpecificationMenuScreenButtons,
                new MenuScreenQuestion(display, "How Do You Create It?", new Vector2D(0, -300))
        );

        ArrayList<Button> hubCreationMenuScreenButtons = new ArrayList<>();
        hubCreationMenuScreenButtons.add(new Button(display, Button.ID_SPOT));
        HUB_CREATION_MENU_SCREEN = new MenuScreen(display, hubCreationMenuScreenButtons, null);

        ArrayList<Button> settingsMenuScreenButtons = new ArrayList<>();
        settingsMenuScreenButtons.add(homeButton);
        SETTINGS_MENU_SCREEN = new MenuScreen(display, settingsMenuScreenButtons, null);

        ArrayList<Button> pauseMenuScreenButtons = new ArrayList<>();
        pauseMenuScreenButtons.add(new Button(display, Button.ID_CONTINUE));
        pauseMenuScreenButtons.add(new Button(display, Button.ID_MAIN_MENU));
        PAUSE_MENU_SCREEN = new MenuScreen(display, pauseMenuScreenButtons, null);
    }

}
