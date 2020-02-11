package flashcards;
 
import java.util.*;
import java.io.*;
 
public class Main {
 
    static Map<String, String> map = new LinkedHashMap<>();
    static Map<String, Integer> mapMistakes = new HashMap<>();
    static Map<String, String> mapRev = new LinkedHashMap<>();
    static LinkedList<String> log = new LinkedList<>();
    static Scanner scanner = new Scanner(System.in);
    static String exportFile = "";
    static String importFile = "";
 
    public static void main(String[] args) {
 
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
 
                if (args[i].equals("-export")) {
                    exportFile = args[i+1];
                } else if (args[i].equals("-import")){
                    importFile = args[i+1];
                    initImport(importFile);
                }
 
            }
        }
 
 
        menu();
 
    }
 
    public static void menu() {
        while (true) {
            logOutput("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String choice = scanner.nextLine();
            logInput(choice);
            switch (choice) {
                case "exit":
                    logOutput("Bye bye!");
                    if (!exportFile.equals("")) {
                        exitExport(exportFile);
                    }
 
                    return;
                case "log":
                    saveLog();
                    break;
                case "hardest card":
                    showHardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                case "add":
                    add();
                    break;
                case "remove":
                    remove();
                    break;
                case "import":
                    importFromFile();
                    break;
                case "export":
                    exportToFile();
                    break;
                case "ask":
                    ask();
                    break;
                default:
            }
        }
    }
 
    public static void showHardestCard() {
        if (mapMistakes.isEmpty()) {
            logOutput("There are no cards with errors.");
            return;
        }
        int max = 0;
        for (int value : mapMistakes.values()) {
            if (value > max) {
                max = value;
            }
        }
        List<String> hardest = new ArrayList<>();
        for (Map.Entry<String, Integer> elem : mapMistakes.entrySet()) {
            if (elem.getValue() == max) {
                hardest.add(elem.getKey());
            }
        }
        StringBuilder msg = new StringBuilder("The hardest card ");
        if (hardest.size() == 1) {
            msg.append("is \"").append(hardest.get(0)).append("\". You have ").append(max).append(" errors answering it.");
        } else {
            msg.append("are ");
            for (int i = 0; i < hardest.size(); i++) {
                msg.append("\"").append(hardest.get(i)).append("\"");
                if (i < hardest.size() - 1) {
                    msg.append(", ");
                }
            }
            msg.append(". You have ").append(max).append(" errors answering them.");
        }
        logOutput(msg.toString());
    }
 
    public static void resetStats() {
        mapMistakes.clear();
        logOutput("Card statistics has been reset.");
    }
 
    public static void logOutput(String... data) {
        for (String s : data) {
            System.out.println(s);
            log.add(s);
        }
    }
 
    public static void logInput(String... data) {
        for (String s : data) {
            log.add("> " + s);
        }
    }
 
    public static void saveLog() {
        logOutput("File name:");
        String fileName = scanner.nextLine();
        logInput(fileName);
        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(file)) {
            for (String s : log) {
                writer.println(s);
            }
        } catch (Exception e) {
            logOutput("Error while saving.");
        }
        logOutput("The log has been saved.");
    }
 
    public static void exitExport(String filename) {
 
        File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(file)) {
            int count = 0;
            for (Map.Entry<String, String> element : map.entrySet()) {
                writer.println(element.getKey());
                writer.println(element.getValue());
                writer.println(mapMistakes.getOrDefault(element.getKey(), 0));
                count++;
            }
            logOutput("" + count + " cards have been saved.");
        } catch (Exception e) {
            logOutput("Error while saving.");
        }
    }
 
    public static void exportToFile() {
        logOutput("File name:");
        String filename = scanner.nextLine();
        logInput(filename);
        File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(file)) {
            int count = 0;
            for (Map.Entry<String, String> element : map.entrySet()) {
                writer.println(element.getKey());
                writer.println(element.getValue());
                writer.println(mapMistakes.getOrDefault(element.getKey(), 0));
                count++;
            }
            logOutput("" + count + " cards have been saved.");
        } catch (Exception e) {
            logOutput("Error while saving.");
        }
    }
 
    public static void initImport (String filename) {
 
        File file = new File(filename);
        try (Scanner fscan = new Scanner(file)) {
            int count = 0;
            while (fscan.hasNextLine()) {
                String card = fscan.nextLine();
                String def = fscan.nextLine();
                int mistakes = Integer.parseInt(fscan.nextLine());
                map.put(card, def);
                mapRev.put(def, card);
                if (mistakes > 0) {
                    mapMistakes.put(card, mistakes);
                }
                count++;
            }
            logOutput("" + count + " cards have been loaded.");
        } catch (Exception e) {
            logOutput("File not found.");
        }
 
 
    }
 
    public static void importFromFile() {
        logOutput("File name:");
        String filename = scanner.nextLine();
        logInput(filename);
        mapMistakes.clear();
        File file = new File(filename);
        try (Scanner fscan = new Scanner(file)) {
            int count = 0;
            while (fscan.hasNextLine()) {
                String card = fscan.nextLine();
                String def = fscan.nextLine();
                int mistakes = Integer.parseInt(fscan.nextLine());
                map.put(card, def);
                mapRev.put(def, card);
                if (mistakes > 0) {
                    mapMistakes.put(card, mistakes);
                }
                count++;
            }
            logOutput("" + count + " cards have been loaded.");
        } catch (Exception e) {
            logOutput("File not found.");
        }
    }
 
    public static void remove() {
        logOutput("The card: ");
        String card = scanner.nextLine();
        logInput(card);
        if (map.containsKey(card)) {
            String value = map.get(card);
            map.remove(card);
            mapRev.remove(value);
            mapMistakes.remove(card);
            logOutput("The card has been removed.");
        } else {
            logOutput("Can't remove \"" + card + "\": there is no such card.");
        }
    }
 
    public static void ask() {
        logOutput("How many times to ask?");
        int num = Integer.parseInt(scanner.nextLine());
        logInput(String.valueOf(num));
        Object[] crunchifyKeys = map.keySet().toArray();
        for (int i = 0; i < num; i++) {
            String key = (String) crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
            String value = map.get(key);
            logOutput("Print the definition of \"" + key + "\": ");
            String answer = scanner.nextLine();
            logInput(answer);
            if (value.equals(answer)) {
                logOutput("Correct answer.");
            } else {
                String msg;
                if (map.containsValue(answer)) {
                    msg = "Wrong answer. The correct one is \"" + value + "\"," +
                            " you've just written the definition of \"" + mapRev.get(answer) + "\".";
                } else {
                    msg = "Wrong answer. The correct one is \"" + value + "\". ";
                }
                int mistakeCount = 1;
                if (mapMistakes.containsKey(key)) {
                    mistakeCount = mapMistakes.get(key) + 1;
                }
                mapMistakes.put(key, mistakeCount);
                logOutput(msg);
            }
        }
 
    }
 
    public static void add() {
        logOutput("The card: ");
        String card = scanner.nextLine();
        logInput(card);
        if (map.containsKey(card)) {
            logOutput("The card \"" + card + "\" already exists.");
            return;
        }
        logOutput("The definition of the card:");
        String def = scanner.nextLine();
        logInput(def);
        if (mapRev.containsKey(def)) {
            logOutput("The definition \"" + def + "\" already exists.");
            return;
        }
        map.put(card, def);
        mapRev.put(def, card);
        logOutput("The pair (\"" + card + "\":\"" + def + "\") has been added.");
    }
}