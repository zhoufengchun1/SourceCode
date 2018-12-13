package homework;

interface Area//4-17
{
    abstract double calculateArea();
}

interface Volume
{
    abstract double calculateVolume();
}

class cone implements Area, Volume
{
    private double height;
    private double round;

    public cone(double height, double round)
    {
        this.height = height;
        this.round = round;
    }

    @Override
    public double calculateArea()
    {
        return Math.PI * round * round + Math.PI * round * (Math.sqrt(round * round + height * height));
    }

    @Override
    public double calculateVolume()
    {
        return (1 / 3.0) * height * Math.PI * round * round;
    }
}