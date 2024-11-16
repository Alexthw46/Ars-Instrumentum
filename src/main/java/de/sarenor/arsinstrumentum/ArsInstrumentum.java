package de.sarenor.arsinstrumentum;

import com.hollingsworth.arsnouveau.setup.registry.CreativeTabRegistry;
import de.sarenor.arsinstrumentum.network.Networking;
import de.sarenor.arsinstrumentum.setup.ArsInstrumentumConfig;
import de.sarenor.arsinstrumentum.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsInstrumentum.MODID)
public class ArsInstrumentum {
    public static final String MODID = "ars_instrumentum";
    public static ModConfigSpec SERVER_CONFIG;

    public ArsInstrumentum(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ArsInstrumentumConfig.CLIENT_SPEC);
        Registration.init(modEventBus);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::doTabsStuff);
        modEventBus.addListener(Networking::register);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void doTabsStuff(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CreativeTabRegistry.BLOCKS.get()) {
            for (var item : Registration.ITEMS.getEntries()) {
                event.accept(item::get);
            }
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

}
