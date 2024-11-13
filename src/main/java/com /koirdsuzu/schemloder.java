package com.koirdsuzu.schemloder;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.IOFunction;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.file.FilenameException;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SchematicPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SchematicPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SchematicPlugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Usage: /schematic <load|save|paste|copy> <filename>");
            return true;
        }

        String action = args[0];
        String fileName = args.length > 1 ? args[1] : "default";
        File schemFile = new File(getDataFolder(), fileName + ".schem");

        switch (action.toLowerCase()) {
            case "load":
                loadSchematic(player, schemFile);
                break;
            case "save":
                saveSchematic(player, schemFile);
                break;
            case "paste":
                pasteSchematic(player);
                break;
            case "copy":
                copySchematic(player);
                break;
            default:
                player.sendMessage("Invalid action. Use load, save, paste, or copy.");
                break;
        }
        return true;
    }

    private void loadSchematic(Player player, File file) {
        try (ClipboardReader reader = ClipboardFormats.findByFile(file).getReader(new FileInputStream(file))) {
            Clipboard clipboard = reader.read();
            player.sendMessage("Schematic loaded successfully.");
            // Store clipboard to paste later
            player.setMetadata("clipboard", clipboard);
        } catch (IOException | NullPointerException e) {
            player.sendMessage("Failed to load schematic.");
            e.printStackTrace();
        }
    }

private void saveSchematic(Player player, File file) {
    try {
        BukkitPlayer bukkitPlayer = BukkitAdapter.adapt(player);
        SessionManager sessionManager = WorldEdit.getInstance().getSessionManager();
        EditSession editSession = sessionManager.get(bukkitPlayer).createEditSession(bukkitPlayer);

        Clipboard clipboard = sessionManager.get(bukkitPlayer).getClipboard().getClipboard();
        
        // .schemファイルとして保存
        try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
            writer.write(clipboard);
            player.sendMessage("Schematic saved successfully as " + file.getName());
        } catch (IOException e) {
            player.sendMessage("Failed to save schematic.");
            e.printStackTrace();
        }
    } catch (Exception e) {
        player.sendMessage("Please make a WorldEdit selection first.");
    }
}

private void pasteSchematic(Player player) {
    try {
        BukkitPlayer bukkitPlayer = BukkitAdapter.adapt(player);
        SessionManager sessionManager = WorldEdit.getInstance().getSessionManager();
        EditSession editSession = sessionManager.get(bukkitPlayer).createEditSession(bukkitPlayer);

        Clipboard clipboard = (Clipboard) player.getMetadata("clipboard").get(0).value();
        
        // プレイヤーの位置に貼り付け
        Operations.complete(clipboard.paste(editSession, BukkitAdapter.asBlockVector(player.getLocation().toVector()), true));
        player.sendMessage("Schematic pasted successfully.");
    } catch (Exception e) {
        player.sendMessage("Failed to paste schematic.");
        e.printStackTrace();
    }
}

private void copySchematic(Player player) {
    try {
        BukkitPlayer bukkitPlayer = BukkitAdapter.adapt(player);
        SessionManager sessionManager = WorldEdit.getInstance().getSessionManager();
        Clipboard clipboard = sessionManager.get(bukkitPlayer).getClipboard().getClipboard();

        // プレイヤーのメタデータにコピーしたクリップボードを保存
        player.setMetadata("clipboard", clipboard);
        player.sendMessage("Schematic copied to clipboard.");
    } catch (Exception e) {
        player.sendMessage("Please make a WorldEdit selection first.");
        e.printStackTrace();
    }
}
