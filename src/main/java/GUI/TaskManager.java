package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TaskManager {

    private ArrayList<String> taskList = new ArrayList<>();
    int numProgrammeRunning = 0;

    public ArrayList<String> getTaskList() {
        return taskList;
    }

    public boolean checkWhiteList(ArrayList<String> whiteList) {
        updateTaskList();
        whiteList.retainAll(taskList);
        return whiteList.size() <= 0;
    }

    public void updateTaskList() {
        try {
            taskList.clear();

            // For test case
//            String findProcess = "chrome.exe";
            //String filenameFilter = "/nh /fi \"Imagename eq "+findProcess+"\"";

            String filenameFilter = "/FI \"Status eq Running\" /FO CSV";
            String tasksCmd = System.getenv("windir") + "/system32/tasklist.exe " + filenameFilter;

            Process p = Runtime.getRuntime().exec(tasksCmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;

            boolean firstline = true;
            String[] newLineArray;
            String programme;

            while ((line = input.readLine()) != null) {
                if (firstline) {
                    firstline = false;
                    continue;
                }

                newLineArray = line.split(",");
                programme = newLineArray[0];
                programme = programme.substring(programme.indexOf("\"") + 1);
                programme = programme.substring(0, programme.lastIndexOf("\""));

                taskList.add(programme);
                numProgrammeRunning += 1;
            }
            taskList.sort(String.CASE_INSENSITIVE_ORDER);
            input.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        TaskManager test = new TaskManager();
        test.updateTaskList();
    }
}
