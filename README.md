# Marvel: Ultimate War
This repository is created and optimized to serve as a code pool for the CS4 game project (A university course project written purely in Java). 

## Intro
Marvel: Ultimate War is a 2 player battle game. Each player picks 3 champions to form his team
and fight the other player’s team. The players take turns to fight the other player’s champions.
The turns will keep going back and forth until a player is able to defeat all of the other player’s
champions which will make him the winner of the battle.
During the battle, each player will use his champions to attack the opponent champions either
by using normal attacks or using special attacks/abilities. The battle takes place on a 5x5 grid.
Each cell in the grid can either be empty, or contain a champion or obstacle/cover. At the
beginning of the battle, each team will stand at one of the sides/edges of the grid as a starting
position.

## Champions
Champions are the fighters that each player will form his team from. Each champion will have
a certain type which influences how the champion deals damage to other types as well as how
much damage it will receive from them. The available types are :- 
- Heroes: they deal extra damage when attacking villains.
- Villains: they deal extra damage when attacking heroes.
- Anti-Heroes: when being attacked or attacking a hero or villain, the antihero will always
act as the opposite type. If attacking an antihero, damage is calculated normally.
