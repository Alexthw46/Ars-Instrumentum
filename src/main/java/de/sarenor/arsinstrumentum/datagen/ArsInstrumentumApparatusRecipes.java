package de.sarenor.arsinstrumentum.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.IEnchantingRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeBuilder;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import de.sarenor.arsinstrumentum.setup.Registration;
import lombok.extern.log4j.Log4j2;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.Items;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ArsInstrumentumApparatusRecipes extends ApparatusRecipeProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    List<EnchantingApparatusRecipe> recipes = new ArrayList<>();

    public ArsInstrumentumApparatusRecipes(DataGenerator generatorIn) {
        super(generatorIn);
        this.generator = generatorIn;
    }

    private static Path getRecipePath(Path pathIn, String str) {
        return pathIn.resolve("data/ars_instrumentum/apparatus/recipes/" + str + ".json");
    }

    @Override
    public void run(HashCache cache) throws IOException {
        log.info("ArsInstrumentum: Recipe-Generation started");
        addEntries();
        Path output = this.generator.getOutputFolder();
        for (IEnchantingRecipe g : recipes) {
            if (g instanceof EnchantingApparatusRecipe) {
                System.out.println(g);
                Path path = getRecipePath(output, ((EnchantingApparatusRecipe) g).getId().getPath());
                DataProvider.save(GSON, cache, ((EnchantingApparatusRecipe) g).asRecipe(), path);
            }
        }
        log.info("ArsInstrumentum: Recipe-Generation ended");
    }

    public void addEntries() {
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(Registration.WIZARDS_ARMARIUM.get())
                .withReagent(ItemsRegistry.MUNDANE_BELT)
                .withPedestalItem(4, ItemsRegistry.BLAZE_FIBER)
                .withPedestalItem(2, Items.DIAMOND)
                .withPedestalItem(Items.ENDER_CHEST)
                .withPedestalItem(ItemsRegistry.MANIPULATION_ESSENCE)
                .build());

        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.ARCHMAGE_HOOD)
                .withReagent(ItemsRegistry.APPRENTICE_HOOD)
                .withPedestalItem(4, ItemsRegistry.END_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.ARCHMAGE_ROBES)
                .withReagent(ItemsRegistry.APPRENTICE_ROBES)
                .withPedestalItem(4, ItemsRegistry.END_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.ARCHMAGE_LEGGINGS)
                .withReagent(ItemsRegistry.APPRENTICE_LEGGINGS)
                .withPedestalItem(4, ItemsRegistry.END_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.ARCHMAGE_BOOTS)
                .withReagent(ItemsRegistry.APPRENTICE_BOOTS)
                .withPedestalItem(4, ItemsRegistry.END_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.APPRENTICE_HOOD)
                .withReagent(ItemsRegistry.NOVICE_HOOD)
                .withPedestalItem(4, ItemsRegistry.BLAZE_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.APPRENTICE_ROBES)
                .withReagent(ItemsRegistry.NOVICE_BOOTS)
                .withPedestalItem(4, ItemsRegistry.BLAZE_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.APPRENTICE_LEGGINGS)
                .withReagent(ItemsRegistry.NOVICE_LEGGINGS)
                .withPedestalItem(4, ItemsRegistry.BLAZE_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
        this.recipes.add(ApparatusRecipeBuilder.builder()
                .withResult(ItemsRegistry.APPRENTICE_BOOTS)
                .withReagent(ItemsRegistry.NOVICE_BOOTS)
                .withPedestalItem(4, ItemsRegistry.BLAZE_FIBER)
                //.keepEnchantmentsOfReagent(true)
                .build());
    }

    @Override
    public String getName() {
        return "ArsInstrumentumApparatus";
    }
}
