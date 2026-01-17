package ru.yourname.soulsword.client.particle;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.yourname.soulsword.SoulSwordMod;

@Mod.EventBusSubscriber(modid = SoulSwordMod.MODID, value = Side.CLIENT)
public class SoulParticleRegistry {

    private static final ResourceLocation BLEEDING = new ResourceLocation(SoulSwordMod.MODID, "particle/bleeding");
    private static final ResourceLocation MARK = new ResourceLocation(SoulSwordMod.MODID, "particle/mark_effect");
    private static final ResourceLocation FEAR_AURA = new ResourceLocation(SoulSwordMod.MODID, "particle/fear_aura");
    private static final ResourceLocation SOUL_WAVE = new ResourceLocation(SoulSwordMod.MODID, "particle/soul_wave");
    private static final ResourceLocation WAVE_PURPLE = new ResourceLocation(SoulSwordMod.MODID, "particle/wave_purple 1");
    private static final ResourceLocation WAVE_RED = new ResourceLocation(SoulSwordMod.MODID, "particle/wave_red 1");
    private static final ResourceLocation WAVE_GOLD = new ResourceLocation(SoulSwordMod.MODID, "particle/wave_gold 1");
    private static final ResourceLocation BLOOD_SHIELD = new ResourceLocation(SoulSwordMod.MODID, "particle/blood_shield_single");

    private static TextureAtlasSprite bleedingSprite;
    private static TextureAtlasSprite markSprite;
    private static TextureAtlasSprite fearAuraSprite;
    private static TextureAtlasSprite soulWaveSprite;
    private static TextureAtlasSprite wavePurpleSprite;
    private static TextureAtlasSprite waveRedSprite;
    private static TextureAtlasSprite waveGoldSprite;
    private static TextureAtlasSprite bloodShieldSprite;

    @SubscribeEvent
    public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(BLEEDING);
        event.getMap().registerSprite(MARK);
        event.getMap().registerSprite(FEAR_AURA);
        event.getMap().registerSprite(SOUL_WAVE);
        event.getMap().registerSprite(WAVE_PURPLE);
        event.getMap().registerSprite(WAVE_RED);
        event.getMap().registerSprite(WAVE_GOLD);
        event.getMap().registerSprite(BLOOD_SHIELD);
    }

    @SubscribeEvent
    public static void onTextureStitchPost(TextureStitchEvent.Post event) {
        bleedingSprite = event.getMap().getAtlasSprite(BLEEDING.toString());
        markSprite = event.getMap().getAtlasSprite(MARK.toString());
        fearAuraSprite = event.getMap().getAtlasSprite(FEAR_AURA.toString());
        soulWaveSprite = event.getMap().getAtlasSprite(SOUL_WAVE.toString());
        wavePurpleSprite = event.getMap().getAtlasSprite(WAVE_PURPLE.toString());
        waveRedSprite = event.getMap().getAtlasSprite(WAVE_RED.toString());
        waveGoldSprite = event.getMap().getAtlasSprite(WAVE_GOLD.toString());
        bloodShieldSprite = event.getMap().getAtlasSprite(BLOOD_SHIELD.toString());
    }

    public static TextureAtlasSprite getBleedingSprite() {
        return bleedingSprite;
    }

    public static TextureAtlasSprite getMarkSprite() {
        return markSprite;
    }

    public static TextureAtlasSprite getFearAuraSprite() {
        return fearAuraSprite;
    }

    public static TextureAtlasSprite getSoulWaveSprite() {
        return soulWaveSprite;
    }

    public static TextureAtlasSprite getWaveSprite(int stageId) {
        if (stageId >= 9 && waveGoldSprite != null) {
            return waveGoldSprite;
        }
        if (stageId >= 5 && waveRedSprite != null) {
            return waveRedSprite;
        }
        if (stageId >= 3 && wavePurpleSprite != null) {
            return wavePurpleSprite;
        }
        return soulWaveSprite;
    }

    public static TextureAtlasSprite getBloodShieldSprite() {
        return bloodShieldSprite;
    }
}
