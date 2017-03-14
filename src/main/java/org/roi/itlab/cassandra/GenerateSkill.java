/**
 * Created by neron on 10.03.2017.
 */
// for example
// GenerateSkill u= new GenerateSkill(4,x,y,z);
// System.out.println("Std Skill: "+u.std);
// System.out.println("Mean Skill: "+u.mean);

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class GenerateSkill {
        double mean;
        double std;
    GenerateSkill(double exper, double experience [], double meanskill[], double stdskill[]) {

            SplineInterpolator si = new SplineInterpolator(); //
            PolynomialSplineFunction mpsf = si.interpolate(experience, meanskill); // Computes an interpolating function for the data set for mean Skill
            mean = mpsf.value(exper); // mean skil for the Accepted exper

            PolynomialSplineFunction spsf = si.interpolate(experience, stdskill); // Computes an interpolating function for the data set for std Skill
            std = spsf.value(exper); // std skil for the Accepted exper
        }

    }

