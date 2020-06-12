package net.lldv.advancedperks.components.managers;

import cn.nukkit.Player;
import cn.nukkit.level.Sound;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import net.lldv.advancedperks.AdvancedPerks;
import net.lldv.advancedperks.components.data.PerksType;
import net.lldv.advancedperks.components.tools.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerksManager {

    public static HashMap<String, List<PerksType>> playerPerks = new HashMap<>();
    public static final Config data = new Config(AdvancedPerks.getInstance().getDataFolder() + "/data/playerdata.yml", Config.YAML);

    public static void updatePerk(Player player, PerksType perk, boolean active) {
        List<PerksType> list = playerPerks.get(player.getName());
        List<String> dataList = data.getStringList("players." + player.getName());
        if (active) {
            if (player.hasPermission("perks.use." + perk.name().toLowerCase())) {
                if (!list.contains(perk) && !dataList.contains(perk.name().toUpperCase())) {
                    list.add(perk);
                    dataList.add(perk.name().toUpperCase());
                    data.set("players." + player.getName(), dataList);
                    data.save();
                    data.reload();
                    if (perk == PerksType.JUMP_BOOST) player.addEffect(Effect.getEffect(Effect.JUMP).setDuration(Integer.MAX_VALUE).setAmplifier(2));
                    if (perk == PerksType.NIGHT_VISION) player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(Integer.MAX_VALUE).setAmplifier(1));
                    if (perk == PerksType.SPEED) player.addEffect(Effect.getEffect(Effect.SPEED).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                    if (perk == PerksType.FAST_BLOCK_BREAK) player.addEffect(Effect.getEffect(Effect.HASTE).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                }
            }
        } else {
            list.remove(perk);
            dataList.remove(perk.name().toUpperCase());
            data.set("players." + player.getName(), dataList);
            data.save();
            data.reload();
            if (perk == PerksType.JUMP_BOOST) player.removeEffect(Effect.JUMP);
            if (perk == PerksType.NIGHT_VISION) player.removeEffect(Effect.NIGHT_VISION);
            if (perk == PerksType.SPEED) player.removeEffect(Effect.SPEED);
            if (perk == PerksType.FAST_BLOCK_BREAK) player.removeEffect(Effect.HASTE);
        }
    }

    public static String hasPermission(Player player, PerksType perk) {
        if (player.hasPermission("perks.use." + perk.name().toLowerCase())) {
            return Language.getAndReplaceNP("valid-permission");
        } else return Language.getAndReplaceNP("invalid-permission");
    }

    public static void checkPlayer(Player player, List<PerksType> perks) {
        List<PerksType> perksList = new ArrayList<>(perks);
        perksList.forEach(perk -> {
            if (!player.hasPermission("perks.use." + perk.name().toLowerCase())) {
                updatePerk(player, perk, false);
            } else {
                if (perk == PerksType.JUMP_BOOST) player.addEffect(Effect.getEffect(Effect.JUMP).setDuration(Integer.MAX_VALUE).setAmplifier(2));
                if (perk == PerksType.NIGHT_VISION) player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(Integer.MAX_VALUE).setAmplifier(1));
                if (perk == PerksType.SPEED) player.addEffect(Effect.getEffect(Effect.SPEED).setDuration(Integer.MAX_VALUE).setAmplifier(3));
                if (perk == PerksType.FAST_BLOCK_BREAK) player.addEffect(Effect.getEffect(Effect.HASTE).setDuration(Integer.MAX_VALUE).setAmplifier(3));
            }
        });
    }

    public static void playSound(Player player, Sound sound) {
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
