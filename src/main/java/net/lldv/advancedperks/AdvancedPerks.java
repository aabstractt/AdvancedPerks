package net.lldv.advancedperks;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import net.lldv.advancedperks.commands.PerksCommand;
import net.lldv.advancedperks.components.forms.FormListener;
import net.lldv.advancedperks.components.forms.FormWindows;
import net.lldv.advancedperks.components.managers.PerksManager;
import net.lldv.advancedperks.components.tools.Language;
import net.lldv.advancedperks.listeners.EventListener;

public class AdvancedPerks extends PluginBase {

    @Getter
    private PerksManager perksManager;

    @Getter
    private FormWindows formWindows;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.saveResource("/data/playerdata.yml");
        Language.initConfiguration(this);
        this.perksManager = new PerksManager(this);
        this.formWindows = new FormWindows(this);
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        this.getServer().getPluginManager().registerEvents(new FormListener(), this);
        this.getServer().getCommandMap().register("advancedperks", new PerksCommand(this));
    }

}
