package net.lldv.advancedperks.components.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.level.Sound;
import net.lldv.advancedperks.AdvancedPerks;
import net.lldv.advancedperks.components.data.PerksType;
import net.lldv.advancedperks.components.forms.custom.CustomForm;
import net.lldv.advancedperks.components.tools.Language;

import java.util.List;

public class FormWindows {

    private final AdvancedPerks instance;

    public FormWindows(final AdvancedPerks instance) {
        this.instance = instance;
    }

    public void sendPerkMenu(Player player) {
        List<PerksType> list = this.instance.getPerksManager().getPlayerPerks().get(player.getName());
        CustomForm form = new CustomForm.Builder(Language.getAndReplaceNP("perk-menu-title"))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-falldamage", this.instance.getPerksManager().hasPermission(player, PerksType.NO_FALL_DAMAGE)), list.contains(PerksType.NO_FALL_DAMAGE)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-nohunger", this.instance.getPerksManager().hasPermission(player, PerksType.NO_HUNGER)), list.contains(PerksType.NO_HUNGER)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-speed", this.instance.getPerksManager().hasPermission(player, PerksType.SPEED)), list.contains(PerksType.SPEED)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-nightvision", this.instance.getPerksManager().hasPermission(player, PerksType.NIGHT_VISION)), list.contains(PerksType.NIGHT_VISION)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-jumpboost", this.instance.getPerksManager().hasPermission(player, PerksType.JUMP_BOOST)), list.contains(PerksType.JUMP_BOOST)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-firedamage", this.instance.getPerksManager().hasPermission(player, PerksType.NO_FIRE_DAMAGE)), list.contains(PerksType.NO_FIRE_DAMAGE)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-waterbreathing", this.instance.getPerksManager().hasPermission(player, PerksType.WATER_BREATHING)), list.contains(PerksType.WATER_BREATHING)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-fastbreak", this.instance.getPerksManager().hasPermission(player, PerksType.FAST_BLOCK_BREAK)), list.contains(PerksType.FAST_BLOCK_BREAK)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-keepxp", this.instance.getPerksManager().hasPermission(player, PerksType.KEEP_EXPERIENCE)), list.contains(PerksType.KEEP_EXPERIENCE)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-keepinventory", this.instance.getPerksManager().hasPermission(player, PerksType.KEEP_INVENTORY)), list.contains(PerksType.KEEP_INVENTORY)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-strength", this.instance.getPerksManager().hasPermission(player, PerksType.STRENGTH)), list.contains(PerksType.STRENGTH)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-regeneration", this.instance.getPerksManager().hasPermission(player, PerksType.REGENERATION)), list.contains(PerksType.REGENERATION)))
                .addElement(new ElementToggle(Language.getAndReplaceNP("perk-menu-doublexp", this.instance.getPerksManager().hasPermission(player, PerksType.DOUBLE_XP)), list.contains(PerksType.DOUBLE_XP)))
                .onSubmit((e, r) -> {
                    this.instance.getPerksManager().updatePerk(e, PerksType.NO_FALL_DAMAGE, r.getToggleResponse(0));
                    this.instance.getPerksManager().updatePerk(e, PerksType.NO_HUNGER, r.getToggleResponse(1));
                    this.instance.getPerksManager().updatePerk(e, PerksType.SPEED, r.getToggleResponse(2));
                    this.instance.getPerksManager().updatePerk(e, PerksType.NIGHT_VISION, r.getToggleResponse(3));
                    this.instance.getPerksManager().updatePerk(e, PerksType.JUMP_BOOST, r.getToggleResponse(4));
                    this.instance.getPerksManager().updatePerk(e, PerksType.NO_FIRE_DAMAGE, r.getToggleResponse(5));
                    this.instance.getPerksManager().updatePerk(e, PerksType.WATER_BREATHING, r.getToggleResponse(6));
                    this.instance.getPerksManager().updatePerk(e, PerksType.FAST_BLOCK_BREAK, r.getToggleResponse(7));
                    this.instance.getPerksManager().updatePerk(e, PerksType.KEEP_EXPERIENCE, r.getToggleResponse(8));
                    this.instance.getPerksManager().updatePerk(e, PerksType.KEEP_INVENTORY, r.getToggleResponse(9));
                    this.instance.getPerksManager().updatePerk(e, PerksType.STRENGTH, r.getToggleResponse(10));
                    this.instance.getPerksManager().updatePerk(e, PerksType.REGENERATION, r.getToggleResponse(11));
                    this.instance.getPerksManager().updatePerk(e, PerksType.DOUBLE_XP, r.getToggleResponse(12));
                    player.sendMessage(Language.getAndReplace("perks-saved", this.instance.getPerksManager().getPlayerPerks().get(player.getName()).size()));
                    this.instance.getPerksManager().playSound(player, Sound.MOB_VILLAGER_YES);
                })
                .build();
        form.send(player);
    }

}
