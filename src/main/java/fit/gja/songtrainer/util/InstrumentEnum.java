package fit.gja.songtrainer.util;

// TODO - string value has to be same as enum string or else it breaks - fix later

public enum InstrumentEnum {
    GUITAR("GUITAR"),
    BASS("BASS"),
    DRUMS("DRUMS"),
    PIANO("PIANO");

    private final String value;

    InstrumentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValidStr(String valueStr) {
        for (InstrumentEnum enumVal : InstrumentEnum.values()) {
            if (enumVal.toString().equals(valueStr))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

