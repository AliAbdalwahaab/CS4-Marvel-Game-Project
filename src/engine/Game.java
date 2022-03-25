package engine;

import model.abilities.*;
import model.effects.*;
import model.world.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean firstLeaderAbilityUsed;
    private boolean secondLeaderAbilityUsed;
    private Object[][] board;
    private static ArrayList<Champion> availableChampions;
    private static ArrayList<Ability> availableAbilities;
    private PriorityQueue turnOrder;
    final private static int BOARDHEIGHT = 5;
    final private static int BOARDWIDTH = 5;

    // Getters
    public Player getFirstPlayer() {
        return this.firstPlayer;
    }

    public Player getSecondPlayer() {
        return this.secondPlayer;
    }

    public boolean isFirstLeaderAbilityUsed() {
        return this.firstLeaderAbilityUsed;
    }

    public boolean isSecondLeaderAbilityUsed() {
        return this.secondLeaderAbilityUsed;
    }

    public Object[][] getBoard() {
        return this.board;
    }

    public ArrayList<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public PriorityQueue getTurnOrder() {
        return this.turnOrder;
    }

    public Game(Player first, Player second) throws Exception {
        this.firstPlayer = first;
        this.secondPlayer = second;
        this.board = new Object[BOARDHEIGHT][BOARDWIDTH];
        this.firstLeaderAbilityUsed = false;
        this.secondLeaderAbilityUsed = false;
        // loadAbilities("csv/Abilities.csv"); // not sure of path
        // loadChampions("csv/Champions.csv"); // not sure of path
        placeChampions();
        placeCovers();
        // What to do with turnOrder ?? load all champs or only chosen 6?
    }

    private void placeChampions() {
        ArrayList<Champion> team1 = firstPlayer.getTeam();
        ArrayList<Champion> team2 = secondPlayer.getTeam();
        for (int i = 0; i < 3; i++) {
            board[BOARDHEIGHT - 1][1 + i] = team1.get(i);
            board[0][1 + i] = team2.get(i);
        }
    }

    private void placeCovers() {
        Random r = new Random();
        int covers = 5;
        while(covers > 0) { // till covers reach 0 (5 covers placed)
            int x = r.nextInt(BOARDWIDTH);
            int y = 1 + r.nextInt(BOARDHEIGHT - 1);
            if (board[x][y] == null) {
                covers--;
                board[x][y] = new Cover(x, y);
            }
        }
    }

    // helper method to extract enums from strings
    private static AreaOfEffect fetchAreaEnum(String enm) {
        // SELFTARGET, SINGLETARGET, TEAMTARGET, DIRECTIONAL, SURROUND
        switch (enm) {
            case "SELFTARGET": return AreaOfEffect.SELFTARGET;
            case "SINGLETARGET": return AreaOfEffect.SINGLETARGET;
            case "TEAMTARGET": return AreaOfEffect.TEAMTARGET;
            case "DIRECTIONAL": return AreaOfEffect.DIRECTIONAL;
            case "SURROUND": return AreaOfEffect.SURROUND;
            default: return null;
        }
    }

    // helper method to produce an effect according to effect name
    private static Effect processEffect(String name, int duration) {
        switch (name) {
            case "Disarm" :
                return new Disarm(name, duration);
            case "Dodge" :
                return new Dodge(name, duration);
            case "Embrace" :
                return new Embrace(name, duration);
            case "PowerUp" :
                return new PowerUp(name, duration);
            case "Root" :
                return new Root(name, duration);
            case "Shield" :
                return new Shield(name, duration);
            case "Shock" :
                return new Shock(name, duration);
            case "Silence" :
                return new Silence(name, duration);
            case "SpeedUp" :
                return new SpeedUp(name, duration);
            case "Stun" :
                return new Stun(name, duration);
            default: return null;
        }
    }

    // helper method to fetch ability and return to champion constructor
    private static Ability fetchAbility(String name) {
        for (Ability a : availableAbilities) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public static void loadAbilities(String filePath) throws Exception {
        /*
        indices of data[] array for each line in Abilities.csv

        0     1     2         3          4             5
        Type, name, manaCost, castRange, baseCooldown, AreaOfEffect,
        6                       7                                    8
        requiredActionsPerTurn, damageAmount/healAmount/effect name, effect duration

        */

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";
        while (br.ready()) {
            line = br.readLine();
            String[] data = line.split(",");
            if (data[0].equals("CC")) { // Crowd Control Ability
                /* public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, Effect effect)
                 Effect(String name, int duration, EffectType type) */
                Effect efct = processEffect(data[7], Integer.parseInt(data[8]));
                CrowdControlAbility ccb = new CrowdControlAbility(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[4]),
                        Integer.parseInt(data[3]), fetchAreaEnum(data[5]), Integer.parseInt(data[6]) , efct);
                availableAbilities.add(ccb);

            } else if (data[0].equals("DMG")) { // Damage Ability
                /* DamagingAbility(String name, int cost, int baseCoolDown, int castRange,
                 AreaOfEffect area, int required, int damageAmount) */
                DamagingAbility dmgA = new DamagingAbility(data[1], Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[3]), fetchAreaEnum(data[5]),
                        Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                availableAbilities.add(dmgA);

            } else { // Heal Ability
                /* HealingAbility(String name, int cost, int baseCoolDown, int castRange,
                 AreaOfEffect area, int required, int healAmount) */
                HealingAbility HelA = new HealingAbility(data[1], Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[3]), fetchAreaEnum(data[5]),
                        Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                availableAbilities.add(HelA);
            }
        }
    }

    public static void loadChampions(String filePath) throws Exception{
        /*

        0     1     2      3     4        5      6            7
        Type, name, maxHP, mana, actions, speed, attackRange, attackDamage,
        8              9              10
        ability1 name, ability2 name, ability3 name

         */
        loadAbilities("csv/Abilities.csv"); // load to use when adding abilities to champs
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";
        while (br.ready()) {
            line = br.readLine();
            String[] data = line.split(",");
            if (data[0].equals("A")) { // Anti Hero
                AntiHero ah = new AntiHero(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                        fetchAbility(data[8]), fetchAbility(data[9]), fetchAbility(data[10]));
                availableChampions.add(ah);

            } else if (data[0].equals("H")) { // Hero
                Hero h = new Hero(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                        fetchAbility(data[8]), fetchAbility(data[9]), fetchAbility(data[10]));
                availableChampions.add(h);

            } else { // Villain
                Villain v = new Villain(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                        fetchAbility(data[8]), fetchAbility(data[9]), fetchAbility(data[10]));
                availableChampions.add(v);

            }
        }
    }


}
