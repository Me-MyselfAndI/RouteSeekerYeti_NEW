import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;


public class Robot {
    private double matchLength;               // How long in seconds the match continues
    private Cell[][] field;
    final private double robotDimension = 1.5;  // Here place an upper-estimate on possible length/width of a robot.
    final int maxCargo = 5;                   // Maximum cargo that the robot can hold
    Cell pos;                                 // Current position
    int cargo;                                // Current amount of cargo
    double maxAcc, shootingTime;              // Maximum acceleration and time it takes to shoot
    ArrayList <RobotSequenceRecord> sequence; // Holds record of all moves the robot did in the order they were done
    private ArrayList <Cell> usedCargo;       // Cargo on the field that has already been used. Needed to avoid picking the same cargo twice
    private Vector vel;                               // Velocity @ any current moment

    public Robot(Cell[][] field, Cell pos, double maxAcc, double shootingTime, int cargo, double matchLength) {    // Initiation of a robot
        this.pos = pos;                   // Assigning starting position
        this.maxAcc = maxAcc;
        this.shootingTime = shootingTime;
        sequence = new ArrayList<RobotSequenceRecord>();
        usedCargo = new ArrayList<Cell>();
        this.cargo = cargo;
        vel = new Vector(0, 0);
        this.field = field;
        this.matchLength = matchLength;
    }

    /**
     * This method creates an acceleration that is partially random (== probabilistic) and partially dependent on the cell value
     * It works the following way:
     * 1. Create acceleration vector that is directly proportional and collinear to the value of the cell
     * 2. Add a probabilistic vector (its length is, of course, random. It stretches up to the product of maximum possible acceleration of the robot and so called probabilistic constant)
     * 3. If the sum is too large (more than maxAxx), keep the direction of the vector but scale it down to maxAcc
     *
     * Returns the resulting vector
     */
    private static Vector probabilisticAcceleration (Robot robot, double probabilisticConstant, double avgFieldValue) {
        Random rand = new Random();
        // Создаем ускорение, равное value-вектору, scaled в случайное (в дальнейшем - мб неслучайное) значение не более maxAcc/avgFieldValue раз
        Vector acc = avgFieldValue == 0 ? new Vector(0, 0) : robot.pos.value.dot(rand.nextDouble()*robot.maxAcc/avgFieldValue);
        // Добавляем к нему стохастический вектор, по модулю не больший, чем maxAcc*probabilisticConstant
        acc.add(new Vector(robot.maxAcc*probabilisticConstant*rand.nextDouble(), (rand.nextDouble()*2 - 1)*Math.PI));
        // Если вышло по модулю более, чем maxAcc, то уменьшаем до maxAcc
        if (acc.getValue() > robot.maxAcc)
            acc = new Vector(robot.maxAcc, acc.getAngle());
        return acc;
    }


    /**
     * This function determines where and how long the robot moves.
     *
     * @param dT    This is how much time per tact there is in seconds. Recommended to take no more than 1.
     * @param probabilisticConstant    How much influence there is for probability in creating acceleration (later passed to probabilistic acceleration)
     * @param avgFieldValue         Average modulus of field value (so, based on total, NOT net). Also used to determine probabilistic acceleration
     * @param remainingTime         How many tacts of time are left
     * @return                      The cell where the robot arrived
     */
    public Cell move (double dT, double probabilisticConstant, double avgFieldValue, double remainingTime, AlliedRobot [] allies){

        field = allies[0].leaveMarkOnField(matchLength - remainingTime);
        field = allies[1].leaveMarkOnField(matchLength - remainingTime);

        boolean wayIsClear;         // This variable will soon be used to store whether or not there are any obstacles on the way
        Vector acc, deltaPos;       //

        // We want to avoid termination of sequence by early bumping into a wall at all costs
        // Such terminations lead to wasting many trials (what could have ended as a good sequence just terminates bc the robot didn't
        // accelerate in the best possible direction). So, we wanna experiment for N = 15 times before the conclusion that it's unavoidable to collide with a wall.
        int counter = 0;
        do {
            counter ++;
            acc = probabilisticAcceleration(this, probabilisticConstant, avgFieldValue); // Here we probabilistically decide what the acceleration value will be
            deltaPos = new Vector(vel.dot(dT).plus(acc.dot(pow(dT, 2) / 2)));            // Using dP = v_0*t + at^2/2
            wayIsClear = deltaPos.wayIsClear(field, pos, robotDimension);                // If there are no obstacles on the way, way is clear.
        } while (counter <= 15 && !wayIsClear);                     // If the way offered has no obstacles, or tried 15 times and no luck, stop looking for another trajectory
        // Почему-то deltaPos портится при передаче в реальное движение

        sequence.add(new RobotSequenceRecord(pos, acc, cargo));                 // Now we add the record with a position, cargo and where the robot accelerated
        if (pos.type.equals("s") && cargo > 0) {                                // If you can shoot now
            vel = new Vector(0, 0);                                 // Stop
            remainingTime -= cargo*shootingTime;                                // Spend time for shooting
            cargo = 0;                                                          // Shoot all cargo
        }
        else if (pos.type.equals("l") && cargo < maxCargo) {            // If can load, load now
            boolean cargoIsFree = true;                                 // This will store whether or not this cargo is still free to be used or it was loaded already
            /** Here we look through the arrayList of used cargo to see if this one is still free.
                This was written in 2020, this year's game had only 4 balls initially on the field. If this year's
                game has a lot of cargo all over the field, rewrite this. Make the field hold the data on whether or not the cargo was used on a particular cell
                because otherwise this part will greatly slow down the program*/
            for (Cell temp : usedCargo) {                               // Look through the arrayList of used cargo.
                if (temp.equals(pos)) {                                 // If you met this cell among the used cargo list, it means it has been taken.
                    cargoIsFree = false;                                // So, this cargo isn't free for use
                    break;                                              // No need to search through this list anymore
                }
            }
            if (cargoIsFree) {                  // If this cargo is free
                cargo++;                        // Load it up (i.e. increase number of cargo aboard)
                usedCargo.add(pos);             // Add it to the used cargo arrayList
            }
        }
        vel.add(acc.dot(dT));       // Using v(dT) = v_0 + a*dT

        /**
         * If there is still time and the sequence isn't over (i.e. robot hasn't bumped into something yet), we run the next move recursively
         */
        if (remainingTime > dT && wayIsClear) {
            pos = field[pos.y + (int) deltaPos.y][pos.x + (int) deltaPos.x];    // The new position is old + dP
            return move(dT, probabilisticConstant, avgFieldValue, remainingTime - dT, allies);  // Recursive step.
        }
        allies[0].clearTraces();
        allies[1].clearTraces();
        return pos;     // When finished with all recursions, return the position
    }
}
