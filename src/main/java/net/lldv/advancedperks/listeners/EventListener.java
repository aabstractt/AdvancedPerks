package net.lldv.advancedperks.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerFoodLevelChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import net.lldv.advancedperks.AdvancedPerks;
import net.lldv.advancedperks.components.data.PerksType;

import java.util.ArrayList;
import java.util.List;

public class EventListener implements Listener {

    private final AdvancedPerks instance;

    public EventListener(final AdvancedPerks instance) {
        this.instance = instance;
    }

    @EventHandler
    public void on(final PlayerJoinEvent event) {
        final Config data = this.instance.getPerksManager().getData();
        if (!data.exists("players." + event.getPlayer().getName())) {
            final List<String> list = new ArrayList<>();
            data.set("players." + event.getPlayer().getName(), list);
            data.save();
            data.reload();
            final List<PerksType> perkList = new ArrayList<>();
            this.instance.getPerksManager().getPlayerPerks().put(event.getPlayer().getName(), perkList);
            return;
        }
        if (!this.instance.getPerksManager().getPlayerPerks().containsKey(event.getPlayer().getName())) {
            final List<PerksType> list = new ArrayList<>();
            this.instance.getPerksManager().getData().getStringList("players." + event.getPlayer().getName()).forEach(perk -> {
                final PerksType perkType = PerksType.valueOf(perk.toUpperCase());
                list.add(perkType);
            });
            this.instance.getPerksManager().getPlayerPerks().put(event.getPlayer().getName(), list);
        }
        this.instance.getPerksManager().checkPlayer(event.getPlayer(), this.instance.getPerksManager().getPlayerPerks().get(event.getPlayer().getName()));
    }

    @EventHandler
    public void on(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            final List<PerksType> list = this.instance.getPerksManager().getPlayerPerks().get(player.getName());
            if (list.contains(PerksType.NO_FALL_DAMAGE)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
            }
            if (list.contains(PerksType.WATER_BREATHING)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) event.setCancelled(true);
            }
            if (list.contains(PerksType.NO_FIRE_DAMAGE)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.LAVA || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void on(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final List<PerksType> list = this.instance.getPerksManager().getPlayerPerks().get(player.getName());
        if (list.contains(PerksType.DOUBLE_XP)) {
            event.setDropExp(event.getDropExp() * 2);
        }
    }

    @EventHandler
    public void on(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final List<PerksType> list = this.instance.getPerksManager().getPlayerPerks().get(player.getName());
        if (list.contains(PerksType.KEEP_EXPERIENCE)) {
            event.setKeepExperience(true);
            event.setExperience(0);
        }
        if (list.contains(PerksType.KEEP_INVENTORY)) {
            event.setKeepInventory(true);
            event.setDrops(null);
        }
    }

    @EventHandler
    public void on(final PlayerFoodLevelChangeEvent event) {
        final Player player = event.getPlayer();
        final List<PerksType> list = this.instance.getPerksManager().getPlayerPerks().get(player.getName());
        if (list.contains(PerksType.NO_HUNGER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final List<PerksType> list = this.instance.getPerksManager().getPlayerPerks().get(player.getName());
        list.forEach(perk -> {
            if (perk == PerksType.JUMP_BOOST) player.removeEffect(Effect.JUMP);
            if (perk == PerksType.NIGHT_VISION) player.removeEffect(Effect.NIGHT_VISION);
            if (perk == PerksType.SPEED) player.removeEffect(Effect.SPEED);
            if (perk == PerksType.FAST_BLOCK_BREAK) player.removeEffect(Effect.HASTE);
            if (perk == PerksType.STRENGTH) player.removeEffect(Effect.STRENGTH);
            if (perk == PerksType.REGENERATION) player.removeEffect(Effect.REGENERATION);
        });
    }

}
