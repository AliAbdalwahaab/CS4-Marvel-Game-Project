package controller;

import engine.Game;
import engine.Player;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.world.Champion;
import model.world.Direction;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

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



}
