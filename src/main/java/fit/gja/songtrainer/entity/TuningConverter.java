package fit.gja.songtrainer.entity;

import javax.persistence.AttributeConverter;

public class TuningConverter implements AttributeConverter<Tuning, String> {

    @Override
    public String convertToDatabaseColumn(Tuning tuning) {
        if (tuning == Tuning.E_STANDARD)
            return "E standard";
        else if (tuning == Tuning.HALF_STEP_DOWN)
            return "half step down";
        else if (tuning == Tuning.DROP_D)
            return "drop D";
        else if (tuning == Tuning.D_STANDARD)
            return "D standard";
        else if (tuning == Tuning.DROP_C)
            return "drop C";
        else if (tuning == Tuning.C_STANDARD)
            return "C standard";
        else
            return "drop B";
    }

    @Override
    public Tuning convertToEntityAttribute(String s) {
        if (s.equals("E standard"))
            return Tuning.E_STANDARD;
        else if (s.equals("half step down"))
            return Tuning.HALF_STEP_DOWN;
        else if (s.equals("drop D"))
            return Tuning.DROP_D;
        else if (s.equals("D standard"))
            return Tuning.D_STANDARD;
        else if (s.equals("drop C"))
            return Tuning.DROP_C;
        else if (s.equals("C standard"))
            return Tuning.C_STANDARD;
        else if (s.equals("drop B"))
            return Tuning.DROP_B;
        else {
            throw new IllegalStateException("Unexpected value: " + s);
        }
    }
}
