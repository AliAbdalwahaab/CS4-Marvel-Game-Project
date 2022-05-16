package engine;

import exceptions.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;
import static model.abilities.AreaOfEffect.SELFTARGET;

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
            case "SELFTARGET":
                return SELFTARGET;
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



    //Methods required for Milestone 2
    public void castAbility(Ability a) throws CloneNotSupportedException, NotEnoughResourcesException,AbilityUseException {
        Champion c = getCurrentChampion();
        //Check for exceptions
        if (a.getManaCost() > c.getMana() || c.getCurrentActionPoints() < a.getRequiredActionPoints()) {
            throw new NotEnoughResourcesException("The current champion does not have enough resources to cast this Ability");

        } else if (!c.getAppliedEffects().isEmpty()) {
            for (int i = 0; i < c.getAppliedEffects().size(); i++) {
                if (c.getAppliedEffects().get(i) instanceof Silence)
                    throw new AbilityUseException("The current champion cannot use this ability at this moment");
            }
        } else if (a.getCurrentCooldown() > 0 )
            throw new AbilityUseException("The current champion cannot use this ability at this moment");


        if (a.getCastArea() == AreaOfEffect.SURROUND) {
            ArrayList<Damageable> targets = this.getSurroundTargets(c, a);
            a.execute(targets);
            kill(targets);
        }

        else if (a.getCastArea() == AreaOfEffect.SELFTARGET)
        {
            //ArrayList<Damageable> targets = new ArrayList<Damageable>();
            //targets.add(c);
            //a.execute(targets);
            if (a instanceof HealingAbility) {
                int newHP = c.getCurrentHP() + ((HealingAbility) a).getHealAmount();
                c.setCurrentHP(newHP);
            } else if (a instanceof CrowdControlAbility) {
                Effect e = (Effect) (((CrowdControlAbility) a).getEffect().clone());
                c.getAppliedEffects().add(e);
                e.apply(c);
            }
        }

        else if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {

            ArrayList<Damageable> targets = new ArrayList<Damageable>();
            if (a instanceof HealingAbility) {
                if (firstPlayer.getTeam().contains(c)) {
                    for (Damageable target: firstPlayer.getTeam()) {
                        int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                abs(target.getLocation().y - c.getLocation().y);
                        if (a.getCastRange() <= manhattanDistance)
                            targets.add(target);
                    }

                } else {
                    for (Damageable target: secondPlayer.getTeam())
                    {
                        int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                abs(target.getLocation().y - c.getLocation().y);
                        if (a.getCastRange() <= manhattanDistance)
                            targets.add(target);
                    }
                }
            } else if (a instanceof DamagingAbility) {
                if (firstPlayer.getTeam().contains(c)) {
                    for (Damageable target: secondPlayer.getTeam()) {
                        int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                abs(target.getLocation().y - c.getLocation().y);
                        if (a.getCastRange() <= manhattanDistance)
                            targets.add(target);
                    }

                } else {
                    for (Damageable target: firstPlayer.getTeam())
                    {
                        int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                abs(target.getLocation().y - c.getLocation().y);
                        if (a.getCastRange() <= manhattanDistance)
                            targets.add(target);
                    }
                }
            } else if (a instanceof CrowdControlAbility) {
                if (((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF) {
                    if (firstPlayer.getTeam().contains(c)) {
                        for (Damageable target: firstPlayer.getTeam()) {
                            int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                    abs(target.getLocation().y - c.getLocation().y);
                            if (a.getCastRange() <= manhattanDistance)
                                targets.add(target);
                        }

                    } else {
                        for (Damageable target: secondPlayer.getTeam())
                        {
                            int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                    abs(target.getLocation().y - c.getLocation().y);
                            if (a.getCastRange() <= manhattanDistance)
                                targets.add(target);
                        }
                    }
                } else {
                    if (firstPlayer.getTeam().contains(c)) {
                        for (Damageable target: secondPlayer.getTeam()) {
                            int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                    abs(target.getLocation().y - c.getLocation().y);
                            if (a.getCastRange() <= manhattanDistance)
                                targets.add(target);
                        }

                    } else {
                        for (Damageable target: firstPlayer.getTeam())
                        {
                            int manhattanDistance = abs(target.getLocation().x - c.getLocation().x) +
                                    abs(target.getLocation().y - c.getLocation().y);
                            if (a.getCastRange() <= manhattanDistance)
                                targets.add(target);
                        }
                    }
                }
            }
            a.execute(targets);
            kill(targets);
        }
        int newChampionMana = c.getMana()-a.getManaCost();
        c.setMana(newChampionMana);
        c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
        a.setCurrentCooldown(a.getBaseCooldown());

    }

    public void castAbility(Ability a, int x, int y) throws NotEnoughResourcesException, AbilityUseException, InvalidTargetException, CloneNotSupportedException {
        Champion c = getCurrentChampion();
        byte team = (byte) 2;
        if (firstPlayer.getTeam().contains(c))
            team = 1;
        //Check for exceptions
        if (a.getManaCost() > c.getMana())
            throw new NotEnoughResourcesException("The current champion does not have enough resources to cast this Ability");

        else if (!c.getAppliedEffects().isEmpty()) {
            for (int i = 0; i < c.getAppliedEffects().size(); i++) {
                if (c.getAppliedEffects().get(i) instanceof Silence)
                    throw new AbilityUseException("The current champion cannot use this ability at this moment");
            }
        } else if (a.getCurrentCooldown() > 0 )
            throw new AbilityUseException("The current champion cannot use this ability at this moment");
        else if (a.getCastArea() != AreaOfEffect.SINGLETARGET)
            return;

        Object currentCell = board[y][x]; //assuming we don't switch the y and the x when invoking the method
        if (currentCell == null) {
            //deduct resources with no results
            int newChampionMana = c.getMana() - a.getManaCost();
            c.setMana(newChampionMana);
            a.setCurrentCooldown(a.getBaseCooldown());
            throw new InvalidTargetException("Cannot cast ability on an empty cell");
        } else {
            if (a instanceof DamagingAbility) {
                if (currentCell instanceof Cover) {
                    int newHP = ((Champion) currentCell).getCurrentHP() -
                            ((DamagingAbility) a).getDamageAmount();
                    ((Champion) currentCell).setCurrentHP(newHP);
                } else {
                    if (team == 1) {
                            if (firstPlayer.getTeam().contains(currentCell)) {
                                //deduct resources with no results (commented cuz unsure if I should put it or not)
                                //int newChampionMana = c.getMana() - a.getManaCost();
                                //c.setMana(newChampionMana);
                                //a.setCurrentCooldown(a.getBaseCooldown());
                                throw new InvalidTargetException ("Cannot cast a damaging ability on a friendly target");
                            }

                            else {
                                int newHP = ((Champion) currentCell).getCurrentHP() -
                                        ((DamagingAbility) a).getDamageAmount();
                                ((Champion) currentCell).setCurrentHP(newHP);
                            }
                    } else {
                        if (secondPlayer.getTeam().contains(currentCell)) {
                            //deduct resources with no results (commented cuz unsure if I should put it or not)
                            //int newChampionMana = c.getMana() - a.getManaCost();
                            //c.setMana(newChampionMana);
                            //a.setCurrentCooldown(a.getBaseCooldown());
                            throw new InvalidTargetException ("Cannot cast a damaging ability on a friendly target");
                        } else {
                            int newHP = ((Champion) currentCell).getCurrentHP() -
                                    ((DamagingAbility) a).getDamageAmount();
                            ((Champion) currentCell).setCurrentHP(newHP);
                        }
                    }
                }
            } else if (a instanceof HealingAbility) {
                    if (!(currentCell instanceof Cover)) {
                        if (team == 1) {
                            if (secondPlayer.getTeam().contains(currentCell)) {
                                //deduct resources with no results (commented cuz unsure if I should put it or not)
                                //int newChampionMana = c.getMana() - a.getManaCost();
                                //c.setMana(newChampionMana);
                                //a.setCurrentCooldown(a.getBaseCooldown());
                                throw new InvalidTargetException ("Cannot cast a healing ability on an enemy target");
                            } else {
                                int newHP = ((Champion) currentCell).getCurrentHP() +
                                        ((HealingAbility) a).getHealAmount();
                                ((Champion) currentCell).setCurrentHP(newHP);
                            }
                        } else {
                            if (firstPlayer.getTeam().contains(currentCell)) {
                                //deduct resources with no results (commented cuz unsure if I should put it or not)
                                //int newChampionMana = c.getMana() - a.getManaCost();
                                //c.setMana(newChampionMana);
                                //a.setCurrentCooldown(a.getBaseCooldown());
                                throw new InvalidTargetException ("Cannot cast a healing ability on an enemy target");
                            } else {
                                int newHP = ((Champion) currentCell).getCurrentHP() +
                                        ((HealingAbility) a).getHealAmount();
                                ((Champion) currentCell).setCurrentHP(newHP);
                            }
                        }
                    } else {
                        //deduct resources with no results
                        //int newChampionMana = c.getMana() - a.getManaCost();
                        //c.setMana(newChampionMana);
                        //a.setCurrentCooldown(a.getBaseCooldown());
                        throw new InvalidTargetException ("Cannot cast a healing ability on a Cover");
                    } //Removed most of this else part because it reduced the number of passed tests (DO NOT DELETE)
            } else if (a instanceof CrowdControlAbility) {
                if (!(currentCell instanceof Cover)) {
                    if (((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF) {
                        if (team == 1) {
                            if (secondPlayer.getTeam().contains(currentCell)) {
                                //deduct resources with no results (commented cuz unsure if I should put it or not)
                                //int newChampionMana = c.getMana() - a.getManaCost();
                                //c.setMana(newChampionMana);
                                //a.setCurrentCooldown(a.getBaseCooldown());
                                throw new InvalidTargetException ("Cannot cast a BUFF crowd control ability" +
                                        " on an enemy target");
                            } else {
                                Effect e = (Effect) (((CrowdControlAbility) a).getEffect().clone());
                                ((Champion) currentCell).getAppliedEffects().add(e);
                                e.apply((Champion) currentCell);
                            }
                        } else {
                            if (firstPlayer.getTeam().contains(currentCell)) {
                                //deduct resources with no results (commented cuz unsure if I should put it or not)
                                //int newChampionMana = c.getMana() - a.getManaCost();
                                //c.setMana(newChampionMana);
                                //a.setCurrentCooldown(a.getBaseCooldown());
                                throw new InvalidTargetException ("Cannot cast a BUFF crowd control ability" +
                                        " on an enemy target");
                            } else {
                                Effect e = (Effect) (((CrowdControlAbility) a).getEffect().clone());
                                ((Champion) currentCell).getAppliedEffects().add(e);
                                e.apply((Champion) currentCell);
                            }

                        }
                    } else {
                        if (team == 1) {
                            if (firstPlayer.getTeam().contains(currentCell)) {
                                //deduct resources with no results (commented cuz unsure if I should put it or not)
                                //int newChampionMana = c.getMana() - a.getManaCost();
                                //c.setMana(newChampionMana);
                                //a.setCurrentCooldown(a.getBaseCooldown());
                                throw new InvalidTargetException ("Cannot cast a DEBUFF crowd control ability" +
                                        " on a friendly target");
                            } else {
                                Effect e = (Effect) (((CrowdControlAbility) a).getEffect().clone());
                                ((Champion) currentCell).getAppliedEffects().add(e);
                                e.apply((Champion) currentCell);
                            }

                        } else {
                            if (secondPlayer.getTeam().contains(currentCell)) {
                                //deduct resources with no results (commented cuz unsure if I should put it or not)
                                //int newChampionMana = c.getMana() - a.getManaCost();
                                //c.setMana(newChampionMana);
                                //a.setCurrentCooldown(a.getBaseCooldown());
                                throw new InvalidTargetException ("Cannot cast a DEBUFF crowd control ability" +
                                        " on a friendly target");
                            } else {
                                Effect e = (Effect) (((CrowdControlAbility) a).getEffect().clone());
                                ((Champion) currentCell).getAppliedEffects().add(e);
                                e.apply((Champion) currentCell);
                            }
                        }
                    }
                } else {
                    //deduct resources with no results
                    //int newChampionMana = c.getMana() - a.getManaCost();
                    //c.setMana(newChampionMana);
                    //a.setCurrentCooldown(a.getBaseCooldown());
                    throw new InvalidTargetException ("Cannot cast a crowd control ability on a Cover");
                } //Removed most of this else part because it reduced the number of passed tests (DO NOT DELETE)
            }
        }

        if (currentCell != null) {
            ArrayList<Damageable> targets = new ArrayList<Damageable>();
            targets.add((Damageable) currentCell);
            kill(targets);
        }
        int newChampionMana = c.getMana() - a.getManaCost();
        c.setMana(newChampionMana);
        a.setCurrentCooldown(a.getBaseCooldown());
    }

    private ArrayList<Damageable> getSurroundTargets(Champion c, Ability a) {
        int theY = c.getLocation().x;
        int theX = c.getLocation().y;
        byte team = (byte) 2;
        if (this.firstPlayer.getTeam().contains(c))
            team = 1;

       ArrayList<Damageable> targets = new ArrayList<Damageable>();

       //i represents y
        // j represents x
       for (int i = theY-1; i <= theY+1;i++) {
           for (int j = theX -1;j <= theX+1;j++)
           {
               if (((i > 0 && i < BOARDHEIGHT) && (j > 0 && j < BOARDWIDTH))) { //i removed the the
                   // && (i != theY && j != theX) part because it fixed a lot of failed tests
                   Object currentCell = board[i][j];

                   //Adding targets assuming a is a DamagingAbility
                   if (a instanceof DamagingAbility) {
                       if (currentCell instanceof Cover)
                           targets.add((Damageable) currentCell);
                       else if (currentCell instanceof Champion) {
                           if (team == 1) {
                               if (secondPlayer.getTeam().contains(currentCell))
                                   targets.add((Damageable) currentCell);
                           }
                           else {
                               if (firstPlayer.getTeam().contains(currentCell))
                                   targets.add((Damageable) currentCell);
                           }
                       }
                   }

                   //Adding targets assuming a is a HealingAbility
                   else if (a instanceof HealingAbility) {
                       if (currentCell instanceof Champion) {
                           if (team == 1) {
                               if (firstPlayer.getTeam().contains(currentCell))
                                   targets.add((Damageable) currentCell);
                           }
                           else {
                               if (secondPlayer.getTeam().contains(currentCell))
                                   targets.add((Damageable) currentCell);
                           }
                       }
                   }

                   //Adding targets assuming a is a CrowdControlAbility
                   else {
                       if (currentCell instanceof Champion) {
                           if (team == 1) {
                                if ( ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF) {
                                    if (firstPlayer.getTeam().contains(currentCell))
                                        targets.add((Damageable) currentCell);
                                }
                                else {
                                    if (secondPlayer.getTeam().contains(currentCell))
                                        targets.add((Damageable) currentCell);
                                }

                           } else {
                               if ( ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF) {
                                   if (secondPlayer.getTeam().contains(currentCell))
                                       targets.add((Damageable) currentCell);
                               }
                               else {
                                   if (firstPlayer.getTeam().contains(currentCell))
                                       targets.add((Damageable) currentCell);
                               }
                           }
                       }
                   }
               }
           }
       }



       return targets;
    }
    public Champion getCurrentChampion() {
        // will remove current champion when turn ends
        return (Champion) turnOrder.peekMin();
    }

    //helper method to remove dead champions
    public void kill(ArrayList<Damageable> targets) {
        for (int i = 0; i < targets.size(); i++) {
            if (targets.get(i) instanceof Cover) {
                Cover co = (Cover) targets.get(i);
                if (co.getCurrentHP() == 0)
                {
                    int coverY = targets.get(i).getLocation().x;
                    int coverX = targets.get(i).getLocation().y;
                    this.board[coverX][coverY] = null;
                }
            }
            else
            {
                if (((Champion) targets.get(i)).getCondition() == Condition.KNOCKEDOUT ||
                        ((Champion) targets.get(i)).getCurrentHP() <= 0) {
                    int championY = targets.get(i).getLocation().x;
                    int championX = targets.get(i).getLocation().y;
                    this.board[championX][championY] = null;
                    if (firstPlayer.getTeam().contains(targets.get(i)))
                        firstPlayer.getTeam().remove(i);
                    else
                        secondPlayer.getTeam().remove(i);
                }

            }
        }
    }

    public void killChampion(ArrayList<Champion> targets) {
        for (int i = 0; i < targets.size(); i++) {
            if (((Champion) targets.get(i)).getCondition() == Condition.KNOCKEDOUT ||
            ((Champion) targets.get(i)).getCurrentHP() <= 0) {
                int championY = targets.get(i).getLocation().x;
                int championX = targets.get(i).getLocation().y;
                this.board[championX][championY] = null;
                if (firstPlayer.getTeam().contains(targets.get(i)))
                    firstPlayer.getTeam().remove(i);
                else
                    secondPlayer.getTeam().remove(i);
            }
        }
    }

    public void useLeaderAbility() throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException, AbilityUseException, CloneNotSupportedException {
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
                        killChampion(targets);


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
                        c.useLeaderAbility(targets);
                        killChampion(targets);

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

    public void prepareChampionTurns() {

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

    public void move(Direction d) {

    }

}
