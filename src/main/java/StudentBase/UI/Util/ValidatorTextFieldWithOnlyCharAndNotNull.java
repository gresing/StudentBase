package StudentBase.UI.Util;

import com.vaadin.data.Validator;

/**
 * Created by gres on 20.06.2016.
 */
public class ValidatorTextFieldWithOnlyCharAndNotNull implements Validator {
    @Override
    public void validate(Object value) throws InvalidValueException {
        if (value == null || value.toString() == "") {
            throw new InvalidValueException("Значение не может быть нулевым");
        }
        char[] numeric = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        for (char c : numeric) {
            if (!(value instanceof String && !((String) value).contains(String.valueOf(c))))
                throw new InvalidValueException("Ожидается бувенное значение");
        }
    }
}
