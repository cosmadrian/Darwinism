package map;

public class GaussianHeightGenerator implements HeightGenerator {

	/* TODO: change these */
	double x0 = Map.WIDTH / 2;
	double y0 = Map.HEIGHT / 2;

	double sigmaX = 500.0;
	double sigmay = 500.0;
	double A = 1.0;

	@Override
	public double getHeight(int x, int y) {
		double xTerm = (double) Math.pow(x - x0, 2) / (double) (2 * Math.pow(sigmaX, 2));
		double yTerm = (double) Math.pow(y - y0, 2) / (double) (2 * Math.pow(sigmay, 2));

		double val = A * Math.exp(-(xTerm + yTerm));
		
		return val;
	}

}
