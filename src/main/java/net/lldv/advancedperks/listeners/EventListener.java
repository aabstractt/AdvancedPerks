package net.lldv.advancedperks.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerFoodLevelChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import net.lldv.advancedperks.components.data.PerksType;
import net.lldv.advancedperks.components.managers.PerksManager;

import java.util.ArrayList;
import java.util.List;

public class EventListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Config data = PerksManager.data;
        if (!data.exists("players." + event.getPlayer().getName())) {
            List<String> list = new ArrayList<>();
            data.set("players." + event.getPlayer().getName(), list);
            data.save();
            data.reload();
            List<PerksType> perkList = new ArrayList<>();
            PerksManager.playerPerks.put(event.getPlayer().getName(), perkList);
            return;
        }
        if (!PerksManager.playerPerks.containsKey(event.getPlayer().getName())) {
            List<PerksType> list = new ArrayList<>();
            PerksManager.data.getStringList("players." + event.getPlayer().getName()).forEach(perk -> {
                PerksType perkType = PerksType.valueOf(perk.toUpperCase());
                list.add(perkType);
            });
            PerksManager.playerPerks.put(event.getPlayer().getName(), list);
        }
        PerksManager.checkPlayer(event.getPlayer(), PerksManager.playerPerks.get(event.getPlayer().getName()));
    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            List<PerksType> list = PerksManager.playerPerks.get(player.getName());
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
    public void on(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<PerksType> list = PerksManager.playerPerks.get(player.getName());
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
    public void on(PlayerFoodLevelChangeEvent event) {
        Player player = event.getPlayer();
        List<PerksType> list = PerksManager.playerPerks.get(player.getName());
        if (list.contains(PerksType.NO_HUNGER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        List<PerksType> list = PerksManager.playerPerks.get(player.getName());
        list.forEach(perk -> {
            if (perk == PerksType.JUMP_BOOST) player.removeEffect(Effect.JUMP);
            if (perk == PerksType.NIGHT_VISION) player.removeEffect(Effect.NIGHT_VISION);
            if (perk == PerksType.SPEED) player.removeEffect(Effect.SPEED);
            if (perk == PerksType.FAST_BLOCK_BREAK) player.removeEffect(Effect.HASTE);
        });
    }

}
