package de.sarenor.arsinstrumentum.client.renderer.tile;

import de.sarenor.arsinstrumentum.ArsInstrumentum;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;

public class GenericModel<T extends GeoAnimatable> extends com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel<T> {
    public String path;

    public GenericModel(String name) {
        super(name);
        this.modelLocation = ResourceLocation.fromNamespaceAndPath(ArsInstrumentum.MODID, "geo/" + name + ".geo.json");
        this.textLoc = ResourceLocation.fromNamespaceAndPath(ArsInstrumentum.MODID, "textures/" + textPathRoot + "/" + name + ".png");
        this.animationLoc = ResourceLocation.fromNamespaceAndPath(ArsInstrumentum.MODID, "animations/" + name + "_animations.json");
    }

    public GenericModel<T> withEmptyAnim() {
        this.animationLoc = ResourceLocation.fromNamespaceAndPath(ArsInstrumentum.MODID, "animations/empty.json");
        return this;
    }

}
