package fit.gja.songtrainer.entity;

import javax.persistence.AttributeConverter;

public class InstrumentConverter implements AttributeConverter<Instrument, String> {

    @Override
    public String convertToDatabaseColumn(Instrument instrument) {
        if (instrument == Instrument.GUITAR)
            return "guitar";
        else if (instrument == Instrument.BASS)
            return "bass";
        else if (instrument == Instrument.DRUMS)
            return "drums";
        else
            return "piano";
    }

    @Override
    public Instrument convertToEntityAttribute(String s) {
        if (s.equals("guitar"))
            return Instrument.GUITAR;
        else if (s.equals("bass"))
            return Instrument.BASS;
        else if (s.equals("drums"))
            return Instrument.DRUMS;
        else if (s.equals("piano"))
            return Instrument.PIANO;
        else {
            throw new IllegalStateException("Unexpected value: " + s);
        }
    }

    public boolean isValidString(String s) {
        if (s.equals("guitar") || s.equals("bass") || s.equals("drums") || s.equals("piano"))
            return true;
        else
            return false;
    }
}
