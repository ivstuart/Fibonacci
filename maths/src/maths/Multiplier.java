package maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/*
 * Matrix Multiplier using Fork Join.
 */
public class Multiplier extends RecursiveTask<BigInteger[][]> {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final BigInteger[][] F;
    public final BigInteger[][] M;
    public final BigInteger[][] R;
    public int task;

    Multiplier(BigInteger[][] F, BigInteger[][] M, BigInteger[][] R, int task) {
        this.F = F;
        this.M = M;
        this.R = R;
        this.task = task;
    }

    protected BigInteger[][] compute() {
        switch (task) {
            case 0:
                List<Multiplier> list = new ArrayList<>();
                list.add(new Multiplier(F, M, R, 1));
                list.add(new Multiplier(F, M, R, 2));
                list.add(new Multiplier(F, M, R, 3));
                list.add(new Multiplier(F, M, R, 4));
                invokeAll(list);
                break;
            case 1:
                R[0][0] = F[0][0].multiply(M[0][0]).add(F[0][1].multiply(M[1][0]));
                break;
            case 2:
                R[0][1] = F[0][0].multiply(M[0][1]).add(F[0][1].multiply(M[1][1]));
                break;
            case 3:
                R[1][0] = F[1][0].multiply(M[0][0]).add(F[1][1].multiply(M[1][0]));
                break;
            case 4:
                R[1][1] = F[1][0].multiply(M[0][1]).add(F[1][1].multiply(M[1][1]));
                break;
        }
        return R;
    }

}