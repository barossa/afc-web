package by.epam.afc.service.util;

import java.util.Random;

/**
 * The type Code generator.
 */
public class CodeGenerator {
    private static final CodeGenerator instance = new CodeGenerator();

    private static final int MIN = 100000;
    private static final int MAX = 999999;

    private CodeGenerator() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CodeGenerator getInstance() {
        return instance;
    }

    /**
     * Generate int.
     *
     * @return the int
     */
    public int generate() {
        Random random = new Random();
        int code = random.ints(MIN, MAX)
                .findFirst()
                .orElse(MIN);
        return code;
    }
}
