public class RobotSequenceRecord extends SequenceRecord {  // This is a class for keeping record of each tact of robot movement

    Vector acc;

    public RobotSequenceRecord(Cell cell, Vector acc, int cargo) {
        super(cell, cargo);
        this.acc = acc;
    }
}
