package homework;

import java.util.Arrays;

interface Average
{
    abstract double calculateAverage();
}

class Demo1 implements Average
{
    private double[] score;

    public Demo1(double[] score)
    {
        this.score = score;
    }

    @Override
    public double calculateAverage()
    {
        double sum = 0;
        for (double temp : score)
        {
            sum += temp;
        }
        return sum/score.length;
    }
}

class Demo2 implements Average
{
    private double[] score;

    public Demo2(double[] score)
    {
        this.score = score;
        Arrays.sort(score);
        score[0] = 0;
        score[score.length - 1] = 0;
    }

    @Override
    public double calculateAverage()
    {
        double sum = 0;
        for (double temp : score)
        {
            sum += temp;
        }
        return sum/(score.length-2);
    }
}

class Demo3 implements Average
{
    private double[] score;
    private double[] weight;
    private double[] finalScore;

    public Demo3(double[] score, double[] weight)
    {
        this.score = score;
        this.weight = weight;
    }

    @Override
    public double calculateAverage()
    {
        double sum = 0;
        for (int i = 0; i < score.length; i++)
        {
            finalScore[i] = score[i] * weight[i];
        }
        for (double temp : finalScore)
        {
            sum += temp;
        }
        return sum/score.length;
    }
}