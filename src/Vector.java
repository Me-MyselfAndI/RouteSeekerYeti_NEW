public class Vector {
    double x, y;

    public Vector () {}

    /**
     * With this program, polar coordinates are uncomparably more convenient as a way to make a vector than Cartesian.
     * But there is another 'constructor' from Cartesians below in case t s needed to create a Vector using Cartesian
     * @param radius - length of the vector
     * @param angle - angle of the vector. Imagine a unit circle. Angle is measured the same way
     */
    public Vector (double radius, double angle) {
        x = radius*Math.cos(angle);
        y = radius*Math.sin(angle);
    }

    public static Vector CreateFromCartesian (double x, double y) {
        Vector res = new Vector();
        res.x = x;
        res.y = y;
        return res;
    }

    public Vector(Vector b) {
        x = b.x;
        y = b.y;
    }

    public double getAngle (){
        return x == 0 && y == 0 ? 0 : Math.acos(x / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) * Math.signum(y);
                            // theta = acos (X/R), where R is hypotenuse
                            // sign(y) determines the direction of the angle (THINK OF UNIT CIRCLE)
    }

    public double getValue () {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));  // Just Pythagorean Theorem
    }

    public double dot (Vector b) {  // Scalar product (or dot-product) of vectors
        return Math.cos(angleBetween(b))*getValue()*b.getValue();
    }

    public Vector dot (double b) {  // Vector times (dot) scalar
        Vector res = new Vector(this);
        res.x *= b;
        res.y *= b;
        return res;
    }

    public double angleBetween (Vector b) {
        return b.getAngle() - getAngle();
    }

    public Vector plus (Vector b) {      // Vector sum
        Vector res = new Vector(this);
        res.x += b.x;
        res.y += b.y;
        return res;
    }

    public String toString() {      // Just prints vector's Cartesian components
        return "(" + Math.round(x) + ";" + Math.round(y) + ")";
    }

    public void setValue (Vector value) {
        x = value.x;
        y = value.y;
    }

    public void add (Vector a) {    // Adds some other vector to this vector
        this.setValue(this.plus(a));
    }


    public boolean containsPoint (double xCoord, double yCoord) {
        Vector vector = Vector.CreateFromCartesian(xCoord, yCoord);
        return this.getAngle() == vector.getAngle() && vector.getValue() <= this.getValue();
    }

    public short getQuadrant () {
        var angle = getAngle();
        while (angle < 0)
            angle += 360;

        if (angle < 180)
            return (short) (angle < 90 ? 1 : 2);
        else
            return (short) (angle < 270? 3 : 4);

    }

    public boolean[] getDirection () {
        var direction = new boolean[2]; // [0] - x; [1] - y; true - pos, false - neg
        var incrementQuadrant = this.getQuadrant();
        if (incrementQuadrant == 1) {
            direction[0] = true;
            direction[1] = true;
        }
        else if (incrementQuadrant == 2) {
            direction[0] = false;
            direction[1] = true;
        }
        else if (incrementQuadrant == 3) {
            direction[0] = false;
            direction[1] = false;
        }
        else {
            direction[0] = true;
            direction[1] = false;
        }

        return direction;
    }

    /**
     *
     * @param field - game field
     * @param pos - position of this vector's starting point
     * @param dPos - the vector of displacement
     * @param robotWidth - the width of robot cell
     * @return whether or not there are obstacles on the offered way
     */
    public static boolean wayIsClear (Cell[][] field, Cell pos, Vector dPos, double robotWidth) {
        if (!pos.check(field, dPos.x, dPos.y)) return false;     // If there is a wall on the endpoint, or it is outside of the game field, quit immediately

        double dPosValue = dPos.getValue();       // In order to not waste time to run the same function many times, we'll
                                                  // just record it once (dynamic programming) and reuse it every time
        Vector increment = new Vector(0.5, dPosValue);  // This will be used as an increment in the for-loop
        for (Vector i = new Vector (0,0); i.getValue() <= dPosValue; i.add(increment)) { // For-loop with a vector as a counter
            Cell currCell = pos.shiftBy(field, i);            // This is the cell that will be reviewed
            for (double xDist = -robotWidth; xDist <= robotWidth; xDist += .5)  // This and the following loops eliminate the cells around the robot cell
                for (double yDist = -robotWidth; yDist <= robotWidth; yDist += .5)
                    if (!currCell.check(field, xDist, yDist))       // If there is a wall here, return false
                        return false;
        }
        // If reached this place, it means the way is clear
        return true;
    }

    // This is the same method as above, just not static
    public boolean wayIsClear (Cell[][] field, Cell pos, double robotWidth) {
        return wayIsClear(field, pos, this, robotWidth);
    }

}
