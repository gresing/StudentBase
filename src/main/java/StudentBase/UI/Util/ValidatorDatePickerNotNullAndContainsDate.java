package StudentBase.UI.Util;

import com.vaadin.data.Validator;

import java.sql.Date;


/**
 * Created by gres on 20.06.2016.
 */
public class ValidatorDatePickerNotNullAndContainsDate implements Validator {
    @Override
    public void validate(Object o) throws InvalidValueException {
        if (o == null) {
            throw new InvalidValueException("Значение не может быть нулевым");
        }
        try {
            Date.valueOf(o.toString());
        } catch (Exception e) {
            throw new InvalidValueException("Ожидается дата");
        }
    }
}
