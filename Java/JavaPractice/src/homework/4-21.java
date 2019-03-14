package homework;

import static java.lang.StrictMath.sqrt;

interface Comparable
{
    abstract int compare(Complex complex);
}

class Complex implements Comparable
{
    private Complex complex;
    private double model;
    private double real;
    private double image;

    public Complex(Complex complex)
    {
        this.complex = complex;
        this.model = sqrt(complex.real * complex.real + complex.image * complex.image);
    }



    @Override
    public int compare(Complex complex)
    {
        double temp = this.complex.model - complex.model;
        if (temp > 0)
            return 1;
        else if (temp == 0)
        {
            return 0;
        }

        else
            return -1;
    }
}