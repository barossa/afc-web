package by.epam.afc.service.util;

import java.util.Random;

public class CodeGenerator {
    private static final CodeGenerator instance = new CodeGenerator();

    private static final int MIN = 100000;
    private static final int MAX = 999999;

    private CodeGenerator(){
    }

    public static CodeGenerator getInstance() {
        return instance;
    }

    public int generate(){
        Random random = new Random();
        int code = random.ints(MIN, MAX)
                .findFirst()
                .orElse(MIN);
        return code;
    }
}
