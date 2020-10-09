package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class GameSite {
    // Перечень игр
    private Set<String> games = new HashSet<>();
    // Статистика игрока: nickname -> игра -> кол-во побед
    private Map<String, Map<String, Integer>> statistics = new HashMap<>();

    public GameSite() {
    }

    public void addGame(String game) {
        games.add(game);
    }

    // регистрирует игроков в системе
    public void addPlayer(String nickname) {
        if (statistics.containsKey(nickname)) {
            System.out.println("Участник с таким ником уже зарегестрирован");
            return;
        }
        statistics.put(nickname, new HashMap<>());
    }

    // добавляет рейтинг игроку
    public void addPlayerRating() {
        List<String> playersNicks = new ArrayList<>(statistics.keySet());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите пользователя: ");
        for (int i = 0; i < playersNicks.size(); i++) {
            System.out.println((i + 1) + ". " + playersNicks.get(i) + ";");
        }
        int indexPlayer = scanner.nextInt() - 1;
        String nick = playersNicks.get(indexPlayer);
        // Добавление игр
        Map<String, Integer> gameMap = statistics.get(nick);
        List<String> gameList = new ArrayList<>(games);
        while (true) {
            System.out.println("Выберите игру:");
            for (int i = 0; i < gameList.size(); i++) {
                System.out.println((i + 1) + ". " + gameList.get(i) + ";");
            }
            System.out.println("0. Выход.");
            int gameIndex = scanner.nextInt();
            if (gameIndex == 0) return;
            System.out.print("Кол-во побед: ");
            // пересчет кол-ва побед
            int winCount = scanner.nextInt();
            String gameName = gameList.get(gameIndex - 1);
            if (gameMap.containsKey(gameName)) {
                winCount += gameMap.get(gameName);
            }
            gameMap.put(gameName, winCount);
            statistics.put(nick, gameMap);
        }
    }

    // выводит список игр, в которые играют все игроки на сайте
    public void printPlayersGames() {
        Set<String> tempAllGames = games;
        for (Map.Entry<String, Map<String, Integer>> m : statistics.entrySet()) {
            for (Map.Entry<String, Integer> stringIntegerEntry : m.getValue().entrySet()) {
                String gameName = stringIntegerEntry.getKey();
                if (!tempAllGames.contains(gameName)) tempAllGames.remove(gameName);
            }
        }
        String strGames = String.join(", ", tempAllGames);
        System.out.println("Общий список игр среди игроков: " + strGames);
    }

    // выводит рейтинг по имени игрока и игре
    public void printRating(String nickname, String game) {
        List<Map<String, Integer>> nickWinList = new ArrayList<>();

        // Получаем список формата Игрок/Выигрыш
        for (Map.Entry<String, Map<String, Integer>> statEntry : statistics.entrySet()) {
            String statNick = statEntry.getKey();

            if (statEntry.getValue().containsKey(game)) {
                Map<String, Integer> ma = new HashMap<>(1);
                Integer winGame = statEntry.getValue().get(game);
                ma.put(statNick, winGame);
                nickWinList.add(ma);
            }
        }

        // Фортировка по выигрышам
        nickWinList = nickWinList.stream()
                .sorted((o1, o2) -> {
                    List<Integer> values1 = (List<Integer>) o1.values();
                    List<Integer> values2 = (List<Integer>) o2.values();
                    return values1.get(0).compareTo(values2.get(0));
                })
                .collect(Collectors.toList());

        // Вывод рейтинга
        for (int i = 0; i < nickWinList.size(); i++) {
            Set<String> nick = nickWinList.get(i).keySet();
            if (nick.contains(nickname)) {
                System.out.println("Рейтинг = " + i + 1);
                return;
            }
        }
    }

    // выводит 10 лучших игроков в определенной игре
    public void printGameTopRating(String game) {
        final int MAX_SIZE = 10;
        List<Map<String, Integer>> nickWinList = new ArrayList<>();

        // Получаем список формата Игрок/Выигрыш
        for (Map.Entry<String, Map<String, Integer>> statEntry : statistics.entrySet()) {
            String statNick = statEntry.getKey();

            if (statEntry.getValue().containsKey(game)) {
                Map<String, Integer> ma = new HashMap<>(1);
                Integer winGame = statEntry.getValue().get(game);
                ma.put(statNick, winGame);
                nickWinList.add(ma);
            }
        }

        // Фортировка по выигрышам
        nickWinList = nickWinList.stream()
                .sorted((o1, o2) -> {
                    List<Integer> values1 = (List<Integer>) o1.values();
                    List<Integer> values2 = (List<Integer>) o2.values();
                    return values2.get(0).compareTo(values1.get(0));
                })
                .limit(MAX_SIZE)
                .collect(Collectors.toList());

        // Вывод рейтинга
        for (int i = 0; i < nickWinList.size(); i++) {
            String nick = nickWinList.get(i)
                    .keySet()
                    .toArray(new String[]{})[0];
            System.out.println((i + 1) + ". " + nick);
        }
    }

    // выводит 10 лучших игроков с учетом всех игр
    public void printTopPlayers() {
        final int MAX_SIZE = 10;
        List<Map<String, Integer>> nickWinList = new ArrayList<>();

        // Получаем список формата Игрок/Выигрыш
        for (Map.Entry<String, Map<String, Integer>> statEntry : statistics.entrySet()) {
            String statNick = statEntry.getKey();
            Integer countWins = 0;

            for (Map.Entry<String, Integer> gameInfoEntry : statistics.get(statNick).entrySet()) {
                countWins += gameInfoEntry.getValue();
            }
            Map<String, Integer> asd = new HashMap<>(1);
            asd.put(statNick, countWins);
            nickWinList.add(asd);
        }

        // Фортировка по выигрышам
        nickWinList = nickWinList.stream()
                .sorted((o1, o2) -> {
                    List<Integer> values1 = (List<Integer>) o1.values();
                    List<Integer> values2 = (List<Integer>) o2.values();
                    return values2.get(0).compareTo(values1.get(0));
                })
                .limit(MAX_SIZE)
                .collect(Collectors.toList());

        // Вывод рейтинга
        for (int i = 0; i < nickWinList.size(); i++) {
            Map<String, Integer> nickWinsMap = nickWinList.get(i);
            String nick = nickWinsMap
                    .keySet()
                    .toArray(new String[]{})[0];
            Integer wins = nickWinsMap.get(nick);
            System.out.println((i + 1) + ". " + nick + " побед: " + wins);
        }
    }
}
