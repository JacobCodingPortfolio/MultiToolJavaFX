package pl.com.logic;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JNartowicz
 */
public class FilesSearch {

    public void getResultFileSearch(String path, String extension, String search, LoggerHandle loggerHandle) throws Exception {

        File file = new File(path);
        if(file.exists()){
            if (file.isDirectory()){

            } else {
                throw new Exception("Nie wybrano folderu");
            }
        } else {
            throw new Exception("Nie poprawna ściezka");
        }

        //Pobranie plików w folderze
        File[] filesInFolder = file.listFiles();

        //Zrobienie listy plików o określonym formacie
        List<File> files = new ArrayList<File>();
        for(File fileInDirectory: filesInFolder){
            String extensionOfFile = FilenameUtils.getExtension(fileInDirectory.getName());
            if(extensionOfFile.equals(extension)){
                files.add(fileInDirectory);
            }
        }

        for(File fileWithSelExtension: files){
            List<Integer> rowsContains = getRowWithString(fileWithSelExtension, search);
            if(rowsContains.size() != 0){
                StringBuilder rowList = new StringBuilder();
                for(Integer intRow: rowsContains){
                    rowList.append(intRow + ", ");
                }
                String msgLog = "Plik " + FilenameUtils.getBaseName(fileWithSelExtension.getName()) + " zawiera podany tekst w wierszach: " + rowList;
                loggerHandle.logMessage(msgLog);
            }
        }
    }

    private List<Integer> getRowWithString(File fileToRead, String srch) throws IOException {
        List<Integer> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileToRead));
        try {
            int l = 1;
            String line = br.readLine();
            while (line != null) {
                if(line.toLowerCase().contains(srch.toLowerCase())){
                    rows.add(l);
                }
                line = br.readLine();
                ++l;
            }
        } finally {
            br.close();
            return rows;
        }
    }

    public interface LoggerHandle{
        void logMessage(String log);
    }

}













