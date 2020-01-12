package Models;

import android.util.Log;

/**
 * Stores attributes of a Monster: name, xp, lvl, lvlXp
 * Use printDebugInfo() to monitor attributes
 */
public class Monster {
    private String name;
    private int experience;
    private int level;
    private int experienceTilNextLevel;

    public Monster(String name) {
        this.name = name;
        experience = 0;
        level = 1;
        experienceTilNextLevel = 3;
    }

    public void setName(String name) {
       this.name = name;
    }

    public void addExperience(int points) {
        experience += points;
        if (experience >= experienceTilNextLevel) {
            int extraExp = experience - experienceTilNextLevel;
            levelUp();
            experience = extraExp;
        }
    }

    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getExperienceTilNextLevel() { return experienceTilNextLevel; }

    // prints information of instance to Console
    public void printDebugInfo() {
       Log.i("Monster.java",
               String.format( "name: %s, exp: %d, lvl: %d, lvlExp: %d",
               name, experience, level, experienceTilNextLevel));
    }

    private void levelUp() {
        level++;
        // experience required for the next level increases (very magical)
        experienceTilNextLevel =
                (int)Math.round( 0.04 * (level ^ 3) + 0.8 * (level ^ 2) + 2 * level);
    }

}
