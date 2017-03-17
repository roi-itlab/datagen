/**
 * Created by neron on 12.03.2017.
 */
import java.util.*;
import org.apache.commons.math3.special.Erf;


public class TestGenerateSkill {
        static int num = 100;
        public static void main(String[] args) {

            //задаем дискретные значения x-стаж, у-средний Skill, z-среднеквадратическое отклонение, g-стаж для которого ищем Skill
            double[] x = new double[]{0, 1, 5};
            double[] y = new double[]{0, 1, 25};
            double[] z = new double[]{0, 0.1, 2.5};
            double g=4;

            GenerateSkill u = new GenerateSkill(g, x, y, z);
            double ka=0.120720674867;// процентная точка распределения Колмогорова для 100 элемнтов
            //double ka=Math.sqrt((-1/(2*num))*Math.log(0.05));//-(1/(6*num)); // не правльно считает
            double std= u.std;
            double mean=u.mean;
            int num = 100;
            double max=0;
            double[] value = new double[num];// храним сгенерированнвые Skill для определенного стажа
            double[] valuey = new double[num];//храним координаты y для функции распределения(практической)

            //запись Skill в массив
            for (int i = 0; i < num; i++) {
                u = new GenerateSkill(g, x, y, z);
                value[i] = u.skill;
            }
            Arrays.sort(value);

            for (int i = 2; i < num-2; i++) {
                valuey[i]= FuncPrakt(value[i],value);// знчение функции рапределения(практической) в точке x[i]
                //nw(value[i],mean,std)-знчение функции рапределения(теоритической) в точке x[i]
                if (Math.abs(nw(value[i],mean,std)-valuey[i])>max){   // поиск максимума между теоритической и практической фукцией распределения
                    max=Math.abs(nw(value[i],mean,std)-valuey[i]);
                }
            }

            if(max<=ka){
                System.out.println("Vse normal'no");}
            else {System.out.println("Gde-to kosyak");}

        }
        // функция распределения построенная по точкам(практическая)
        public static double FuncPrakt(double s,double[] p) {
            double r=0;
            for (int j=0; j<num;j++ ){
                if(p[j]<=s){
                    r++;
                }
            }
            r=r/num;
            return r;
        }

        //функция нормального распределения(теоретическая, с которой будем сравнивать)
        public static double nw (double x, double med,double sko){
            double l;
            Erf o = null;
            double t=o.erf((x-med)/(1.41421356*sko));
            l=0.5*(1+t);

            return l;
        }

    }
