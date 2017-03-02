package org.roi.itlab.cassandra.random_attributes;



import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vadim on 26.02.2017.
 */
public class RandomAttributesDirector {
    private RandomAttributesBuilder builder;
    Random r ;
    List<Point> pointList;
    List<Point> pointList2;
    List<Point> pointList3;

    public RandomAttributesDirector()
    {
        builder = new RandomAttributesBuilderImpl();
        r = new Random(System.currentTimeMillis());
        ///////
        pointList = new ArrayList<>();
        pointList.add(new Point(18,1));
        pointList.add(new Point(25,2));
        pointList.add(new Point(30,3));
        pointList.add(new Point(60,1));

        pointList2 = new ArrayList<>();
        pointList2.add(new Point(0,0));
        pointList2.add(new Point(4,2));
        pointList2.add(new Point(10,3));
        pointList2.add(new Point(20,3));

        pointList3 = new ArrayList<>();
        pointList3.add(new Point(0,1));
        pointList3.add(new Point(3,5));
        pointList3.add(new Point(5,10));
        pointList3.add(new Point(7,5));
        pointList3.add(new Point(10,1));
    }

    public Attributes constract()
    {
        //random distributions
        builder = new RandomAttributesBuilderImpl();
        return builder.setAge(rand(65, -18,4,pointList)).setExperience(rand(30,0,4,pointList2)).
                setAgression(rand(11,0,12,pointList3)).build();

    }

    public int rand(int a, int b, int c, List<Point> list)
    {
        int m = 0;
        while(m <=0)
        {
          // m = distribution(r.nextInt(65)-18, 4*r.nextDouble());
            m = dist(r.nextInt(a)+b, c*r.nextDouble(), list);
        }
        return m;
    }

    public int distribution(int a, double b)
    {
        if(a > 18 && b < 5 ) {
            if (a < 25) {
                if(b < (1/7.0 * a -11/ 7.0)){return a;}
            } else if (a < 30) {
                if(b < (1/5.0*a -3)){return a;}
            } else if (a < 60) {
                if(b < (-1/15.0 *a +5)){return a;}
            }
        }
        return 0;
    }

    public int dist(int a, double b, List<Point> list)
    {
        //System.out.println(a + " "+ b);
        for(int i=1; i < list.size();++i)
        {
            if(a <= list.get(i).x && a > list.get(i-1).x)
            {

                //if(b < (list.get(i).y-list.get(i-1).y)/(list.get(i).x-list.get(i-1).x)*(a-1)+list.get(i-1).y)
                if(b < (list.get(i).y-list.get(i-1).y)/(list.get(i).x-list.get(i-1).x)*(a-list.get(i-1).x)+list.get(i-1).y)
                {
                    //System.out.println((list.get(i).y-list.get(i-1).y)/(list.get(i).x-list.get(i-1).x)*(a-list.get(i-1).x)
                     //       +list.get(i-1).y);
                    //System.out.println((((double)pointList.get(i).y-pointList.get(i-1).y)/(pointList.get(i).x-pointList.get(i-1).x))+ " " + (-(pointList.get(i).y-pointList.get(i-1).y)/(pointList.get(i).x-pointList.get(i-1).x)+pointList.get(i-1).y));
                    return a;
                }
            }
        }
        return 0;
    }

     public static void main(String[] args)
    {
        Attributes attribut;

        DescriptiveStatistics stats = new DescriptiveStatistics();
        RandomAttributesDirector rad = new RandomAttributesDirector();
        List<Attributes> list = new ArrayList<Attributes>();

        System.out.println("age experience agression");
        for(int i = 0; i < 10; ++i) {
            list.add(rad.constract());
        }

        for(Attributes a : list){
            System.out.println(a.getAge() + " " + a.getExpirience() + " " + a.getAgression());
            stats.addValue(a.getAge());
        }
        System.out.println("mean =" + stats.getMean()+ ", sd ="+ stats.getStandardDeviation());

    }
}
