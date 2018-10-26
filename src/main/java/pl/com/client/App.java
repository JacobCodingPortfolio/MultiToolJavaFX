package pl.com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import pl.com.client.tab.TabSearchFileContent;

/**
 * @author JNartowicz
 */
public class App extends Application {

    private static App app;
    private FXMLLoader loader;
    private Parent parent;
    private Scene mainScene;
    private Stage primaryStage;

    private TabPane mainTabPane;

    //Tabs
    private TabSearchFileContent tabSearchFileContent;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        this.parent = this.loader.load();
        this.mainScene = new Scene(this.parent);
        this.app = this;
        this.mainTabPane = (TabPane) getElementById("mainTabPane");
        buildTabs();
        this.primaryStage.setScene(this.mainScene);
        this.primaryStage.show();
    }

    private void buildTabs() {
        this.tabSearchFileContent = new TabSearchFileContent();
        this.tabSearchFileContent.setText("Wyszukaj zawartość");
        mainTabPane.getTabs().add(tabSearchFileContent);

    }

    public static App getApplication(){
        return app;
    }

    protected Object getElementById(String codeName){
        return this.loader.getNamespace().get(codeName);
    }

}
