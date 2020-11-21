package net.lldv.advancedperks.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.advancedperks.AdvancedPerks;

public class PerksCommand extends PluginCommand<AdvancedPerks> {

    public PerksCommand(final AdvancedPerks owner) {
        super(owner.getConfig().getString("Commands.Perks.Name"), owner);
        this.setDescription(owner.getConfig().getString("Commands.Perks.Description"));
        this.setPermission(owner.getConfig().getString("Commands.Perks.Permission"));
        this.setAliases(owner.getConfig().getStringList("Commands.Perks.Aliases").toArray(new String[]{}));
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            this.getPlugin().getFormWindows().sendPerkMenu(player);
        }
        return false;
    }
}
