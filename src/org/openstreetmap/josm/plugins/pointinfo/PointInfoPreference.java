// License: GPL. See LICENSE file for details.
package org.openstreetmap.josm.plugins.pointinfo;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.preferences.DefaultTabPreferenceSetting;
import org.openstreetmap.josm.gui.preferences.PreferenceTabbedPane;
import org.openstreetmap.josm.tools.GBC;

/**
 * Plugin preferences.
 */
public class PointInfoPreference extends DefaultTabPreferenceSetting {

    private final JComboBox<String> module = new JComboBox<>();

    /**
     * Constructs a new {@code PointInfoPreference}.
     */
    public PointInfoPreference() {
        super("pointinfo", tr("Point information settings"), tr("Settings for the point information plugin."), true);
    }

    @Override
    public String getIconName() {
        return "info-sml.png";
    }

    @Override
    public void addGui(PreferenceTabbedPane gui) {
        JPanel panel = new JPanel(new GridBagLayout());
        for (String modName : PointInfoPlugin.getModules()) {
            module.addItem(modName);
        }
        module.setSelectedItem(Main.pref.get("plugin.pointinfo.module", "RUIAN"));
        module.setToolTipText(tr("The module called to get the point information."));
        panel.add(new JLabel(tr("Module")), GBC.std());
        panel.add(module, GBC.eol().fill(GridBagConstraints.HORIZONTAL).insets(5, 0, 0, 5));
        panel.add(Box.createVerticalGlue(), GBC.eol().fill(GridBagConstraints.VERTICAL));
        createPreferenceTabWithScrollPane(gui, panel);
    }

    @Override
    public boolean ok() {
        Main.pref.put("plugin.pointinfo.module", (String) module.getSelectedItem());
        return false;
    }
}
