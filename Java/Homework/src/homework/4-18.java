package homework;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

class Globe implements Solid
{
    private double round;

    public Globe(double round)
    {
        this.round = round;
    }

    @Override
    public double calculateVolume()
    {
        return (4 / 3.0) * PI * pow(round, 3);
    }

    @Override
    public double calculateArea()
    {
        return 4 * PI * round * round;
    }
}

interface Solid
{
    abstract double calculateArea();

    abstract double calculateVolume();

}