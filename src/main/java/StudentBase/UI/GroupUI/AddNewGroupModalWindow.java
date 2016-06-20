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
class AddNewGroupModalWindow extends Window {

    public AddNewGroupModalWindow(GroupUI groupUI) { //TODO общая асбтракция с edit
        super("Добавить новую группу");
        center();
        setDraggable(false);
        VerticalLayout modalWindowLayout = new VerticalLayout();
        modalWindowLayout.setSpacing(true);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        modalWindowLayout.setMargin(true);

        TextField groupName = new TextField("Название факультета");
        groupName.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());

        TextField groupNumber = new TextField("Номер группы");
        groupNumber.addValidator(new ValidatorTextFieldWithOnlyNumericAndNotNull());

        Button addNewGroup = new Button("Добавить");
        Button cancelButton = new Button("Отменить");

        buttonsLayout.addComponents(addNewGroup, cancelButton);

        modalWindowLayout.addComponents(groupNumber, groupName, buttonsLayout);

        setContent(modalWindowLayout);

        addNewGroup.addClickListener(clickEvent ->
        {
            Group g = new Group();

            try {
                groupName.validate();
                groupNumber.validate();
                try (Connection connection = groupUI.getDf().getConnection()) {
                    GenericDao<Group> groupDao = groupUI.getDf().getGroupDao(connection);
                    g.setFacultyName(groupName.getValue());
                    g.setNumber(Integer.parseInt(groupNumber.getValue()));
                    groupDao.persist(g);
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