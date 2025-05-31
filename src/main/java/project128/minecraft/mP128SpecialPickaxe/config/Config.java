package project128.minecraft.mP128SpecialPickaxe.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


public class Config {
    private static Config instance;

    private final Plugin plugin;

    private Config(Plugin plugin) {
        this.plugin = plugin;
    }

    public static synchronized Config getInstance(Plugin plugin) {
        if (instance == null)
            instance = new Config(plugin);
        return instance;
    }

    public Object get(@NotNull Field target) {
        try {
            ConfigurationSection section = getSection(target);
            if (section.get(target.fieldName).getClass() != target.type)
                throw new InvalidFieldType(target.fieldName, target.type,
                        section.get(target.fieldName).getClass());

            return section.getObject(target.fieldName, target.type);
        } catch (FieldDoesNotExist | InvalidFieldType | SectionDoesNotExist | ThisIsNotASection e) {
            plugin.getLogger().severe(e.getMessage());
            throw e;
        }
    }

    private @NotNull ConfigurationSection getSection(@NotNull Field target) {
        ConfigurationSection section = plugin.getConfig();
        for (int i = 0; i < target.path.length - 1; i++) {
            assert section != null;
            if (!section.isSet(target.path[i]))
                throw new SectionDoesNotExist(target.path[i]);
            if (!section.isConfigurationSection(target.path[i]))
                throw new ThisIsNotASection(target.path[i]);
            section = section.getConfigurationSection(target.path[i]);
        }
        assert section != null;
        if (!section.isSet(target.fieldName))
            throw new FieldDoesNotExist(target.fieldName);
        return section;
    }

    public enum Field {
        VERSION(new String[]{"version"}, Integer.class),
        NOT_ALLOWED_COMMAND(new String[]{"messages", "not-allowed-command"}, String.class),
        NOT_ALLOWED_USE(new String[]{"messages", "not-allowed-use"}, String.class),
        GIVE_SELF(new String[]{"messages", "give-self"}, String.class);

        public final String[] path;
        public final String fieldName;
        public final Class<?> type;

        Field(String @NotNull [] path, Class<?> type) {
            this.path = path;
            this.fieldName = path[path.length - 1];
            this.type = type;
        }
    }

}
