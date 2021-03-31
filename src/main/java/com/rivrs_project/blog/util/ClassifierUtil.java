package com.rivrs_project.blog.util;


import org.springframework.web.multipart.MultipartFile;

/*
    1. Кидаем csv в classifier 1 arg - путь к нашему csv, 2 arg - выходной csv
    2. Обновляем поля в строке из csv где ID = +suited_id+  isClassified = 'true', CurElementMark = '+suited_class+'
 */
public class ClassifierUtil {
    //TODO Change paths
    private String classifierPath = "C:/LushnikovDM-serverApp-test/classificator-danya/ClassClassifier/ClassClassifier/ClassClassifier.py";
    private String documentsPath = "C:/LushnikovDM-serverApp-test/blog/src/main/resources/documents/";
    private TerminalUtil console = new TerminalUtil();
    //TODO Добавить id пользователя
    //TODO Добавить id параграфа

    public void classify (String documentUUID){
        try {
            long start = System.nanoTime();
            String filePath = documentsPath + documentUUID + "/" + "result1.csv";
            String resultPath = documentsPath + documentUUID + "/" + "result3.csv";
            String commandForClassifier = "python " + classifierPath + " " + filePath + " " + resultPath;
            console.executeCommand(commandForClassifier);
            double elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
            System.out.println("Time of classifying: " + elapsedTimeInSec);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
