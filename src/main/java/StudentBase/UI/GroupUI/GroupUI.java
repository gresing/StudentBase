package StudentBase.UI.GroupUI;

import StudentBase.DAO.DaoFactory;
import StudentBase.DAO.GenericDao;
import StudentBase.DAO.HSQLDBDAO;
import StudentBase.Entities.Group;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


public class GroupUI extends CustomComponent {


    private DaoFactory df;
    private String caption;

    private Grid grid;
    private Button groupAddButton;
    private Button editGroupButton;
    private Button deleteGroupButton;

    private Group selectedGroup;


    public GroupUI(String caption) {
        this.caption = caption;
        df = new HSQLDBDAO();
        groupAddButton = new Button("Добавить группу");
        editGroupButton = new Button("Изменить группу");
        deleteGroupButton = new Button("Удалить группу");
        grid = new Grid();

        groupAddButton.addClickListener(e ->
        {
            AddNewGroupModalWindow modalWindow = new AddNewGroupModalWindow(this);
            modalWindow.setModal(true);
            UI.getCurrent().addWindow(modalWindow);
        });

        editGroupButton.setEnabled(false);
        editGroupButton.addClickListener(e ->
        {
            EditGroupModalWindow modalWindow = new EditGroupModalWindow(this);
            modalWindow.setModal(true);
            UI.getCurrent().addWindow(modalWindow);
        });

        deleteGroupButton.setEnabled(false);
        deleteGroupButton.addClickListener(e ->
        {
            try (Connection connection = df.getConnection()) {
                GenericDao<Group> groupDao = df.getGroupDao(connection);
                groupDao.delete(selectedGroup);
            } catch (SQLIntegrityConstraintViolationException ex) {
                Notification.show("Невозможно удалить группу, в которой есть студенты");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            refreshGrid();
            setEditAndDeleteButtonsDisabled();
        });

        refreshGrid();
        grid.setColumnOrder("number", "facultyName");
        grid.getColumn("number").setHeaderCaption("Номер группы");
        grid.getColumn("facultyName").setHeaderCaption("Название факультета");
        grid.removeColumn("id");

        grid.addItemClickListener(itemClickEvent ->
        {
            deleteGroupButton.setEnabled(true);
            editGroupButton.setEnabled(true);
            if (itemClickEvent.getItemId() instanceof Group)
                selectedGroup = (Group) itemClickEvent.getItemId();
        });

        VerticalLayout buttonLayout = new VerticalLayout(groupAddButton, editGroupButton, deleteGroupButton);

        HorizontalLayout wholeLayout = new HorizontalLayout(grid, buttonLayout);

        buttonLayout.setSpacing(true);
        wholeLayout.setSpacing(true);
        setCompositionRoot(wholeLayout);
    }


    public void refreshGrid() {
        List<Group> groupList = null;
        try (Connection connection = df.getConnection()) {
            GenericDao<Group> groupDao = df.getGroupDao(connection);
            groupList = groupDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Group> container = new BeanItemContainer<>(Group.class, groupList);
        grid.setContainerDataSource(container);
    }

    void setEditAndDeleteButtonsDisabled() {
        editGroupButton.setEnabled(false);
        deleteGroupButton.setEnabled(false);
    }

    @Override
    public String getCaption() {
        return caption;
    }

    DaoFactory getDf() {
        return df;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    Group getSelectedGroup() {
        return selectedGroup;
    }
}
