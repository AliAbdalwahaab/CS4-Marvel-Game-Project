package engine;

import exceptions.AbilityUseException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import model.abilities.*;
import model.effects.*;
import model.world.*;
import java.awt.*;
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

    public int getBoardheight() {
        return BOARDHEIGHT;
    }

    public int getBoardwidth() {
        return BOARDWIDTH;
    }

    public ArrayList<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public PriorityQueue getTurnOrder() {
        return this.turnOrder;
    }

    public ArrayList<Champion> getAvailableChampions() {
        return this.availableChampions;
    }

    public Game(Player first, Player second) {
        this.firstPlayer = first;
        this.secondPlayer = second;
        this.board = new Object[BOARDHEIGHT][BOARDWIDTH];
        this.firstLeaderAbilityUsed = false;
        this.secondLeaderAbilityUsed = false;
        availableChampions = new ArrayList<>();
        availableAbilities = new ArrayList<>();
        placeChampions();
        placeCovers();
        turnOrder = new PriorityQueue(first.getTeam().size()+second.getTeam().size());
        prepareChampionTurns();
    }

    private void placeChampions() {
        ArrayList<Champion> team1 = firstPlayer.getTeam();
        ArrayList<Champion> team2 = secondPlayer.getTeam();
        if (team1.size() == 0 || team2.size() == 0) return;
        for (int i = 0; i < 3; i++) {
            board[BOARDHEIGHT - 1][1 + i] = team2.get(i);
            board[0][1 + i] = team1.get(i);
            team2.get(i).setLocation(new Point(BOARDHEIGHT - 1, 1 + i));
            team1.get(i).setLocation(new Point(0, i + 1));
        }
    }

    private void placeCovers() {
        Random r = new Random();
        int covers = 5;
        while (covers > 0) { // till covers reach 0 (5 covers placed)
            int x = r.nextInt(BOARDWIDTH);
            int y = 1 + r.nextInt(BOARDHEIGHT - 2);
            if (board[y][x] == null) {
                covers--;
                board[y][x] = new Cover(y, x);
            }
        }
    }

    // helper method to extract enums from strings
    private static AreaOfEffect fetchAreaEnum(String enm) {
        // SELFTARGET, SINGLETARGET, TEAMTARGET, DIRECTIONAL, SURROUND
        switch (enm) {
            case "SELFTARGET":
                return AreaOfEffect.SELFTARGET;
            case "SINGLETARGET":
                return AreaOfEffect.SINGLETARGET;
            case "TEAMTARGET":
                return AreaOfEffect.TEAMTARGET;
            case "DIRECTIONAL":
                return AreaOfEffect.DIRECTIONAL;
            case "SURROUND":
                return AreaOfEffect.SURROUND;
            default:
                return null;
        }
    }

    // helper method to produce an effect according to effect name
    private static Effect processEffect(String name, int duration) {
        switch (name) {
            case "Disarm":
                return new Disarm(duration);
            case "Dodge":
                return new Dodge(duration);
            case "Embrace":
                return new Embrace(duration);
            case "PowerUp":
                return new PowerUp(duration);
            case "Root":
                return new Root(duration);
            case "Shield":
                return new Shield(duration);
            case "Shock":
                return new Shock(duration);
            case "Silence":
                return new Silence(duration);
            case "SpeedUp":
                return new SpeedUp(duration);
            case "Stun":
                return new Stun(duration);
            default:
                return null;
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

    // check if Ability a1 is already in the availableAbilities arrayList
    /*
    private static boolean isDuplicate (Ability a1) {
        for (Ability a: availableAbilities){
            if (a.toString().equals(a1.toString())) {
                return true;
            }
        }
        return false;
    }
    //removed check since it has no use
    */

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
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals("CC")) { // Crowd Control Ability
                /* public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required, Effect effect)
                 Effect(String name, int duration, EffectType type) */
                Effect efct = processEffect(data[7], Integer.parseInt(data[8]));
                CrowdControlAbility ccb = new CrowdControlAbility(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[4]),
                        Integer.parseInt(data[3]), fetchAreaEnum(data[5]), Integer.parseInt(data[6]), efct);
                // if (isDuplicate(ccb)) continue;
                availableAbilities.add(ccb);

            } else if (data[0].equals("DMG")) { // Damage Ability
                /* DamagingAbility(String name, int cost, int baseCoolDown, int castRange,
                 AreaOfEffect area, int required, int damageAmount) */
                DamagingAbility dmgA = new DamagingAbility(data[1], Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[3]), fetchAreaEnum(data[5]),
                        Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                // if (isDuplicate(dmgA)) continue;
                availableAbilities.add(dmgA);

            } else { // Heal Ability
                /* HealingAbility(String name, int cost, int baseCoolDown, int castRange,
                 AreaOfEffect area, int required, int healAmount) */
                HealingAbility HelA = new HealingAbility(data[1], Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[3]), fetchAreaEnum(data[5]),
                        Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                // if (isDuplicate(HelA)) continue;
                availableAbilities.add(HelA);
            }
        }
    }

    public static void loadChampions(String filePath) throws Exception {
        /*

        0     1     2      3     4        5      6            7
        Type, name, maxHP, mana, actions, speed, attackRange, attackDamage,
        8              9              10
        ability1 name, ability2 name, ability3 name

         */
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";
        while (true) {
            line = br.readLine();
            if (line == null) break;
            String[] data = line.split(",");
            if (data[0].equals("A")) { // Anti Hero
                AntiHero ah = new AntiHero(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                ah.getAbilities().add(fetchAbility(data[8]));
                ah.getAbilities().add(fetchAbility(data[9]));
                ah.getAbilities().add(fetchAbility(data[10]));
                availableChampions.add(ah);

            } else if (data[0].equals("H")) { // Hero
                Hero h = new Hero(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                h.getAbilities().add(fetchAbility(data[8]));
                h.getAbilities().add(fetchAbility(data[9]));
                h.getAbilities().add(fetchAbility(data[10]));
                availableChampions.add(h);

            } else { // Villain
                Villain v = new Villain(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                v.getAbilities().add(fetchAbility(data[8]));
                v.getAbilities().add(fetchAbility(data[9]));
                v.getAbilities().add(fetchAbility(data[10]));
                availableChampions.add(v);
            }
        }
    }

    public void prepareChampionTurns(){

            for (int i = 0; i < 3; i++) {
                if(firstPlayer.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT) {
                turnOrder.insert(firstPlayer.getTeam().get(i));
            }
        }

            for (int i = 0; i < 3; i++) {
                if(firstPlayer.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT) {
                turnOrder.insert(secondPlayer.getTeam().get(i));
            }
        }
    }

    //Methods required for Milestone 2
    public Champion getCurrentChampion() {
        return (Champion) turnOrder.peekMin();
    }

    public void useLeaderAbility() throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException, AbilityUseException {
        Champion c = getCurrentChampion();

        //Check if current champion is a Leader
        if (firstPlayer.getLeader().compareTo(c) == 0 || secondPlayer.getLeader().compareTo(c) == 0) {
            //Check if current champion is Leader of first team
            if (firstPlayer.getLeader().compareTo(c) == 0) {
                //Check if leader ability already used
                if (!firstLeaderAbilityUsed) {
                    //Choose targets based on Hero Class
                    if (c instanceof Hero) {
                        ArrayList<Champion> targets = firstPlayer.getTeam();
                        c.useLeaderAbility(targets);

                    } else if (c instanceof Villain) {
                        ArrayList<Champion> targets = secondPlayer.getTeam();
                        c.useLeaderAbility(targets);

                    } else if (c instanceof AntiHero) {
                        ArrayList<Champion> targets = new ArrayList<Champion>();
                        for (int i = 0; i < firstPlayer.getTeam().size();i++) {
                            targets.add(firstPlayer.getTeam().get(i));
                        }

                        for (int i = 0; i < secondPlayer.getTeam().size();i++) {
                            targets.add(secondPlayer.getTeam().get(i));
                        }

                        targets.remove(c);
                        targets.remove(secondPlayer.getLeader());
                        c.useLeaderAbility(targets);

                    }
                    firstLeaderAbilityUsed = true;
                }
                else
                    throw new LeaderAbilityAlreadyUsedException("Leader Ability has already been used");
            }

            //Check if current champion is leader of second team
            else if (secondPlayer.getLeader().compareTo(c) == 0) {
                //Check if leader ability is already used
                if (!secondLeaderAbilityUsed) {
                    //Choose targets based on Hero Class
                    if (c instanceof Hero) {
                        ArrayList<Champion> targets = secondPlayer.getTeam();
                        c.useLeaderAbility(targets);

                    } else if (c instanceof Villain) {
                        ArrayList<Champion> targets = firstPlayer.getTeam();
                        try {
                            c.useLeaderAbility(targets);
                        } catch (AbilityUseException e) {
                            System.out.println("Cannot use leader ability at the moment");
                            return;
                        }
                    } else if (c instanceof AntiHero) {
                        ArrayList<Champion> targets = new ArrayList<Champion>();
                        for (int i = 0; i < firstPlayer.getTeam().size();i++) {
                            targets.add(firstPlayer.getTeam().get(i));
                        }

                        for (int i = 0; i < secondPlayer.getTeam().size();i++) {
                            targets.add(secondPlayer.getTeam().get(i));
                        }

                        targets.remove(c);
                        targets.remove(firstPlayer.getLeader());
                        c.useLeaderAbility(targets);

                    }
                    secondLeaderAbilityUsed = true;
                }
                else
                    throw new LeaderAbilityAlreadyUsedException("Leader Ability has already been used");

            }
            else
                throw new LeaderNotCurrentException("The Champion whose turn is currently taking place is not a leader");

        }

    }

}
