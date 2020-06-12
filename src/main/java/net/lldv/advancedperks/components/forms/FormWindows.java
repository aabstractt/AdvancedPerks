package net.lldv.advancedperks.components.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.level.Sound;
import net.lldv.advancedperks.components.data.PerksType;
import net.lldv.advancedperks.components.forms.custom.CustomForm;
import net.lldv.advancedperks.components.managers.PerksManager;
import net.lldv.advancedperks.components.tools.Language;

import java.util.List;

public class FormWindows {

    public static void sendPerkMenu(Player player) {
        List<PerksType> list = PerksManager.playerPerks.get(player.getName());
        CustomForm form = new CustomForm.Builder(Language.getAndReplaceNP("perk-menu-title"))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-falldamage", PerksManager.hasPermission(player, PerksType.NO_FALL_DAMAGE)), list.contains(PerksType.NO_FALL_DAMAGE)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-nohunger", PerksManager.hasPermission(player, PerksType.NO_HUNGER)), list.contains(PerksType.NO_HUNGER)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-speed", PerksManager.hasPermission(player, PerksType.SPEED)), list.contains(PerksType.SPEED)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-nightvision", PerksManager.hasPermission(player, PerksType.NIGHT_VISION)), list.contains(PerksType.NIGHT_VISION)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-jumpboost", PerksManager.hasPermission(player, PerksType.JUMP_BOOST)), list.contains(PerksType.JUMP_BOOST)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-firedamage", PerksManager.hasPermission(player, PerksType.NO_FIRE_DAMAGE)), list.contains(PerksType.NO_FIRE_DAMAGE)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-waterbreathing", PerksManager.hasPermission(player, PerksType.WATER_BREATHING)), list.contains(PerksType.WATER_BREATHING)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-fastbreak", PerksManager.hasPermission(player, PerksType.FAST_BLOCK_BREAK)), list.contains(PerksType.FAST_BLOCK_BREAK)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-keepxp", PerksManager.hasPermission(player, PerksType.KEEP_EXPERIENCE)), list.contains(PerksType.KEEP_EXPERIENCE)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-keepinventory", PerksManager.hasPermission(player, PerksType.KEEP_INVENTORY)), list.contains(PerksType.KEEP_INVENTORY)))
                .onSubmit((e, r) -> {
                    PerksManager.updatePerk(e, PerksType.NO_FALL_DAMAGE, r.getToggleResponse(0));
                    PerksManager.updatePerk(e, PerksType.NO_HUNGER, r.getToggleResponse(1));
                    PerksManager.updatePerk(e, PerksType.SPEED, r.getToggleResponse(2));
                    PerksManager.updatePerk(e, PerksType.NIGHT_VISION, r.getToggleResponse(3));
                    PerksManager.updatePerk(e, PerksType.JUMP_BOOST, r.getToggleResponse(4));
                    PerksManager.updatePerk(e, PerksType.NO_FIRE_DAMAGE, r.getToggleResponse(5));
                    PerksManager.updatePerk(e, PerksType.WATER_BREATHING, r.getToggleResponse(6));
                    PerksManager.updatePerk(e, PerksType.FAST_BLOCK_BREAK, r.getToggleResponse(7));
                    PerksManager.updatePerk(e, PerksType.KEEP_EXPERIENCE, r.getToggleResponse(8));
                    PerksManager.updatePerk(e, PerksType.KEEP_INVENTORY, r.getToggleResponse(9));
                    player.sendMessage(Language.getAndReplace("perks-saved", PerksManager.playerPerks.get(player.getName()).size()));
                    PerksManager.playSound(player, Sound.MOB_VILLAGER_YES);
                })
                .build();
        form.send(player);
    }

}
