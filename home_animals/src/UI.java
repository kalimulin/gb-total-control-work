import java.util.Map;
import java.util.Scanner;

public class UI {
    public String showMenu(Map<String, String> menu) {
        Scanner scanner = new Scanner(System.in);

        for (String s : menu.keySet()) {
            System.out.println(s + " - " + menu.get(s));
        }
        System.out.print("> ");

        String answer = scanner.next();

        if(menu.containsKey(answer)){
            return answer;
        }

        return "";
    }

    public int getInteger(String message){
        Scanner scanner = new Scanner(System.in);

        System.out.print(message);
        return scanner.nextInt();
    }

    public String getString(String message){
        Scanner scanner = new Scanner(System.in);

        System.out.print(message);
        return scanner.next();
    }
}
