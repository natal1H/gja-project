package fit.gja.songtrainer.util;

/**
 * Class representing guitar and bass tuning
 */
public class Tuning {

    /**
     * Enum containing most popular tunings
     */
    public enum TuningEnum {
        NONE("NONE"),
        E_STANDARD("E_STANDARD"),
        HALF_STEP_DOWN("HALF_STEP_DOWN"),
        DROP_D("DROP_D"),
        D_STANDARD("D_STANDARD"),
        DROP_C("DROP_C"),
        C_STANDARD("C_STANDARD"),
        DROP_B("DROP_B");

        private final String value;

        TuningEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
