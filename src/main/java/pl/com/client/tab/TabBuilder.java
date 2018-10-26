package pl.com.client.tab;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * @author JNartowicz
 */
public abstract class TabBuilder extends Tab {

    private FXMLLoader loader;
    private AnchorPane tabAnchorPane;

    public TabBuilder() {
        this.loader = new FXMLLoader(getClass().getResource("/" + getFxmlFileName()));
        try {
            this.tabAnchorPane = this.loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setContent(this.tabAnchorPane);
        this.buildComponents();
    }

    public abstract Map<Class<? extends Node>, String[]> getComponents();
    public abstract String getMainAnchorPaneName();
    public abstract String getFxmlFileName();

    protected Object getElementById(String codeName){
        return this.loader.getNamespace().get(codeName);
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public AnchorPane getTabAnchorPane() {
        return tabAnchorPane;
    }

    public void buildComponents(){
        Map<Class<? extends Node>, String[]> components = getComponents();
        Iterator<Class<? extends Node>> classIterator = components.keySet().iterator();
        while (classIterator.hasNext()){
            Class<? extends Node> aClass = classIterator.next();
            String[] comps = components.get(aClass);
            for(String s: comps){
                try {
                    TabBuilder object = TabBuilder.this;
                    Field f1 = this.getClass().getDeclaredField(s);
                    f1.setAccessible(true);
                    try {
                        f1.set(object, getElementById(s));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    //this.getClass().getDeclaredField(s).getType();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}





















