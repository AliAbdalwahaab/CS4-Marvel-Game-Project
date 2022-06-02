package controller;

import engine.*;
import exceptions.*;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Direction;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import static model.abilities.AreaOfEffect.SINGLETARGET;

public class GameController {
    private Game game;

    public GameController(Game game) {
        //TODO
        this.game = game;
    }

    public void onMoveClicked(String direction) {

        if (direction.equals("UP")) {
            try {
                game.move(Direction.UP);
            } catch (UnallowedMovementException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            } catch (NotEnoughResourcesException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        } else if (direction.equals("DOWN")) {
            try {
                game.move(Direction.DOWN);
            } catch (UnallowedMovementException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            } catch (NotEnoughResourcesException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        } else if (direction.equals("RIGHT")) {
            try {
                game.move(Direction.RIGHT);
            } catch (UnallowedMovementException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            } catch (NotEnoughResourcesException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        } else if (direction.equals("LEFT")) {
            try {
                game.move(Direction.LEFT);
            } catch (UnallowedMovementException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            } catch (NotEnoughResourcesException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }

    }

    public void onAttackClicked(String direction) {
        Direction d;
        switch(direction) {
            case "UP": d = Direction.UP; break;
            case "DOWN": d = Direction.DOWN; break;
            case "LEFT": d = Direction.LEFT; break;
            case "RIGHT": d = Direction.RIGHT; break;
            default: d = Direction.UP;
        }
        try {
            game.attack(d);
        } catch (ChampionDisarmedException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        } catch (NotEnoughResourcesException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    public void onEndTurnClicked() {
        game.endTurn();
    }

    public void onCastAbilityClicked(Ability a, String direction) {
        if (a == null) {
            JOptionPane.showMessageDialog(null,"No ability selected.");
        } else {
            if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
                Direction d;
                switch(direction) {
                    case "UP": d = Direction.UP; break;
                    case "DOWN": d = Direction.DOWN; break;
                    case "LEFT": d = Direction.LEFT; break;
                    case "RIGHT": d = Direction.RIGHT; break;
                    default: d = Direction.UP;
                }
                try {
                    game.castAbility(a,d);
                } catch (NotEnoughResourcesException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (AbilityUseException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (InvalidTargetException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (CloneNotSupportedException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }

            } else if (a.getCastArea() == AreaOfEffect.SURROUND) {
                try {
                    game.castAbility(a);
                } catch (CloneNotSupportedException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (NotEnoughResourcesException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (AbilityUseException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }

            } else if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {
                try {
                    game.castAbility(a);
                } catch (CloneNotSupportedException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (NotEnoughResourcesException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (AbilityUseException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
            } else if (a.getCastArea() == AreaOfEffect.SELFTARGET) {
                try {
                    game.castAbility(a);
                } catch (CloneNotSupportedException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (NotEnoughResourcesException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                } catch (AbilityUseException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
            }
        }
    }
    public Object[][] getBoard() {
        return this.game.getBoard();
    }

    public Champion getCurrentChampion() {
        return game.getCurrentChampion();
    }

    public Player getPlayer1() {
        return game.getFirstPlayer();
    }

    public Player getPlayer2() {
        return game.getSecondPlayer();
    }

    public Champion getChamp(String name) {
        for (Champion c: game.getFirstPlayer().getTeam()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        for (Champion c: game.getSecondPlayer().getTeam()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }

    public Game getGame(){
        return this.game;
    }



}
