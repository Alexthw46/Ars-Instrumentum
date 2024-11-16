package de.sarenor.arsinstrumentum.items;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.registry.SpellCasterRegistry;
import com.hollingsworth.arsnouveau.api.spell.AbstractCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CopyPasteSpellScroll extends ModItem implements ICasterTool {

    public static final String COPY_PASTE_SPELL_SCROLL = "copy_paste_spell_scroll";
    public static final String APPLIED_CONFIGURATION = "Applied Spell";

    public CopyPasteSpellScroll() {
        super((new Properties()).stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level worldIn, Player player, @NotNull InteractionHand handIn) {
        ItemStack usedCopyPasteScroll = player.getItemInHand(handIn);
        if (!worldIn.isClientSide() && player.isShiftKeyDown()) {
            ItemStack offhand = player.getOffhandItem();
            if (offhand.getItem() instanceof ICasterTool offhandCasterTool) {
                AbstractCaster<? extends AbstractCaster<?>> copyPasteSpellcaster = this.getSpellCaster(usedCopyPasteScroll);
                AbstractCaster<? extends AbstractCaster<?>> offhandSpellcaster = offhandCasterTool.getSpellCaster(offhand);
                if (copyPasteSpellcaster == null || offhandSpellcaster == null) {
                    return new InteractionResultHolder<>(InteractionResult.PASS, usedCopyPasteScroll);
                }
                offhandSpellcaster.setSpell(copyPasteSpellcaster.getSpell());
                offhandSpellcaster.setColor(copyPasteSpellcaster.getColor());
                offhandSpellcaster.setSpellName(copyPasteSpellcaster.getSpellName());
                PortUtil.sendMessage(player, Component.literal(APPLIED_CONFIGURATION));
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, usedCopyPasteScroll);
            } else {
                return new InteractionResultHolder<>(InteractionResult.PASS, usedCopyPasteScroll);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, usedCopyPasteScroll);
    }

    public boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
        ItemStack heldStack = player.getItemInHand(handIn);
        AbstractCaster<? extends AbstractCaster<?>> thisCaster = SpellCasterRegistry.from(stack);
        if (thisCaster == null || !(heldStack.getItem() instanceof SpellBook) && !(heldStack.getItem() instanceof SpellParchment)) {
            return false;
        } else {
            Spell spell = new Spell();
            if (heldStack.getItem() instanceof ICasterTool) {
                AbstractCaster<? extends AbstractCaster<?>> heldCaster = SpellCasterRegistry.from(heldStack);
                if (heldCaster == null) {
                    return false;
                }
                spell = heldCaster.getSpell();
                thisCaster.setColor(heldCaster.getColor());
                thisCaster.setFlavorText(heldCaster.getFlavorText());
                thisCaster.setSpellName(heldCaster.getSpellName());
            }

            if (this.isScribedSpellValid(thisCaster, player, handIn, stack, spell)) {
                thisCaster.setSpell(spell, thisCaster.getCurrentSlot());
                this.sendSetMessage(player);
                return true;
            } else {
                this.sendInvalidMessage(player);
            }

            return false;
        }
    }

    @Override
    public boolean doesSneakBypassUse(@NotNull ItemStack stack, @NotNull LevelReader world, @NotNull BlockPos pos, @NotNull Player player) {
        return true;
    }

    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext tooltipContext, @NotNull List<Component> tooltip2, @NotNull TooltipFlag flagIn) {
        AbstractCaster<? extends AbstractCaster<?>> copyPasteSpellcaster = this.getSpellCaster(stack);
        if (copyPasteSpellcaster != null) {
            tooltip2.add(Component.literal("Inscribed Spell: " + copyPasteSpellcaster.getSpellName()));
            tooltip2.add(Component.literal(copyPasteSpellcaster.getSpell().getDisplayString()));
        }
        super.appendHoverText(stack, tooltipContext, tooltip2, flagIn);
    }
}
