package StudentBase.UI;

import javax.servlet.annotation.WebServlet;

import StudentBase.UI.GroupUI.GroupUI;
import StudentBase.UI.StudentUI.StudentUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("StudentBase.MyAppWidgetset")
public class StartUI extends UI {


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        TabSheet ts = new TabSheet();

        StudentUI studentUI = new StudentUI("Управление студентами");
        GroupUI groupUI = new GroupUI("Управление группами");
        ts.setSizeFull();
        ts.addSelectedTabChangeListener(selectedTabChangeEvent -> { // переделать в eventfire?
            studentUI.refreshGrid();
            groupUI.refreshGrid();
        });
        ts.addComponents(groupUI, studentUI);

        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();

        vl.addComponent(ts);
        setContent(vl);
        setSizeFull();

    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = StartUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
