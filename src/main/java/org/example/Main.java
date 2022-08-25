package org.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final ScriptEngine graalEngine = new ScriptEngineManager().getEngineByName("graal.js");

    /*
     * @return - ['141421', '1421']['+'] | ['IV', 'I']['+']
     * */
    private static String greetingAndInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Привет, это калькулятор, он умеет выполнять следующие операции: +|-|*|/. Пример: 1+1, II - I");
        System.out.print(":> ");

        return scanner.nextLine();
    }

    /*
    * @param number - double number
    * */

    private static void end(Double number) {
        System.out.println("Ответ >>> " + number);
    }

    /*
     * @param line - IVI, IXVI, XVI
     * @return - 5, 15, 16
     * */
    private static String convertToArabic(String line) {
        String[] lineArray = line.split("[+/*-]");

        for (String i: lineArray) {
            try {
                i = i.replaceAll("\\s+", "");
                Integer.parseInt(i);
            } catch (NumberFormatException errorNumber) {
                int resoult = 0;
                String[] workLine = i.split("");
                for (int j = 0; j < workLine.length; j++) {
                    int numberUp = j;
                    if (!(numberUp+1 >= workLine.length)) numberUp++;

                    try {
                        resoult += Roman.valueOf(workLine[j] +
                                workLine[numberUp]).toInt();
                        j++;
                    } catch (IllegalArgumentException errorArgument) {
                        try {
                            resoult += Roman.valueOf(workLine[j]).toInt();
                        } catch (IllegalArgumentException errorArgument2) {
                            System.out.println("Вы ввели неверное значение: " + i);
                            line = "Ошибка";
                            break;
                        }

                    }
                }
                line = line.replaceAll(i, String.valueOf(resoult));
            }
        }
        return line;
    }


    /*
     * @param exp - ["4412412", "24241", "41412"]["+", "*"]
     * @return - 1008280704
     * */
    private static Double calc(String exp) throws ScriptException {
        // Да я использую жабаскрипт в жабе, я думаю один раз можно
        // Как никак являюсь жабаскрипт девелопером
        return Double.valueOf(graalEngine.eval(exp).toString());
    }

    public static void main(String[] args) throws ScriptException {
        // :3
        String line = convertToArabic(greetingAndInput());
        if (Objects.equals(line, "Ошибка")) return;
        end(calc(line));
    }
}