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
class EditStudentModalWindow extends Window {
    List<Group> groupList;
    private StudentUI studentUI;

    public EditStudentModalWindow(StudentUI studentUI) {
        super("Редактировать студента");
        this.studentUI = studentUI;
        center();
        VerticalLayout modalWindowLayout = new VerticalLayout();
        modalWindowLayout.setSpacing(true);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        modalWindowLayout.setMargin(true);

        TextField surName = new TextField("Фамилия");
        surName.setValue(studentUI.getSelectedStudent().getSurname());
        TextField name = new TextField("Имя");
        name.setValue(studentUI.getSelectedStudent().getName());
        TextField patronymic = new TextField("Отчество");
        patronymic.setValue(studentUI.getSelectedStudent().getPatronymic());
        DateField birthDay = new DateField("Дата рождения");
        birthDay.setValue(studentUI.getSelectedStudent().getBirthday());
        ComboBox groupNumber = new ComboBox("Номер группы");


        surName.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());
        name.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());
        patronymic.addValidator(new ValidatorTextFieldWithOnlyCharAndNotNull());
        birthDay.addValidator(new ValidatorNotNull());
        groupNumber.addValidator(new ValidatorNotNull());


        groupNumber.setNullSelectionAllowed(false);
        groupNumber.setNewItemsAllowed(false);
        groupNumber.setTextInputAllowed(false);

        fillComboBox(groupNumber);
        groupNumber.setValue(studentUI.getSelectedStudentGroup());

        Button editStudent = new Button("Изменить");
        Button cancelButton = new Button("Отменить");

        buttonsLayout.addComponents(editStudent, cancelButton);

        modalWindowLayout.addComponents(name, surName, patronymic, birthDay, groupNumber, buttonsLayout);

        setContent(modalWindowLayout);

        editStudent.addClickListener(clickEvent ->
        {

            try {
                name.validate();
                surName.validate();
                patronymic.validate();
                groupNumber.validate();
                birthDay.validate();
                Long id = getGroupIDByGroupNumber(Integer.parseInt(groupNumber.getValue().toString()));
                Student student = studentUI.getSelectedStudent();
                student.setName(name.getValue());
                student.setSurname(surName.getValue());
                student.setPatronymic(patronymic.getValue());
                student.setBirthday(birthDay.getValue());
                student.setGroupId(id);

                try (Connection connection = studentUI.getDf().getConnection()) {
                    GenericDao<Student> studentDao = studentUI.getDf().getStudentDao(connection);
                    studentDao.update(student);
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