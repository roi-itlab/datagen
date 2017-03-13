/**
 * Created by neron on 10.03.2017.
 */
// for example
// GenerateSkill u= new GenerateSkill(4,x,y);
// System.out.println(u.result);

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class GenerateSkill {
        double result;
    GenerateSkill(double skill1, double experience [], double skill[]) {

            SplineInterpolator si = new SplineInterpolator(); //
            PolynomialSplineFunction psf = si.interpolate(experience, skill); // Computes an interpolating function for the data set
            result = psf.value(skill1); // The generated skill is in the variable result
        }

    }

