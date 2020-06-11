package net.lldv.advancedperks;

import cn.nukkit.plugin.PluginBase;
import net.lldv.advancedperks.commands.PerksCommand;
import net.lldv.advancedperks.components.forms.FormListener;
import net.lldv.advancedperks.listeners.EventListener;

public class AdvancedPerks extends PluginBase {

    private static AdvancedPerks instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new FormListener(), this);
        getServer().getCommandMap().register(getConfig().getString("Commands.Perks"), new PerksCommand(getConfig().getString("Commands.Perks")));
    }

    public static AdvancedPerks getInstance() {
        return instance;
    }
}
