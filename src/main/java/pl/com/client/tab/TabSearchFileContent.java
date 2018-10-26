package pl.com.client.tab;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pl.com.logic.FilesSearch;
import pl.com.logic.LoggerTextArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JNartowicz
 */
public class TabSearchFileContent extends TabBuilder {

    private static final int LIMIT = 50;
    private AnchorPane mainAnchorPane;
    private static List<String> logContainer = new ArrayList<>();
    private static List<String> logHistory = new ArrayList<>();
    private static Double progress = 0.0;

    private TextField filePath;
    private TextField fileExtension;
    private TextField fileSearch;
    private TextArea fileLogs;
    private Button search;
    private ProgressBar fileProgress;

    private static FilesSearch filesSearch;
    private static SearchTabLogger searchTabLogger;

    public TabSearchFileContent() {
        filesSearch = new FilesSearch();
        searchTabLogger = new SearchTabLogger();
        setControlsStyle();
        setActions();
    }

    private void setControlsStyle(){
        this.fileLogs.setEditable(false);
    }

    private void setActions() {
        this.search.setOnMouseClicked(event -> {
            try {
                filesSearch.getResultFileSearch(filePath.getText(), fileExtension.getText(), fileSearch.getText(), log -> {
                    //System.out.println(log);
                    TabSearchFileContent.this.fileLogs.setText(searchTabLogger.info(log));
                    System.out.println(searchTabLogger.info(log));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void insertLogger(String message){
        this.logContainer.add(message);
        this.refreshLogger();
    }

    public void clearLogContainer(){
        for(String s: logContainer){
            logHistory.add(s);
        }
        logContainer.clear();
    }

    private void refreshLogger(){
        this.fileLogs.setText(""); //Wyczysczenie
        StringBuilder builder = new StringBuilder();
        int limitCheck = 0;
        LOOP:
        for (int i = logContainer.size() - 1 ; i >= 0 ; i--) {
            builder.append(logContainer.get(i) + "\n");
            limitCheck++;
            if(limitCheck > LIMIT){
                break LOOP;
            }
        }
        this.fileLogs.setText(builder.toString());
    }

    public Map<Class<? extends Node>, String[]> getComponents() {
        Map<Class<? extends Node>, String[]> map = new HashMap<Class<? extends Node>, String[]>();
        map.put(TextField.class, new String[]{"filePath","fileExtension","fileSearch"});
        map.put(TextArea.class, new String[]{"fileLogs"});
        map.put(Button.class, new String[]{"search"});
        map.put(ProgressBar.class, new String[]{"fileProgress"});
        return map;
    }

    public String getMainAnchorPaneName() {
        return "fileMainAnchor";
    }

    public String getFxmlFileName() {
        return "fileContent.fxml";
    }

    private class SearchTabLogger extends LoggerTextArea {
        @Override
        public int getHistoryLimit() {
            return 20;
        }
    }

}










