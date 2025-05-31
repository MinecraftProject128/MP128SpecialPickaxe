package project128.minecraft.mP128SpecialPickaxe.config;

public class SectionDoesNotExist extends RuntimeException {
    public SectionDoesNotExist(String section) {
        super("Секции \"" + section + "\" не существует в файле config.yml!");
    }
}
