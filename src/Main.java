import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    final static int amountOfTrials = 300000;
    final static double cargoConstant = 1.2;   // Strategic value of having one more cargo
    final static double matchTime = 15;        // Match time in seconds
    final static int tactAmount = 15;    // This is how many tacts there are in one match. IF YOU ARE CHZNGING THIS< ALSO CHANGE IN CELL.JAVA
    final static double wallConstant = 0.05;    // The greater is this constant, the more points robot loses for bumping into a wall

    public static double probabilisticConstant = 1.2;   // This determines how much influence probability vector has.
                                                        // See Robot.probabilisticAcceleration for more


    static Cell[][] field = {        // w = Wall/obstacle;    l = Loading station;    s = suitable to Shoot;     i = Initiation line
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("l"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("l"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("l"), new Cell(" "), new Cell("l"), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("i"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell("s"), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell(" "), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
            {new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w"), new Cell("w")},
    };


    /**
     * This whole thing is a mini-AI for picking the best starting point, based on Q-Learning.
     * In order to explain how it works, let's first see the initial state of the system.
     *
     * Imagine a probability line broken into field.length equal intervals. Each interval corresponds to some point on the initiation line.
     * Let's pick an arbitrary point on the interval. Wherever this point falls, we will take this cell as a starting cell. So, initially all points have equal chances
     * to be picked.
     * After the finish of program run, each point gets rewarded according to how much points it scored. That part of the program is outside this method. But what is important
     * is that the greater score the sequence starting from some cell yielded, the greater reward it gets. The parameter that determines how much that point on average gets rewarded will
     * be further called value because it determines how valuable is each cell on the initiation line as a starting point.
     *
     * Now, what does this have to do with a random line? I said that all segments have an equal length (initially it's 1). Now, after we ran the first trial, the first point got
     * reevaluated. If it is successful, let's make its segment longer. If it isn't - that segment should be shortened. So, this segment is a "graphic" representation of value
     * Now when we are picking a point on the line at random, we get a greater chance that this point is on a particular segment if it was a successful run, and if it wasn't - the chance for that
     * point to fall onto this segment decreases.
     *
     * After a large number of trials, each starting cell gets evaluated fairly, meaning its value represents how likely a robot is to win starting from that cell.
     *
     *
     * NOTE IN CASE when NOT ALL of the INITIATION CELLS ON THE FIELD ARE ALLIGNED HORIZONTALLY:
     * You will have to turn initLineValues into an ArrayList <Cell> instead of being an array. This is an easy fix. All the arrays that are related to
     * StartLines will also have to be turned into such ArrayLists.
     * DO NOT turn initCell arrays into 2D arrays. You will get a slower program. I don't want this, U don't want this, no one wants this. Don't make yourself and the drive team unhappy
     * If that is a 2D array of cells, then instead of being O(n) this method will become O(n^2). This will happen due to running around the whole field where no more than 5% of the
     * cells can be initialline cells. This is rudely inefficient. Don't. Just use ArrayList
     *
     */
    public static Cell pickStartingPosition (int startRowNumber, double[] initLineValues, double totalInitLineValue) {  // This returns a cell where the robot should start.
        Random rand = new Random();

        double randomInitLineValue = rand.nextDouble()*totalInitLineValue;      // This variable picks some value from 0 to sum of all initLine values. This is a random point on the number line

        if (randomInitLineValue == totalInitLineValue || randomInitLineValue == 0) {
            System.out.println("\tAlert: bad starting position" + (randomInitLineValue == 0 ? initLineValues.length - 1 : 0));
        }


        int initCellXCoord;

        // We want to keep subtracting cell values from the totalInitLineValue one by one until we hit randomInitLineValue. This will be the moment when we hit the
        // cell that corresponds to that point on that interval.
        for (initCellXCoord = initLineValues.length - 1; initCellXCoord > 1 && randomInitLineValue > 0.0005; randomInitLineValue -= initLineValues[initCellXCoord]) {
            initCellXCoord--;
        }

        // Now whe the loop reached the coordinate, it is the one we start from.

        // System.out.println(initCellXCoord);
        if (initCellXCoord < 4 || initCellXCoord > initLineValues.length - 4) { // You can trace a dangerous situation here
            // If the compiler got into this branch, it means it picked a wall as a starting point. That's bad - robot cannot start there.
            // Yet, nothing critical. That can never become a part of the output of the program. The reason is, such sequence is doomed
            // to get immediately cut down at the first attempt to move the robot: the program will assume the bot bumped into a wall.
            System.out.println("Look closer");  // So, it is enough to just print something. If you get this message too often, something's messed up. It means that somehow
            // wall values got high enough to cause obot to start from the walls. These values can not ever get rewarded bc the sequence has
            // to have length of 1 that immediately terminates at this wall
        }
        return field[startRowNumber][initCellXCoord];   //Returns the cell on the field
    }


    /**
     *
     * Now that you know how the startCell-picking AI works, you shouldn't have troubles understanding this large AI. It is based on the same approach.
     * The main difference - apart from the field being 2-dimensional - is that instead of just havig some scalar values for each cell, we want each value to be a vector.
     * This vector will point in the direction of the path from there that proved itself to be on average most successful.
     * So, when you got a good score, the avg vector of the particular cell's scores will get added a large addend that is directed where the robot went from the cell.
     * In case the trial yielded a bad score, a smaller addend will be added, and the average will point less in that direction.
     *
     * Everything the rest is the same as in the method that picks a starting cell.
     */
    public static void main(String[] args) throws Exception {
        // This is an array for counting all nonzero cells.
        boolean [][] valueIsZero = new boolean[field.length][field[0].length];

        // Setting of all values of coordinates for the cells; also sets up zero value array.
        for (int i = 0; i < field[0].length; ++i) {
            for (int j = 0; j < field.length; ++j) {
                field[j][i].x = i;
                field[j][i].y = j;
                valueIsZero[j][i] = true;
            }
        }

        int initLine = 0;              // This needs to be outside of the loop, in order to be accessible later
        for (; initLine < field.length && field[initLine][field[0].length/2].type != "i"; ++initLine);  // This finds initLine


        double bestScore = -1;      // This stores the best achieved score out of all trials
        ArrayList<RobotSequenceRecord> bestSequence = new ArrayList<>();   // This stores the sequence that achieved best possible score
        String outputOfBestSequence = "";       // This stores the output of the best sequence
        long totalScore = 0;            // This scores the total of all scores up to the current trial
        double totalValue = 0;          // This stores total (NOT net) value of all cells.
        int numberOfBestScores = 1;     // This stores how many times the best score was achieved


        totalValue = 0.0;


        // The following is an array storing data of how valuable each cell on the initiation line is for being the starting cell.
        double[] initLineValues = new double[field[initLine].length];

        // Assigns initial values to the initLine. These are just scalars that indicate
        // the likelihood of getting many points if started the game from the respective cell
        for (int i = 1; i < initLineValues.length - 1; ++i) {
            initLineValues[i] = 1;
        }
        // First and last 3 cells are walls => the robot should not consider starting from there, so make their values zeros.
        for (int i = 0; i < 3; ++i) {
            initLineValues[i] = 0;
            initLineValues[initLineValues.length - i - 1] = 0;
        }

        // Total of all init. line values
        double totalInitLineValue = initLineValues.length - 2;



        AlliedRobot[] allies = new AlliedRobot[2];
        allies[0] = new AlliedRobot(field, "4290", 3, 1);
        allies[1] = allies[0];

        for (int trial = 1; trial <= amountOfTrials; ++trial) {
            for (int i = 0; i < trial % 20; ++i)
                System.out.print('_');
            System.out.println("\n\n\t\tTrial: L-" + (amountOfTrials - trial));
            if (trial % 200000 == 1) {
                    // In order to prevent the initLineValues from slipping completely into an uneven system that prevents new strategies from appearing, we need to
                    // once in a while null them all
                    for (int i = 1; i < initLineValues.length - 1; ++i)
                    initLineValues[i] = 1;

                totalInitLineValue = initLineValues.length - 6;
                for (int i = 0; i < 3; ++i) {
                    initLineValues[i] = 0;
                    initLineValues[initLineValues.length - i - 1] = 0;
                }
            }

            // The following block is to calculate the total amount of nonzero-valued cells.
            // It will be used for calculating the avg field value among nonzero cells
            int totalNonzeroValues = 0;
            for (boolean[] i : valueIsZero) {
                for (boolean j : i) {
                    if (!j) totalNonzeroValues++;
                }
            }

            // Creating the robot. The long expression of rand.nextInt(...) picks random cell except for the first and last one.
            Robot robot = new Robot (field, pickStartingPosition(initLine, initLineValues,
                    totalInitLineValue), 20, 1, 3, tactAmount);
            double increment = matchTime/tactAmount; // This is an increment of time each "turn"

            robot.move(increment, probabilisticConstant,  totalValue/(totalNonzeroValues + 1), tactAmount, allies);


            double score = cargoConstant*3 + 5;          // This is a running score
            String output = "";

            //Здесь должно быть начисление очков
            double[] scoreMarks = new double[robot.sequence.size()];    // This array stores how much of a score did each cell produce for the total
                                                                // The idea is, if the cell was visited before or at the moment when some part of the score was gained,
                                                    // then it means it commited to that score. Cells that were visited later than the score increased don't get reward for the points
            double maxScore = score;        // Max Score is for that exact purpose. This stores the maximum score that has been reached up to the moment. See below inside the loop
            robot.sequence.add(new RobotSequenceRecord(new Cell(0, 0), new Vector(0, 0), robot.cargo)); // This is used for avoiding the error of getting outside of array boundaries

            for (int i = robot.sequence.size() - 2; i >= 0; i--) {  // We go DOWN from the top. This way we keep track of the scores and avoid assigning scores to
                                                                    // the cells that have nothing to deal with how the score was gained

                Cell currCell = robot.sequence.get(i).cell;     // Stores current cell
                int cargo = robot.sequence.get(i).cargo;        // Stores cargo that the robot had when was at currCell

                int prevCargo = robot.sequence.get(i+1).cargo;  // Stores cargo that the robot had at the next cell after currCell. It is prevCargo bc we
                                                                // are going backwards, and it's previous on our way from last member of the sequence to the first

                output = ("(" + currCell.x + "; " + currCell.y + ") -->\n").concat(output);

                // If the robot is on the loading kind of cell, and there is enough space to load, it should do that. Cargo increases by 1.
                if (cargo < prevCargo) {    // If the cargo decreased in comparison to the prevCell, it means it loaded (think backwards)
                    output = "l ".concat(output);
                    score += cargoConstant;         // Increase score by the strategic value of having a cargo
                }
                else while (currCell.type.equals("s") && cargo > 0) {    // If it had cargo to shoot with and was at a shooting place, it sjot
                                           // It would always keep shooting until shot everything, so it is a while, not if.
                    output = "s ".concat(output);
                    score += 3 - cargoConstant; // Score increased by 3 but decreased by the strategic value of having one more cargo.
                                                // This is what we need maxScore for: if we look at the next cell in the sequence, it will now yield cargoConst less points
                                                // despite that cargo was later filled up again to some extent (that appears in the formula when score is initialized).
                                                // So, the next cell shouldn't get awarded less points if the cargo was later reloaded, and each cell gets awarded not score
                                                // at the particular moment, but the greatest score out of those that were achieved after that cell was visited
                    cargo --;
                }
                maxScore = Math.max(((double)Math.round(score*10))/10, maxScore);
                scoreMarks[i] = maxScore;           // Record the maximum score into scoreMarks of the current cell
            }



            //System.out.println(output);
            totalScore += maxScore;    // Increasing the total score of all trials by this score
            robot.sequence.remove(robot.sequence.size() - 1);   // Now we remove the extra node that we added above


            // In case that the maxScore beats the best score out of all trials, we need to assign the new best score.
            // Another parameter of a good run is a shorter distance. The distance is plargely related to the number of nodes.
            // So, if the bestSequence yields the same score, but it's longer, we still reassign it
            if (maxScore > bestScore || (maxScore == bestScore && robot.sequence.size() < bestSequence.size())) {    // This is for recording the best possible sequence

                if (bestScore != maxScore) {
                    numberOfBestScores = 0;
                }

                bestScore = maxScore;
                bestSequence = robot.sequence;
                outputOfBestSequence = output;
            }

            if (maxScore == bestScore)
                numberOfBestScores++;

            // Here we reassign values for the sequence.
            for (int i = 0/*2*/; i < robot.sequence.size(); ++i) {

                RobotSequenceRecord currNode = robot.sequence.get(i);   // This is the currently reviewed robotSequenceRecord

                totalValue -= currNode.cell.value.getValue();    // Every time we assign scores, the value changes. So, we subtract it here and add back the new after the value of the cell is changed
                currNode.cell.mutateValueAVG (Vector.CreateFromCartesian(currNode.acc.x, currNode.acc.y).dot((scoreMarks[i])*(maxScore == bestScore ? 1 : 1)));
                totalValue += currNode.cell.value.getValue();

                // Because the chance that some cell became zero after some number of changes is disappearingly small and
                // doesn't affect anything, unless happens too often, it is ok to just mark any cell that ever got changed as nonzero
                // for the sake of reducing O(n^2) of checking each cell for its value to O(n) of the following operation
                valueIsZero[currNode.cell.y][currNode.cell.x] = false;
            }

            if (robot.sequence.size() > 0) {    // This is a punishment for hitting the wall
                RobotSequenceRecord lastNode = robot.sequence.get(robot.sequence.size() - 1);

                totalValue -= lastNode.cell.value.getValue();
                lastNode.cell.mutateValueAVG (Vector.CreateFromCartesian(-lastNode.acc.x, -lastNode.acc.y).dot((tactAmount-robot.sequence.size())*wallConstant));
                System.out.println();
                totalValue += lastNode.cell.value.getValue();
            }



            // This block is responsible for changing values of the initiation line
            // We reward the ctarting cell if it gave a better-than-average score. Else we punish it.
            double tempDiff = (initLineValues[robot.sequence.get(0).cell.x] >= 0.25)
                    ? (score - totalScore/trial)*0.002 : 0;
            initLineValues[robot.sequence.get(0).cell.x] += tempDiff;
            totalInitLineValue += tempDiff;



            // Use the following block for debugging if you need to see field values.
            /*
            if (trial % 1000 <= 3) {
                for (int i = 0; i < field.length; ++i) {
                    for (int j = 0; j < field[0].length; ++j) {
                        System.out.print(field[i][j].value);
                    }
                    System.out.println();
                }
            }
            */

            //System.out.println("Score: " + score);
            //System.out.println("Average score per trial: " + ((double)Math.round(1000.0*totalScore/trial))/1000);
            System.out.println("Best score: " + bestScore);
            System.out.println("Number of best scores: " + numberOfBestScores);
        }
        System.out.println("The best sequence yields " + bestScore + " points:\n" + outputOfBestSequence);

        for (int i = 0; i < bestSequence.size(); ++i) {
            bestSequence.get(i).cell.type = "\u001B[31m" + (i+1) + "\u001B[0m";
        }

        for (int i = 0; i < allies[0].sequence.size(); ++i) {
            allies[0].sequence.get(i).cell.type = allies[0].sequence.get(i).cell.type.concat("\u001B[32m" + (i+1) + "\u001B[0m");
        }

        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[i].length; ++j) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }


        File file = new File("RobotPath.csv");  // This will be the file for output
        file.createNewFile();
        FileWriter output = new FileWriter(file);

        output.append("Move #\tAngle (radians)\tDistance(cells)\n");    // This is the hat of the table

        Vector prevDisplacement = new Vector(1, Math.PI/2);     // This will store the vector of previous (last move)
                                                                    // displacement at every point
        for (int i = 0; i < bestSequence.size() - 1; ++i) {
            Cell currCell = bestSequence.get(i).cell, nextCell = bestSequence.get(i+1).cell;    // Current cell
            Vector currDisplacement = Vector.CreateFromCartesian(nextCell.x - currCell.x, nextCell.y - currCell.y); // Vector of current displacement
            double angle = prevDisplacement.angleBetween(currDisplacement);     // Calculates the angle between the
                                                            // previous and current displacement vectors. The direction is
                                                // the same as in polar circle: CCW is positive, CW is negative
            double distance = currDisplacement.getValue();  // Gets the distance between current and next cells

            System.out.println("Move #" + (i+1) + ":\n\tAngle: " + Math.round(angle*180/Math.PI) + " degrees\n\tDistance: " + distance);
            // Output for a programmer to see

            output.append(i + "\t" + angle + "\t" + distance + "\n");
            // Output for the computer into the CSV file. NOTE THAT THE ANGLE HERE IS IN RADIANS

            prevDisplacement = currDisplacement;    // Assigns current displacement into previous displacement
                                                    // bc the next iteration it will be previous already
        }
        output.close();

    }
}