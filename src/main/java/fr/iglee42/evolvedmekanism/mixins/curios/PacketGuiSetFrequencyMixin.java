package fr.iglee42.evolvedmekanism.mixins.curios;

import fr.iglee42.evolvedmekanism.curios.CuriosHelper;
import mekanism.common.lib.frequency.IFrequencyItem;
import mekanism.common.network.to_server.PacketGuiSetFrequency;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// Run after Mekanism Curios' default-priority redirect. Modify the stored
// result, not the hand lookup invocation which it owns.
@Mixin(value = PacketGuiSetFrequency.class, remap = false, priority = 900)
public class PacketGuiSetFrequencyMixin {

    @ModifyVariable(method = "handle(Lnet/minecraftforge/network/NetworkEvent$Context;)V",
            at = @At("STORE"), ordinal = 0, require = 1, remap = false)
    private ItemStack evolvedmekanism$resolvePacketItem(ItemStack selected, NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null) return selected;
        return CuriosHelper.resolvePacketItem(player, selected,
                stack -> stack.getItem() instanceof IFrequencyItem);
    }
}
