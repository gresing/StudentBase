package StudentBase.UI.StudentUI;

import StudentBase.DAO.DaoFactory;
import StudentBase.DAO.GenericDao;
import StudentBase.DAO.HSQLDBDAO;
import StudentBase.Entities.Group;
import StudentBase.Entities.Student;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unchecked")
public class StudentUI extends CustomComponent {

    private DaoFactory df;
    private String caption;

    private List<Student> studentList;
    private Integer selectedStudentGroup;
    private Student selectedStudent;

    private IndexedContainer container;

    private Grid grid;
    private Button addNewStudent;
    private Button deleteStudent;
    private Button editButton;
    List<TextField> filterFields;


    public StudentUI(String caption) {
        this.caption = caption;

        filterFields = new ArrayList<>();

        df = new HSQLDBDAO();
        initContainer();

        addNewStudent = new Button("Добавить нового студента");
        deleteStudent = new Button("Удалить студента");

        editButton = new Button("Редактировать студента");
        setDisabledEditAndDeleteButtons();

        grid = new Grid();

        refreshGrid();
        addFilter();

        grid.removeColumn("ID");
        grid.setSizeFull();

        addNewStudent.addClickListener(clickEvent ->
        {
            AddNewStudentModalWindow modalWindow = new AddNewStudentModalWindow(this);
            modalWindow.setModal(true);
            UI.getCurrent().addWindow(modalWindow);
        });

        editButton.addClickListener(clickEvent -> {
            EditStudentModalWindow modalWindow = new EditStudentModalWindow(this);
            modalWindow.setModal(true);
            UI.getCurrent().addWindow(modalWindow);
        });

        deleteStudent.addClickListener(clickEvent -> {
            try (Connection connection = df.getConnection()) {
                GenericDao<Student> studentDao = df.getStudentDao(connection);
                studentDao.delete(selectedStudent);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            refreshGrid();
            deleteStudent.setEnabled(false);
        });

        grid.addItemClickListener(itemClickEvent ->
        {
            for (Student student : studentList) {
                if (student.getId().equals(itemClickEvent.getItem().getItemProperty("ID").getValue())) {
                    selectedStudent = student;

                    selectedStudentGroup = (Integer) itemClickEvent.getItem().getItemProperty("Номер группы").getValue();
                    break;
                }
            }
            deleteStudent.setEnabled(true);
            editButton.setEnabled(true);
        });


        HorizontalLayout buttonsLayout = new HorizontalLayout(addNewStudent, editButton, deleteStudent);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setSizeUndefined();

        VerticalLayout wholeLayout = new VerticalLayout(buttonsLayout, grid);
        wholeLayout.setSizeFull();
        wholeLayout.setSpacing(true);
        wholeLayout.setSizeFull();
        setCompositionRoot(wholeLayout);
    }

    void setDisabledEditAndDeleteButtons() {
        deleteStudent.setEnabled(false);
        editButton.setEnabled(false);
    }

    private void initContainer() {
        container = new IndexedContainer();
        container.addContainerProperty("Фамилия", String.class, null);
        container.addContainerProperty("Имя", String.class, null);
        container.addContainerProperty("Отчество", String.class, null);
        container.addContainerProperty("Дата рождения", String.class, null);
        container.addContainerProperty("Номер группы", Integer.class, null);
        container.addContainerProperty("ID", Long.class, null);
    }

    public void refreshGrid() {
        studentList = getStudentsList();
        List<Group> groupList = getGroupsList();
        fillContainer(studentList, groupList);
        grid.setContainerDataSource(container);
    }


    private void fillContainer(List<Student> studentList, List<Group> groupList) {
        container.removeAllItems();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (Student st : studentList) {
            Object itemId = container.addItem();
            Integer groupNumber = null;
            for (Group gr : groupList) {
                if (gr.getId().equals(st.getGroupId())) {
                    groupNumber = gr.getNumber();
                    break;
                }
            }
            container.removeAllContainerFilters();
            for (TextField tf : filterFields) {
                tf.setValue("");
            }
            container.getContainerProperty(itemId, "Фамилия").setValue(st.getSurname());
            container.getContainerProperty(itemId, "Имя").setValue(st.getName());
            container.getContainerProperty(itemId, "Отчество").setValue(st.getPatronymic());
            container.getContainerProperty(itemId, "Дата рождения").setValue(formatter.format(st.getBirthday()));
            container.getContainerProperty(itemId, "Номер группы").setValue(groupNumber);
            container.getContainerProperty(itemId, "ID").setValue(st.getId());
        }
    }

    List<Group> getGroupsList() {
        List<Group> groupList = null;
        try (Connection connection = df.getConnection()) {
            GenericDao<Group> groupDao = df.getGroupDao(connection); //TODO: cash?
            groupList = groupDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    private List<Student> getStudentsList() {
        List<Student> studentList = null;
        try (Connection connection = df.getConnection()) {
            GenericDao<Student> studentDao = df.getStudentDao(connection);
            studentList = studentDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    private void addFilter() {
        filterFields.clear();
        HeaderRow filterRow = grid.appendHeaderRow();
        grid.getContainerDataSource()
                .getContainerPropertyIds().stream().filter(pid -> pid.equals("Фамилия") || pid.equals("Номер группы")).forEach(pid -> {

            Grid.HeaderCell cell = filterRow.getCell(pid);
            TextField filterField = new TextField();
            filterFields.add(filterField);
            filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);
            filterField.setInputPrompt("Фильтр");
            filterField.addTextChangeListener(change -> {
                container.removeContainerFilters(pid);

                if (!change.getText().isEmpty())
                    container.addContainerFilter(
                            new SimpleStringFilter(pid,
                                    change.getText(), true, false));
            });
            cell.setComponent(filterField);
        });
    }

    Integer getSelectedStudentGroup() {
        return selectedStudentGroup;
    }

    DaoFactory getDf() {
        return df;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    Student getSelectedStudent() {
        return selectedStudent;
    }


}