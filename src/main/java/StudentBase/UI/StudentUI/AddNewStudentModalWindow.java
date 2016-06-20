package StudentBase.UI.StudentUI;

import StudentBase.DAO.GenericDao;
import StudentBase.Entities.Group;
import StudentBase.Entities.Student;
import StudentBase.UI.Util.ValidatorNotNull;
import StudentBase.UI.Util.ValidatorTextFieldWithOnlyCharAndNotNull;
import com.vaadin.data.Validator;
import com.vaadin.ui.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gres on 20.06.2016.
 */
class AddNewStudentModalWindow extends Window {
    List<Group> groupList;
    private StudentUI studentUI;

    public AddNewStudentModalWindow(StudentUI studentUI) {
        super("Добавить нового студента");
        this.studentUI = studentUI;
        center();
        VerticalLayout modalWindowLayout = new VerticalLayout();
        modalWindowLayout.setSpacing(true);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        modalWindowLayout.setMargin(true);

        TextField surName = new TextField("Фамилия");
        surName.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());
        TextField name = new TextField("Имя");
        name.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());
        TextField patronymic = new TextField("Отчество");
        patronymic.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());

        DateField birthDay = new DateField("Дата рождения");
        birthDay.addValidator(new ValidatorNotNull());
        ComboBox groupNumber = new ComboBox("Номер группы");
        groupNumber.addValidator(new ValidatorNotNull());


        groupNumber.setNullSelectionAllowed(false);
        groupNumber.setTextInputAllowed(false);

        fillComboBox(groupNumber);

        Button addNewStudent = new Button("Добавить");
        Button cancelButton = new Button("Отменить");

        buttonsLayout.addComponents(addNewStudent, cancelButton);

        modalWindowLayout.addComponents(name, surName, patronymic, birthDay, groupNumber, buttonsLayout);

        setContent(modalWindowLayout);

        addNewStudent.addClickListener(clickEvent ->
        {
            try {
                name.validate();
                surName.validate();
                patronymic.validate();
                groupNumber.validate();
                birthDay.validate();
                Long id = getGroupIDByGroupNumber(Integer.parseInt(groupNumber.getValue().toString()));
                Student student = new Student(surName.getValue(), name.getValue(), patronymic.getValue(), birthDay.getValue(), id);

                try (Connection connection = studentUI.getDf().getConnection()) {
                    GenericDao<Student> studentDao = studentUI.getDf().getStudentDao(connection);
                    studentDao.persist(student);
                    studentUI.refreshGrid();
                    close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            } catch (Validator.InvalidValueException e) {
                Notification.show(e.getMessage());
            }
        });
        studentUI.setDisabledEditAndDeleteButtons();
        cancelButton.addClickListener(clickEvent -> close());
    }


    private void fillComboBox(ComboBox groupNumber) {
        groupList = studentUI.getGroupsList();
        for (Group group : groupList) {
            groupNumber.addItem(group.getNumber());
        }
    }

    private Long getGroupIDByGroupNumber(Integer groupNumber) {
        Long id = null;
        for (Group group : groupList) {
            if (group.getNumber() == groupNumber) {
                id = group.getId();
            }
        }
        return id;
    }
}