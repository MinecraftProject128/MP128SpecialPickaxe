package project128.minecraft.mP128SpecialPickaxe.config;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public class NonExistentValue extends RuntimeException {
    public <E extends Enum<E>> NonExistentValue(String value, Class<E> clazz) {
        super("Значение \"" + value + "\" неуместно! (Ожидается любое из значений: " +
                StringUtils.join(Arrays.stream(clazz.getEnumConstants()).toArray(), ", ") + ").");
    }
}
