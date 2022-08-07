package org.bpc;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public class Misc {
    public static Number number(String value) {
        try {
            return NumberFormat.getInstance().parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
