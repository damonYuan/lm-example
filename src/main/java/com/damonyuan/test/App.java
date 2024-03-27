package com.damonyuan.test;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // Define the observed data (target values)
        // y = x^2 + x + 1
        final double[] xData = {1, 2, 3, 4, 5, -1};
        final double[] yData = {3.01, 6.99, 13.01, 20.99, 30.99, 1};

        // Define the initial guess for the parameters
        double[] initialGuess = {1.0, 1.0, 1.0};

        // Create the objective function implementing MultivariateJacobianFunction
        MultivariateJacobianFunction objectiveFunction = new MultivariateJacobianFunction() {
            @Override
            public Pair<RealVector, RealMatrix> value(final RealVector point) {
                double a = point.getEntry(0);
                double b = point.getEntry(1);
                double c = point.getEntry(2);

                int numDataPoints = xData.length;

                // Compute the residuals (errors) and Jacobian matrix
                double[] residuals = new double[numDataPoints];
                double[][] jacobianData = new double[numDataPoints][3];

                for (int i = 0; i < numDataPoints; i++) {
                    double x = xData[i];
                    double y = yData[i];

                    residuals[i] = y - (a * x * x + b * x + c);

                    // Compute the partial derivatives (Jacobian matrix)
                    jacobianData[i][0] = -x * x;
                    jacobianData[i][1] = -x;
                    jacobianData[i][2] = -1.0;
                }

                RealVector residualsVector = MatrixUtils.createRealVector(residuals);
                RealMatrix jacobianMatrix = MatrixUtils.createRealMatrix(jacobianData);

                return new Pair<>(residualsVector, jacobianMatrix);
            }
        };

        // Create the Levenberg-Marquardt optimizer
        LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer();

        // Build the least squares problem
        LeastSquaresProblem problem = new LeastSquaresBuilder()
                .model(objectiveFunction)
                .target(yData)
                .lazyEvaluation(false)
                .maxEvaluations(1000)
                .maxIterations(1000)
                .start(initialGuess)
                .build();

        // Run the optimization
        final LeastSquaresOptimizer.Optimum optimum = optimizer.optimize(problem);
        RealVector optimizedParameters = optimum.getPoint();

        // Print the optimized parameters
        System.out.println("Optimized parameters:");
        System.out.println("a = " + optimizedParameters.getEntry(0));
        System.out.println("b = " + optimizedParameters.getEntry(1));
        System.out.println("c = " + optimizedParameters.getEntry(2));
    }
}
