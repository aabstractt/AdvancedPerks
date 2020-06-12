package net.lldv.advancedperks.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.lldv.advancedperks.components.forms.FormWindows;

public class PerksCommand extends Command {

    public PerksCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            FormWindows.sendPerkMenu(player);
        }
        return false;
    }
}
