package StudentBase.UI.Util;

import com.vaadin.data.Validator;

/**
 * Created by gres on 20.06.2016.
 */
public class ValidatorTextFieldWithOnlyNumericAndNotNull implements Validator {
    @Override
    public void validate(Object value) throws InvalidValueException {
        if (value.toString() == "") {
            throw new InvalidValueException("Значение не может быть нулевым");
        }
        try {
            Long.parseLong(value.toString());
        } catch (Exception e) {
            throw new InvalidValueException("Ожидается цифровое значение");
        }

    }
}