package project128.minecraft.mP128SpecialPickaxe.config;

public class FieldDoesNotExist extends RuntimeException {
    public FieldDoesNotExist(String field) {
        super("Поля \"" + field + "\" не существует в файле config.yml!");
    }
}
