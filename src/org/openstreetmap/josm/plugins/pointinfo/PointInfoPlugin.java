// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.pointinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.preferences.PreferenceSetting;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.gui.MainMenu;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.pointinfo.ruian.RuianModule;
import org.openstreetmap.josm.plugins.pointinfo.catastro.CatastroModule;

/**
 * This is the main class for the PointInfo plugin.
 * @author Marián Kyral
 */
public class PointInfoPlugin extends Plugin {

    private static final HashMap<String, AbstractPointInfoModule> modules = new HashMap<>();
    static {
        registerModule(new RuianModule());
        registerModule(new CatastroModule());
    }

    /**
     * Constructs a new {@code PointInfoPlugin}.
     * @param info plugin information
     */
    public PointInfoPlugin(PluginInformation info) {
        super(info);
        MainMenu.add(MainApplication.getMenu().moreToolsMenu, new PointInfoAction());
    }

    @Override
    public PreferenceSetting getPreferenceSetting() {
        return new PointInfoPreference();
    }

    /**
     * Register a module as available to select in the preferences.
     * @param module PointInfo module
     */
    public static void registerModule(AbstractPointInfoModule module) {
        modules.put(module.getName(), module);
    }

    /**
     * Returns a list of available modules names
     * @return modsList 
     */
    public static List<String> getModules() {
        return new ArrayList<>(modules.keySet());
    }

    /**
     * Returns the currently selected module
     * @return currentModule
     */
    public static AbstractPointInfoModule getModule() {
        AbstractPointInfoModule r = modules.get(Main.pref.get("plugin.pointinfo.module", "RUIAN"));
        if (r == null) {
            r = modules.get("RUIAN");
        }
        return r;
    }
}
