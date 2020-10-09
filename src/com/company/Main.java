package com.company;

import java.util.Scanner;

public class Main {
    static GameSite gameSite = new GameSite();

    public static void main(String[] args) {
        menuLesson1();
    }

    private static void menuLesson1() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Добавить игрока;");
            System.out.println("2. Добавить игру;");
            System.out.println("3. Добавляет рейтинг игроку;");
            System.out.println("4. Список игр, в которые играют все игроки на сайте;");
            System.out.println("5. Вывод рейтинга по имени игрока и игре;");
            System.out.println("6. Top 10 лучших игроков в определенной игре;");
            System.out.println("7. Top 10 лучших игроков с учетом всех игр;");
            selectedMenu(scanner.nextInt());
        }
    }

    private static void selectedMenu(int select) {
        Scanner scanner = new Scanner(System.in);

        switch (select) {
            case 1:
                System.out.print("Ник: ");
                gameSite.addPlayer(scanner.nextLine());
                break;
            case 2:
                System.out.print("Название игры: ");
                gameSite.addGame(scanner.nextLine());
                break;
            case 3:
                gameSite.addPlayerRating();
                break;
            case 4:
                gameSite.printPlayersGames();
                break;
            case 5:
                break;
            case 6:
                gameSite.printGameTopRating("login");
                break;
            case 7:
                gameSite.printTopPlayers();
                break;
        }
    }
}
