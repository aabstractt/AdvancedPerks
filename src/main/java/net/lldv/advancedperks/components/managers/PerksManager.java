package net.lldv.advancedperks.components.managers;

import cn.nukkit.Player;
import cn.nukkit.level.Sound;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import lombok.Getter;
import net.lldv.advancedperks.AdvancedPerks;
import net.lldv.advancedperks.components.data.PerksType;
import net.lldv.advancedperks.components.tools.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerksManager {

    private final AdvancedPerks instance;

    @Getter
    private final Config data;

    @Getter
    private final Map<String, List<PerksType>> playerPerks = new HashMap<>();

    public PerksManager(final AdvancedPerks instance) {
        this.instance = instance;
        this.data = new Config(this.instance.getDataFolder() + "/data/playerdata.yml", Config.YAML);
    }

    public void updatePerk(Player player, PerksType perk, boolean active) {
        List<PerksType> list = this.playerPerks.get(player.getName());
        List<String> dataList = this.data.getStringList("players." + player.getName());
        if (active) {
            if (player.hasPermission("perks.use." + perk.name().toLowerCase())) {
                if (!list.contains(perk) && !dataList.contains(perk.name().toUpperCase())) {
                    list.add(perk);
                    dataList.add(perk.name().toUpperCase());
                    this.data.set("players." + player.getName(), dataList);
                    this.data.save();
                    this.data.reload();
                    if (perk == PerksType.JUMP_BOOST) player.addEffect(Effect.getEffect(Effect.JUMP).setDuration(Integer.MAX_VALUE).setAmplifier(2));
                    if (perk == PerksType.NIGHT_VISION) player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(Integer.MAX_VALUE).setAmplifier(1));
                    if (perk == PerksType.SPEED) player.addEffect(Effect.getEffect(Effect.SPEED).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                    if (perk == PerksType.FAST_BLOCK_BREAK) player.addEffect(Effect.getEffect(Effect.HASTE).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                    if (perk == PerksType.STRENGTH) player.addEffect(Effect.getEffect(Effect.STRENGTH).setDuration(Integer.MAX_VALUE).setAmplifier(1));
                    if (perk == PerksType.REGENERATION) player.addEffect(Effect.getEffect(Effect.REGENERATION).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                }
            }
        } else {
            list.remove(perk);
            dataList.remove(perk.name().toUpperCase());
            this.data.set("players." + player.getName(), dataList);
            this.data.save();
            this.data.reload();
            if (perk == PerksType.JUMP_BOOST) player.removeEffect(Effect.JUMP);
            if (perk == PerksType.NIGHT_VISION) player.removeEffect(Effect.NIGHT_VISION);
            if (perk == PerksType.SPEED) player.removeEffect(Effect.SPEED);
            if (perk == PerksType.FAST_BLOCK_BREAK) player.removeEffect(Effect.HASTE);
            if (perk == PerksType.STRENGTH) player.removeEffect(Effect.STRENGTH);
            if (perk == PerksType.REGENERATION) player.removeEffect(Effect.REGENERATION);
        }
    }

    public String hasPermission(Player player, PerksType perk) {
        if (player.hasPermission("perks.use." + perk.name().toLowerCase())) {
            return Language.getAndReplaceNP("valid-permission");
        } else return Language.getAndReplaceNP("invalid-permission");
    }

    public void checkPlayer(Player player, List<PerksType> perks) {
        List<PerksType> perksList = new ArrayList<>(perks);
        perksList.forEach(perk -> {
            if (!player.hasPermission("perks.use." + perk.name().toLowerCase())) {
                this.updatePerk(player, perk, false);
            } else {
                if (perk == PerksType.JUMP_BOOST) player.addEffect(Effect.getEffect(Effect.JUMP).setDuration(Integer.MAX_VALUE).setAmplifier(2));
                if (perk == PerksType.NIGHT_VISION) player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(Integer.MAX_VALUE).setAmplifier(1));
                if (perk == PerksType.SPEED) player.addEffect(Effect.getEffect(Effect.SPEED).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                if (perk == PerksType.FAST_BLOCK_BREAK) player.addEffect(Effect.getEffect(Effect.HASTE).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                if (perk == PerksType.STRENGTH) player.addEffect(Effect.getEffect(Effect.STRENGTH).setDuration(Integer.MAX_VALUE).setAmplifier(1));
                if (perk == PerksType.REGENERATION) player.addEffect(Effect.getEffect(Effect.REGENERATION).setDuration(Integer.MAX_VALUE).setAmplifier(3));
            }
        });
    }

    public void playSound(Player player, Sound sound) {
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = sound.getSound();
        packet.x = new Double(player.getLocation().getX()).intValue();
        packet.y = (new Double(player.getLocation().getY())).intValue();
        packet.z = (new Double(player.getLocation().getZ())).intValue();
        packet.volume = 1.0F;
        packet.pitch = 1.0F;
        player.dataPacket(packet);
    }

}
