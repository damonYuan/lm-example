package com.damonyuan.test;

import org.apache.commons.math3.fitting.leastsquares.*;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

import static java.lang.Math.sqrt;
import static java.util.Arrays.fill;

public class App {
    // The following simple example shows how to find the center of a circle of known radius to best fit observed 2D points.
    public static void main(String[] args) {
        // (x-a)^2 + (y-b)^2 = r^2
        // f(a, b) = sqr((x-a)^2 + (y-b)^2) - r
        final double radius = 1;
        final double[] xs = new double[]{0, 1, 2, 1};
        final double[] ys = new double[]{1, 2, 1, 0};
        // obviously the fitting result should be close to (1, 1)
        final int numOfPoints = xs.length;

        // the model function components are the distances to current estimated center,
        // they should be as close as possible to the specified radius
        final MultivariateJacobianFunction distancesToCurrentCenter = new MultivariateJacobianFunction() {
            @Override
            public Pair<RealVector, RealMatrix> value(final RealVector point) {

                final double a = point.getEntry(0);
                final double b = point.getEntry(1);

                final RealVector value = new ArrayRealVector(numOfPoints);
                final RealMatrix jacobian = new Array2DRowRealMatrix(numOfPoints, 2);
                for (int i = 0; i < numOfPoints; ++i) {
                    final double x = xs[i];
                    final double y = ys[i];
                    final double distance = sqrt((x - a) * (x - a) + (y - b) * (y - b));
                    final double residual = distance - radius;
                    value.setEntry(i, residual);
                    // df/da = -(x-a)/sqrt((x-a)^2 + (y-b)^2)
                    jacobian.setEntry(i, 0, -(x - a) / distance);
                    // df/db = -(y-b)/sqrt((x-a)^2 + (y-b)^2)
                    jacobian.setEntry(i, 1, -(y - b) / distance);
                }

                return new Pair<>(value, jacobian);
            }
        };

        // the target is f(a, b) = sqr((x-a)^2 + (y-b)^2) - r = 0
        final double[] prescribedDistances = new double[numOfPoints];
        fill(prescribedDistances, 0);

        // least squares problem to solve : modeled radius should be close to target radius
        final LeastSquaresProblem problem = new LeastSquaresBuilder()
                .start(new double[]{100, 50})
                .model(distancesToCurrentCenter)
                .target(prescribedDistances)
                .lazyEvaluation(false)
                .maxEvaluations(1000)
                .maxIterations(1000)
                .build();
        System.out.println("Observation size: " + problem.getObservationSize());
        System.out.println("Parameter size: " + problem.getParameterSize());
        System.out.println("Start with: " + problem.getStart());

        final LeastSquaresOptimizer.Optimum optimum = new LevenbergMarquardtOptimizer().optimize(problem);
        System.out.println("fitted center: " + optimum.getPoint().getEntry(0) + " " + optimum.getPoint().getEntry(1));
        System.out.println("RMS: " + optimum.getRMS());
        System.out.println("evaluations: " + optimum.getEvaluations());
        System.out.println("iterations: " + optimum.getIterations());
    }
}
