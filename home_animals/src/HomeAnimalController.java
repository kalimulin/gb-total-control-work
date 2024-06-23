import classes.Cat;
import classes.Dog;
import classes.Hamster;
import helpers.AnimalCounter;
import helpers.HomeAnimalList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class HomeAnimalController {
    private final HomeAnimalList<Object> homeAnimalList = new HomeAnimalList<>();
    private final UI ui = new UI();

    private final Map<String, String> mainMenu = new HashMap<String, String>() {{
        put("1", "Добавить домашнее животное");
        put("2", "Добавить команду для домашнего животного");
        put("3", "Отобразить список домашних животных");
        put("4", "Показать команды домашнего животного");
        put("5", "Показать кол-во домашних животных");
        put("0", "Выход");
    }};
    // меню второго уровня
    private final Map<String, String> homeAnimalsMenu = new HashMap<>() {{
        put("1", "Кот");
        put("2", "Собака");
        put("3", "Хомяк");
        put("0", "Отмена");
    }};

    private final Map<String, String> confirmMenu = new HashMap<>(){{
        put("1","Да");
        put("0","Нет");
    }};

    private enum ANIMALS {CAT, DOG, HAMSTER};

    public void Run() throws Exception {
        String menu;
        do {
            menu = selectOperation();

            switch (selectOperation()) {
                case "11" -> addHomeAnimal(ANIMALS.CAT);
                case "12" -> addHomeAnimal(ANIMALS.DOG);
                case "13" -> addHomeAnimal(ANIMALS.HAMSTER);
                case "21" -> addCommand(ANIMALS.CAT);
                case "22" -> addCommand(ANIMALS.DOG);
                case "23" -> addCommand(ANIMALS.HAMSTER);
                case "31" -> showAnimals(ANIMALS.CAT);
                case "32" -> showAnimals(ANIMALS.DOG);
                case "33" -> showAnimals(ANIMALS.HAMSTER);
                case "41" -> showAnimalCommands(ANIMALS.CAT);
                case "42" -> showAnimalCommands(ANIMALS.DOG);
                case "43" -> showAnimalCommands(ANIMALS.HAMSTER);
                case "5" -> showAnimalsCount();
            }
        } while (!(menu.isEmpty() || menu.equals("0")));
    }

    private void showAnimalsCount() throws Exception{
        try(AnimalCounter counter = new AnimalCounter()){
            Logger.getAnonymousLogger().info(counter.getCount().toString());
        }
    }

    private void showAnimalCommands(ANIMALS animal){
        String name = ui.getString("Имя животного: ");

        Object o = null;

        switch (animal){
            case CAT -> o = homeAnimalList.findCat(name);
            case DOG -> o = homeAnimalList.findDog(name);
            case HAMSTER -> o = homeAnimalList.findHamster(name);
        }

        if(o == null){
            Logger.getAnonymousLogger().info("Животное не найдено");
            return;
        }

        List<String> commands = null;

        switch (animal){
            case CAT -> commands = ((Cat)o).getCommandList();
            case DOG -> commands = ((Dog)o).getCommandList();
            case HAMSTER -> commands = ((Hamster)o).getCommandList();
        }

        StringBuilder strCommands = new StringBuilder();
        for (String c :commands) {
            strCommands.append(c).append(", ");
        }

        Logger.getAnonymousLogger().info(strCommands.toString());
    }

    private void showAnimals(ANIMALS animal){
        List<Object> animals = null;

        switch (animal){
            case CAT -> animals = homeAnimalList.getCats();
            case DOG -> animals = homeAnimalList.getDogs();
            case HAMSTER -> animals = homeAnimalList.getHamsters();
        }

        Logger logger = Logger.getAnonymousLogger();
        for (Object o : animals) {
            logger.info(o.toString());
        }
    }

    private void addCommand(ANIMALS animal){
        String name = ui.getString("Имя животного: ");
        Object objAnimal = null;
        switch (animal){
            case CAT -> objAnimal = homeAnimalList.findCat(name);
            case DOG -> objAnimal = homeAnimalList.findDog(name);
            case HAMSTER -> objAnimal = homeAnimalList.findHamster(name);
        }

        if(objAnimal == null){
            Logger.getAnonymousLogger().info("Такого животного нет");
        }
        else{
            String command = ui.getString("Новая команда: ");

            switch (animal){
                case CAT -> ((Cat)objAnimal).addCommand(command);
                case DOG -> ((Dog)objAnimal).addCommand(command);
                case HAMSTER -> ((Hamster)objAnimal).addCommand(command);
            }
        }

    }

    private void addHomeAnimal(ANIMALS animal) throws Exception {
        try (AnimalCounter counter = new AnimalCounter()) {
            counter.add();
        }

        String name = ui.getString("Имя: ");
        String color = ui.getString("Цвет: ");
        String date = ui.getString("Дата рождения: ");

        List<String> commands = new ArrayList<>();
        System.out.println("Добавить команды?");
        String menu = ui.showMenu(confirmMenu);
        while (menu.equals("1")){
            String command = ui.getString("Команда: ");
            commands.add(command);
            System.out.println("Продолжить?");
            menu = ui.showMenu(confirmMenu);
        }

        switch (animal){
            case CAT -> homeAnimalList.addAnimal(new Cat(name, color, date, commands));
            case DOG -> homeAnimalList.addAnimal(new Dog(name, color, date, commands));
            case HAMSTER -> homeAnimalList.addAnimal(new Hamster(name, color, date, commands));
        }
    }

    private String selectOperation() {
        String menu = ui.showMenu(mainMenu);
        if (!menu.isEmpty() && !menu.equals("0") && !menu.equals("5")) {
            return menu + ui.showMenu(homeAnimalsMenu);
        }

        return menu;
    }
}
