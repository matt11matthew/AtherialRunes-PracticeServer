package net.atherialrunes.practiceserver.api.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Matthew E on 9/21/2016.
 */
public abstract class AtherialCommand implements CommandExecutor, TabExecutor {

    protected final String command;
    protected final String description;
    protected final List<String> alias;
    protected final String usage;
    protected final String permMessage;

    protected static CommandMap cmap;

    /**
     * Just a command.
     *
     * @param command the command.
     * @since 1.0
     */
    public AtherialCommand(String command) {
        this(command, null, null, null, null);
    }

    /**
     * Command and usage.
     *
     * @param command the command.
     * @param usage   The command usage
     * @since 1.0
     */
    public AtherialCommand(String command, String usage) {
        this(command, usage, null, null, null);
    }

    /**
     * Command, usage & description!
     *
     * @param command     the command.
     * @param usage       The command usage
     * @param description About the command.
     * @since 1.0
     */
    public AtherialCommand(String command, String usage, String description) {
        this(command, usage, description, null, null);
    }

    /**
     * Command, usage, description and permissions message.
     *
     * @param command           the command.
     * @param usage             The command usage
     * @param description       About the command.
     * @param permissionMessage The permission message!
     * @since 1.0
     */
    public AtherialCommand(String command, String usage, String description, String permissionMessage) {
        this(command, usage, description, permissionMessage, null);
    }

    /**
     * Command, usage, description and aliases.
     *
     * @param command     the command.
     * @param usage       The command usage
     * @param description About the command.
     * @param aliases     Aliases.
     */
    public AtherialCommand(String command, String usage, String description, List<String> aliases) {
        this(command, usage, description, null, aliases);
    }

    public AtherialCommand(String command, List<String> aliases) {
        this(command, null, null, null, aliases);
    }
    /**
     * Command, usage, description, permissionsMessage and Aliases.
     *
     * @param command           the command.
     * @param usage             The command usage
     * @param description       About the command.
     * @param permissionMessage The permission message!
     * @param aliases           aliases.
     * @since 1.0
     */
    public AtherialCommand(String command, String usage, String description, String permissionMessage, List<String> aliases) {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.permMessage = permissionMessage;
        this.alias = aliases;
    }

    public void register() {
        ReflectCommand cmd = new ReflectCommand(this.command);
        if (this.alias != null) cmd.setAliases(this.alias);
        if (this.description != null) cmd.setDescription(this.description);
        if (this.usage != null) cmd.setUsage(this.usage);
        if (this.permMessage != null) cmd.setPermissionMessage(this.permMessage);
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);
    }

    final CommandMap getCommandMap() {
        if (cmap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return cmap;
        }
        return getCommandMap();
    }

    private final class ReflectCommand extends Command {
        private AtherialCommand exe = null;

        protected ReflectCommand(String command) {
            super(command);
        }

        public void setExecutor(AtherialCommand exe) {
            this.exe = exe;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            return exe != null && exe.onCommand(sender, this, commandLabel, args);
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alais, String[] args) {
            if (exe != null) {
                return exe.onTabComplete(sender, this, alais, args);
            }
            return null;
        }
    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}