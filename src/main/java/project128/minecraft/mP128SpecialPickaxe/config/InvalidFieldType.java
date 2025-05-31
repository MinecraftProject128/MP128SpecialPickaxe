package project128.minecraft.mP128SpecialPickaxe.config;

public class InvalidFieldType extends RuntimeException {
    public InvalidFieldType(String field, Class<?> expected, Class<?> received) {
        super("Поле \"" + field + "\" имеет не верный тип в файле config.yml! (Ожидается " + expected.getName() + ", но получен " + received.getName() + ").");
    }
}
