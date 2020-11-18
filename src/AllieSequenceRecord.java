public class AllieSequenceRecord extends SequenceRecord {

    double time;

    public AllieSequenceRecord (Cell cell, int cargo, double time) {
        super(cell, cargo);
        this.time = time;
    }

    public AllieSequenceRecord (Cell cell, int cargo) {
        super(cell, cargo);
    }

}
