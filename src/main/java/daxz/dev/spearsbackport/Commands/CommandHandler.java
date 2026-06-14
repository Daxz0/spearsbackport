package daxz.dev.spearsbackport.Commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import daxz.dev.spearsbackport.Registry.ItemRegistry;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

public class CommandHandler {

    public static LiteralCommandNode<CommandSourceStack> spears() {

        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("spears");

        root.then(
                Commands.literal("give")
                        .requires(sender -> sender.getSender().hasPermission("op"))
                        .then(
                Commands.argument("item", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            ItemRegistry.getRegisteredItems().keySet().forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .executes(ctx -> {
                            if (ctx.getSource().getSender() instanceof Player player) {
                                ItemRegistry.giveItem(player, ctx.getArgument("item", String.class));
                            }
                            return 1;
                        })
                )
        );

        return root.build();
    }




}
