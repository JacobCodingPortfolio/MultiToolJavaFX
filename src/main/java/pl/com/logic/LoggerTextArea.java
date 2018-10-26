package pl.com.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JNartowicz
 */
public abstract class LoggerTextArea {

    private List<String> messages = new ArrayList<>();
    private List<String> messagesHistory = new ArrayList<>();

    public String info(String text){
        messagesHistory.add(text);

        if(messages.size() > getHistoryLimit()){
            messages.remove(0);
            messages.add(text);
        }

        StringBuilder builder = new StringBuilder();
        for(String s: messages){
            builder.append(s + "\n");
        }

        return builder.toString();
    }

    public abstract int getHistoryLimit();

}
