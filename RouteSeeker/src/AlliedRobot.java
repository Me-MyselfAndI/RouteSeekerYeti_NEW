import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class AlliedRobot {

    private Cell[][] field;
    private final Cell[][] initialField;
    private final double marginOfTimeError = 3;
    private final double defaultTactTime = 1.0;
    ArrayList <AllieSequenceRecord> sequence;
    double avgVel, shootingTime, loadingTime;

    public AlliedRobot(Cell[][] field, String teamNum, int cargo, double... tactTimeHolder) {
        this.field = field;
        this.initialField = field;
        double tactTime = tactTimeHolder.length == 0 ? defaultTactTime : tactTimeHolder[0];

        Scanner in = new Scanner("");
        try {
            String alliedPathSource = System.getProperty("user.dir");
            System.out.println("Working Directory: " + alliedPathSource + "\n\n");
            File alliedPathFile = new File (alliedPathSource + "\\RouteSeeker\\Allies_strategies\\" + teamNum + ".txt");
            in = new Scanner(alliedPathFile);
        }
        catch (Exception E) {
            System.out.println("\u001b[31mFile not found");
        }
        String currLine;

        // System.out.println (in.nextLine());
        avgVel = in.nextDouble();       // Reads average velocity
        shootingTime = in.nextDouble(); // Reads shooting time
        loadingTime = in.nextDouble();

        sequence = new ArrayList<AllieSequenceRecord>();    // Creates the sequence

        in.nextLine();                  // Skips to reading the next line
        while (true) {                  // Will keep reading until gets to the end. It will break then.
            currLine = in.nextLine().replaceAll("\\(", "").replaceAll(",",
                    "").replaceAll("\\)" , " ");    // Puts the line into the format that
                                                                              // can be converted to int
            Scanner scannedLine = new Scanner(currLine);
            int [] coords = new int[2];         // Coordinates of a point that is currently being read
            String action = "";                 // Action will hold "", "l", or "s" (no action, load, or shoot)

            try {
                coords[1] = scannedLine.nextInt();  // Get the x coordinate
                coords[0] = scannedLine.nextInt();  // Get the y coordinate
            } catch (Exception e){                  // If no more coordinates left to read, break out of the loop
                break;
            }
            if (scannedLine.hasNext()) {            // If possible, move on to the next symbol - that is the action of this cell
                action = scannedLine.next();        //
                if (action.equals("l"))
                    cargo ++;
                else if (action.equals("s")) {
                    cargo = 0;
                }
            }

            sequence.add(new AllieSequenceRecord(field[coords[1]][coords[0]], cargo));  // Add this to the sequence log
            //System.out.println(coords[0] + ", " + coords[1] + " " + action);    // Print out the result
        }

        System.out.println(avgVel);
        System.out.println(shootingTime);
        System.out.println(loadingTime);

        double time = 0;                                    // Sets a time counter. Will record timestamps at every cell
        sequence.get(0).time = 0;                           // Timestamp for the 0th term of the sequence

        for (int i = 1; i < sequence.size(); ++i) {
            AllieSequenceRecord prevNode = sequence.get(i-1), currNode = sequence.get(i);   // Determines prevNode and currNode
            time += Math.sqrt (Math.pow(currNode.cell.x - prevNode.cell.x, 2) +
                    Math.pow(currNode.cell.y - prevNode.cell.y, 2))/avgVel;         // Calculates time change by dividing
                                                // distance by velocity. It is not too precise, so we have a time window
                                        // for the movement of the robot
            currNode.time = time;       // Assigns timestamp to the current node
        }

        in.close();                     // Closes the input

    }


    public Cell[][] leaveMarkOnField (double currTime) {

        double tempTime;
                                                        // The further idea is that we take "time window": current
                                                        // time +- margin of error of time (a robot of allies can start with a
                                                        // delay, or the speed may be uneven, etc), so we want to have some margin
                                                        // We UNDER NO CIRCUMSTANCE want to cross a part of the trajectory of
                                                        // our allies in their current time window.
                                                        // We call the first point of that chunk of the trajectory early boundary
                                                        // We call the last point of that chunk late boundary

        AllieSequenceRecord lateBoundary;               // Create record for the log of the late boundary
        int lateBoundaryIndex = sequence.size();        // This is the index of the late boundary
        do {
            lateBoundaryIndex--;                        // We just move down and down and down from the last point of
                                                        // the WHOLE trajectory until we reach the late boundary time
            lateBoundary = sequence.get(lateBoundaryIndex);     // Get the sequence record at currently reviewed index
            tempTime = lateBoundary.time;
            if (tempTime <= currTime + marginOfTimeError) { // If the currently reviewed time is within the time window,
                break;                                  // Break out of the loop
            }
        } while (tempTime > 0);                         // And just in case the time window did not work right, the
                                                        // while loop should end if the late boundary gets earlier than
                                                        // the beginning of the match.

        AllieSequenceRecord earlyBoundary;              // Here the idea is the same, but we go not from the end, but
                                                        // from the beginning
        int earlyBoundaryIndex = -1;
        do {
            earlyBoundaryIndex++;
            earlyBoundary = sequence.get(earlyBoundaryIndex);
            tempTime = earlyBoundary.time;
            if (tempTime >= currTime - marginOfTimeError || earlyBoundaryIndex == lateBoundaryIndex) {
                break;
            }
        } while (tempTime < currTime);


        // Here it actually calculates the trajectory at any particular time
        // This is just needed for to erase marks of where the allied robot has traveled, so that our robot
        // knows it is not occupied anymore.
        for (int i = 0; i < earlyBoundaryIndex; ++i) {
            AllieSequenceRecord currNode = sequence.get(i), nextNode = sequence.get(i+1);
            Vector totalDisplacement= Vector.CreateFromCartesian(nextNode.cell.x - currNode.cell.x, nextNode.cell.y - currNode.cell.y);
            Vector increment = totalDisplacement.dot(1/(nextNode.time - currNode.time));

            // Increment is just needed to calculate where the robot will gt from this cell.
            // There are two reasons to pick only those greater than 0.75.
            // First, it spares time. If we anyway stay on the same cell, no need to run a comparably time-consuming
            // method of changing cells.
            // Another is, look at the for-loop below (lines 135-141). It will never end if the vector is too small and
            // doesn't switch to the next cell.
            if (increment.getValue() > 0.75)
                increment = new Vector(0.75, increment.getAngle());



            for (Vector currDisplacement = new Vector(0, 0); currDisplacement.getValue() < totalDisplacement.getValue(); currDisplacement.add(increment)){
                Cell currCell = currNode.cell.shiftBy(field, currDisplacement);
                if (!currCell.checkBeingInsideField(field, 0, 0))
                    break;
                if (currCell.type.contains("r"))
                    currCell.type = currCell.type.replaceAll("r", "");
            }
        }

        // This draws marks on the cells where an allied robot currently is. This is how our robot knows that this is
        // a restricted area. The working principle is the same as the loop above that erases old marks.
        // It is CRUCIALLY important to erase old marks BEFORE creating new. Otherwise, bc of the margins, we risk to in the
        // best case scenario erase the lower time margin, but in the worst case scenario - just erase the place where allied the robot currently is
        for (int i = earlyBoundaryIndex; i < lateBoundaryIndex - 1; ++i) {
            AllieSequenceRecord currNode = sequence.get(i), nextNode = sequence.get(i+1);
            Vector totalDisplacement= Vector.CreateFromCartesian(nextNode.cell.x - currNode.cell.x, nextNode.cell.y - currNode.cell.y);
            Vector increment = totalDisplacement.dot(1/(nextNode.time - currNode.time));

            if (increment.getValue() > 0.75)
                increment = new Vector(0.75, increment.getAngle());



            for (Vector currDisplacement = new Vector(0, 0); currDisplacement.getValue() < totalDisplacement.getValue(); currDisplacement.add(increment)){
                Cell currCell = currNode.cell.shiftBy(field, currDisplacement);
                if (!currCell.checkBeingInsideField(field, 0, 0))
                    break;
                if (!currCell.type.contains("r"))
                    currCell.type = currCell.type.concat("r");
            }
        }


        // Enable the following if you suspect that this function isn't working properly.
        // Look at when and where the allie paths are marked
        /*for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[i].length; ++j)
                System.out.print(field[i][j]);
            System.out.println();
        }*/


        return field;
    }

    public Cell[][] clearTraces () {
        field = initialField;
        return field;
    }

    // This method is needed for testing. If you are in doubt whether or not an allied robot follows the strategy you
    // think it does, run this method and assign the result to the field. It will mark all the path with r's
    public Cell[][] markWholePath () {
        for (int i = 0; i < sequence.size() - 1; ++i) {
            AllieSequenceRecord currNode = sequence.get(i), nextNode = sequence.get(i+1);
            Vector totalDisplacement= Vector.CreateFromCartesian(nextNode.cell.x - currNode.cell.x, nextNode.cell.y - currNode.cell.y);
            Vector increment = totalDisplacement.dot(1/(nextNode.time - currNode.time));

            if (increment.getValue() > 0.75)
                increment = new Vector(0.75, increment.getAngle());



            for (Vector currDisplacement = new Vector(0, 0); currDisplacement.getValue() < totalDisplacement.getValue(); currDisplacement.add(increment)){
                Cell currCell = currNode.cell.shiftBy(field, currDisplacement);

                if (!currCell.checkBeingInsideField (field, 0, 0))
                    break;
                currCell.type = "r";
            }
        }

        return field;
    }
}
