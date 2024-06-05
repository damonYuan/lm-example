README
====

Example for [OLS with Levenberg-Marquardt Optimizer](https://commons.apache.org/proper/commons-math/userguide/leastsquares.html).

https://vnav.mit.edu/material/17-18-NonLinearLeastSquares-notes.pdf

https://zlthinker.github.io/optimization-for-least-square-problem

https://www.youtube.com/watch?v=CjrRFbQwKLA

[Lecture 13: Non-linear least squares and the Gauss-Newton method](https://www.uio.no/studier/emner/matnat/math/MAT3110/h19/undervisningsmateriale/lecture13.pdf)

[Least squares](https://en.wikipedia.org/wiki/Least_squares#:~:text=Least%20squares%20problems%20fall%20into,has%20a%20closed%2Dform%20solution) problems fall into two categories: linear or ordinary least squares and nonlinear least squares, depending on whether or not the residuals are linear in all unknowns. The linear least-squares problem occurs in statistical regression analysis; it has a closed-form solution. The nonlinear problem is usually solved by iterative refinement; at each iteration the system is approximated by a linear one, and thus the core calculation is similar in both cases.

# TODO

1. The first one is based on the Gauss-Newton method. 
2. The second one is the Levenberg-Marquardt method. [Gauss-Newton / Levenberg-Marquardt Optimization](https://mat.uab.cat/~alseda/MasterOpt/optimization.pdf)
3. .lazyEvaluation(false) -> what is this
4. .maxEvaluations(1000)  -> what is this
5. .maxIterations(1000) -> what is this
