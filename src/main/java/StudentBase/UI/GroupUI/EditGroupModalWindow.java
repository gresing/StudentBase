package StudentBase.UI.GroupUI;

import StudentBase.DAO.GenericDao;
import StudentBase.Entities.Group;
import StudentBase.UI.Util.ValidatorTextFieldWithOnlyCharAndNotNull;
import StudentBase.UI.Util.ValidatorTextFieldWithOnlyNumericAndNotNull;
import com.vaadin.data.Validator;
import com.vaadin.ui.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gres on 20.06.2016.
 */

class EditGroupModalWindow extends Window {


    public EditGroupModalWindow(GroupUI groupUI) {
        super("Редактирование группы");

        center();
        setDraggable(false);
        VerticalLayout modalWindowLayout = new VerticalLayout();
        modalWindowLayout.setSpacing(true);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        modalWindowLayout.setMargin(true);

        Group selectedGroup = groupUI.getSelectedGroup();

        TextField groupName = new TextField("Название группы");
        groupName.setValue(selectedGroup.getFacultyName());
        groupName.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());

        TextField groupNumber = new TextField("Номер группы");
        groupNumber.setValue(String.valueOf(selectedGroup.getNumber()));
        groupNumber.addValidator(new ValidatorTextFieldWithOnlyNumericAndNotNull());

        Button editGroup = new Button("Изменить");
        Button cancelButton = new Button("Отменить");

        buttonsLayout.addComponents(editGroup, cancelButton);
        modalWindowLayout.addComponents(groupName, groupNumber, buttonsLayout);

        setContent(modalWindowLayout);

        editGroup.addClickListener(clickEvent ->
        {
            try {
                groupName.validate();
                groupNumber.validate();
                Group g = selectedGroup;
                g.setFacultyName(groupName.getValue());
                g.setNumber(Integer.parseInt(groupNumber.getValue()));
                g.setId(selectedGroup.getId());

                try (Connection connection = groupUI.getDf().getConnection()) {
                    GenericDao<Group> groupDao = groupUI.getDf().getGroupDao(connection);
                    groupDao.update(g);
                    groupUI.refreshGrid();
                    close();
                    groupUI.setEditAndDeleteButtonsDisabled();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (Validator.InvalidValueException e) {
                Notification.show(e.getMessage());
            }

        });
        cancelButton.addClickListener(clickEvent -> close());
    }
}