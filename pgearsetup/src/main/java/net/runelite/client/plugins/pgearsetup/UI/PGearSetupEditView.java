package net.runelite.client.plugins.pgearsetup.UI;

import net.runelite.client.plugins.pgearsetup.GearSetupData;
import net.runelite.client.plugins.pgearsetup.GearSetupItemOptions;
import net.runelite.client.plugins.pgearsetup.PGearSetup;
import net.runelite.client.plugins.paistisuite.api.types.PKitType;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.HashMap;

import static net.runelite.client.plugins.pgearsetup.UI.PGearSetupPanel.BACKGROUND_COLOR;
import static net.runelite.client.plugins.pgearsetup.UI.PGearSetupPanel.PANEL_BACKGROUND_COLOR;
import static net.runelite.client.ui.PluginPanel.PANEL_WIDTH;

public class PGearSetupEditView extends JPanel {
    PGearSetup plugin;
    PGearSetupPanel mainPanel;
    GearSetupData data;

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(PluginPanel.PANEL_WIDTH, super.getPreferredSize().height);
    }

    public PGearSetupEditView(PGearSetup plugin, PGearSetupPanel mainPanel, GearSetupData data){
        this.plugin = plugin;
        this.data = data;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(5, 1, 5, 1, PANEL_BACKGROUND_COLOR),
                BorderFactory.createLineBorder(BACKGROUND_COLOR, 2)
        ));
        buildPanel();
    }

    private void buildPanel(){
        JPanel equipmentView = new JPanel();
        equipmentView.setLayout(null);
        equipmentView.setPreferredSize(new Dimension(PANEL_WIDTH, 210));
        equipmentView.setSize(new Dimension(PANEL_WIDTH, 210));

        HashMap<PKitType, PGearSetupItemElement> slotElements = new HashMap<>();
        for (PKitType k : PKitType.values()){
            if (k == PKitType.HAIR || k == PKitType.JAW) continue;
            GearSetupItemOptions slotItem = this.data.getEquipment().getOrDefault(k, null);
            if (slotItem != null){
                slotElements.put(k, new PGearSetupItemElement(plugin, mainPanel, slotItem, k));
            } else {
                data.getEquipment().put(k, new GearSetupItemOptions(-1, -1, false));
                slotElements.put(k, new PGearSetupItemElement(plugin, mainPanel, data.getEquipment().get(k), k));
            }
        }

        for (PGearSetupItemElement slot : slotElements.values()){
            equipmentView.add(slot);
        }

        slotElements.get(PKitType.HEAD).setBounds((equipmentView.getWidth()/2) - 21, 5, 36, 36);

        slotElements.get(PKitType.AMULET).setBounds((equipmentView.getWidth()/2) - 21, 5 + 40, 36, 36);
        slotElements.get(PKitType.CAPE).setBounds((equipmentView.getWidth()/2) - 21 - 40, 5 + 40, 36, 36);
        slotElements.get(PKitType.AMMUNITION).setBounds((equipmentView.getWidth()/2) - 21 + 40, 5 + 40, 36, 36);

        slotElements.get(PKitType.TORSO).setBounds((equipmentView.getWidth()/2) - 21, 5 + 40*2, 36, 36);
        slotElements.get(PKitType.WEAPON).setBounds((equipmentView.getWidth()/2) - 21 - 55, 5 + 40*2, 36, 36);
        slotElements.get(PKitType.SHIELD).setBounds((equipmentView.getWidth()/2) - 21 + 55, 5 + 40*2, 36, 36);

        slotElements.get(PKitType.LEGS).setBounds((equipmentView.getWidth()/2) - 21, 5 + 40*3, 36, 36);

        slotElements.get(PKitType.BOOTS).setBounds((equipmentView.getWidth()/2) - 21, 5 + 40*4, 36, 36);
        slotElements.get(PKitType.HANDS).setBounds((equipmentView.getWidth()/2) - 21 - 55, 5 + 40*4, 36, 36);
        slotElements.get(PKitType.RING).setBounds((equipmentView.getWidth()/2) - 21 + 55, 5 + 40*4, 36, 36);

        JPanel inventoryView = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
        inventoryView.setPreferredSize(new Dimension(PANEL_WIDTH, 320));
        for (int i = 0; i < 28; i++){
            if (i < data.getInventory().size()) {
                inventoryView.add(new PGearSetupItemElement(plugin, mainPanel, data.getInventory().get(i)));
            } else {
                data.getInventory().add(new GearSetupItemOptions(-1, -1, false));
                inventoryView.add(new PGearSetupItemElement(plugin, mainPanel, data.getInventory().get(i)));
            }
        }

        add(equipmentView, BorderLayout.CENTER);
        add(inventoryView, BorderLayout.SOUTH);
    }
}
