package project128.minecraft.mP128SpecialPickaxe.config;

public class ThisIsNotASection extends RuntimeException {
    public ThisIsNotASection(String section) {
        super("\"" + section + "\" в файле config.yml - это не секция, а должна быть!");
    }
}
