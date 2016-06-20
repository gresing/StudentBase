package StudentBase.UI.Util;

import com.vaadin.data.Validator;

/**
 * Created by gres on 20.06.2016.
 */
public class ValidatorNotNull implements Validator {
    @Override
    public void validate(Object o) throws InvalidValueException {
        if (o == null || o.toString().trim().equals("")) {
            throw new InvalidValueException("Значение не может быть нулевым");
        }
    }
}
