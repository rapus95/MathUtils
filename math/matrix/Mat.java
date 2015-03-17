package math.matrix;

import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class Mat {

    protected double mat[][]; // mat[rows][columns]

    public Mat(int rows, int colums) {
        mat = new double[rows][colums];
    }

    public Mat(Mat m) {
        mat = new double[m.getRows()][m.getColums()];
        for (int row = 0; row < mat.length; row++) {
            System.arraycopy(m.mat[row], 0, mat[row], 0, mat[row].length);
        }
    }

    public static void setIdentity(Mat m) {
        m.setIdentity();
    }

    public static void setIdentity(Mat m, double scale) {
        m.setIdentity(scale);
    }

    public void setIdentity() {
        setIdentity(1);
    }

    public void setIdentity(double scale) {
        for (int row = 0; row < getRows(); row++) {
            Arrays.fill(mat[row], 0);
            if (row < mat[row].length)
                mat[row][row] = scale;
        }
    }

    public int getRows() {
        return mat.length;
    }

    public int getColums() {
        return mat[0].length;
    }

    public void setValue(int row, int col, double val) {
        mat[row][col] = val;
    }

    public double getValue(int row, int col) {
        return mat[row][col];
    }

    public Mat rotate(double degree) {
        throw new UnsupportedOperationException("Not yet implemented!!!!");
    }

    public static Mat makeRotMat(double degree, double... axis) {
        Mat m = new Mat(axis.length, axis.length);
        if (degree != 0)
            throw new UnsupportedOperationException("Not yet implemented!!!!");
        return m;
    }

    public static Mat makeRotMat_Given(int dimensions, double degree, int axis1, int axis2) {
        Mat rot = new Mat(dimensions, dimensions);
        rot.setIdentity();
        rot.mat[axis1][axis1] = rot.mat[axis2][axis2] = Math.cos(Math.toRadians(degree));
        rot.mat[axis1][axis2] = -(rot.mat[axis2][axis1] = Math.sin(Math.toRadians(degree)));
        return rot;
    }

    public static Mat add(Mat m1, Mat m2) {
        return m1.add(m2);
    }

    public Mat add(Mat other) {
        if (this.getRows() != other.getRows() || this.getColums() != other.getColums())
            throw new IllegalArgumentException("can't add up " + this.getRows() + "x" + this.getColums() + "- and "
                    + other.getRows() + "x" + other.getColums() + "-Matrix");
        Mat m = new Mat(this.getRows(), this.getColums());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getColums(); j++) {
                m.mat[i][j] = this.mat[i][j] + other.mat[i][j];
            }
        }
        return m;
    }

    public double pNorm(int p) {
        double sum = 0;
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColums(); j++) {
                if (p == 1) {
                    sum += this.mat[i][j];
                } else {
                    sum += Math.pow(this.mat[i][j], p);
                }
            }
        }
        return Math.pow(sum, 1 / p);
    }

    public static Mat mul(Mat m1, Mat m2) {
        return m1.mul(m2);
    }

    public Mat mul(Mat other) {
        int col1 = getColums(), row1 = getRows(), col2 = other.getColums(), row2 = other.getRows();
        if (col1 != row2)
            throw new IllegalArgumentException("can't multiply" + row1 + "x" + col1 + " with " + row2 + "x" + col2
                    + ".");
        Mat dest = new Mat(row1, col2);
        for (int destRow = 0; destRow < row1; destRow++) {
            for (int i = 0; i < col1; i++) {
                for (int destCol = 0; destCol < col2; destCol++) {
                    dest.mat[destRow][destCol] += mat[destRow][i] * other.mat[i][destCol];
                }
            }
        }
        return dest;
    }

    public Vec mul(Vec other) {
        int mat_col = getColums(), mat_row = getRows(), vec_row = other.getDimensionCount();
        if (mat_col != vec_row)
            throw new IllegalArgumentException("can't multiply" + mat_row + "x" + mat_col + "-Matrix with " + vec_row
                    + "D-Vector.");
        Vec dest = new Vec(mat_row);
        for (int destRow = 0; destRow < mat_row; destRow++) {
            for (int i = 0; i < mat_col; i++) {
                dest.vec[destRow] += mat[destRow][i] * other.vec[i];
            }
        }
        return dest;
    }

    public static Mat mul(Mat m1, double val) {
        return m1.mul(val);
    }

    public Mat mul(double val) {
        Mat dest = new Mat(this);
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColums(); col++) {
                dest.mat[row][col] *= val;
            }
        }
        return dest;
    }

    public Mat transpose() {
        Mat transposed = new Mat(getColums(), getRows());
        for (int col = 0; col < getRows(); col++) {
            for (int row = 0; row < getColums(); row++) {
                transposed.mat[row][col] = mat[col][row];
            }
        }
        return transposed;
    }

    @Override
    public String toString() {
        return "Mat [mat=" + Arrays.deepToString(this.mat) + "]";
    }

    public double[] toArray() {
        int rows = this.getRows();
        int columns = this.getColums();
        double[] array = new double[rows * columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(mat[i], 0, array, i * columns, columns);
        }
        return array;
    }

    public void toOGLDoubleBuffer(DoubleBuffer buff) {
        int rows = this.getRows();
        int columns = this.getColums();
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                buff.put(mat[r][c]);
            }
        }
    }
    
    public static IVec solve(Mat a, IVec b, double omega, double epsilon){
        int n;
        if(a.getColums()!=(n=a.getRows())) throw new IllegalArgumentException("expected a square-matrix");
        if(b.getDimensionCount()!=n) throw new IllegalArgumentException("expeted the given vector to be of length n");
        Vec xNew, xOld;
        xNew = new Vec(n);
        do{
            xOld=xNew;
            xNew = new Vec(n);
            for(int i=0; i<n; i++){
                double finalResult = b.getComponent(i);
                double tmp=0;
                for(int j=0; j<i; j++){
                    tmp+=a.getValue(i, j)*xNew.getComponent(j);
                }
                finalResult-=tmp;
                tmp=0;
                for(int j=i+1; j<n; j++){
                    tmp+=a.getValue(i, j)*xOld.getComponent(j);
                }
                finalResult-=tmp;
                xNew.setComponent(i, (1-omega)*xOld.getComponent(i)+omega/a.getValue(i, i)*finalResult);
            }
        }while(xOld.distanceTo(xNew)>=epsilon);
        return xNew;
    }

    public static Mat createMatrixFromConsole() {
        Scanner s = new Scanner(System.in);
        int rows = s.nextInt();
        int cols = s.nextInt();
        s.nextLine();
        Mat m = new Mat(rows, cols);
        m.readMatrixFromConsole(s);
        s.close();
        return m;
    }
    
    public void readMatrixFromConsole(Scanner s){
        for (int i = 0; i < this.getRows(); i++) {
            String[] input = s.nextLine().split(" ");
            if (input.length != this.getColums()){
                throw new IllegalArgumentException("expected " + this.getColums() + " elements but got " + input.length
                        + " elements.");
            }
            for (int j = 0; j < input.length; j++) {
                this.mat[i][j] = Double.valueOf(input[j]);
            }
        }
    }

    public void writeMatrixToConsole() {
        if (this.getRows() <= 0)
            return;
        System.out.print("Matrix:");
        for (int i = 0; i < this.getRows(); i++) {
            System.out.println();
            if (this.getColums() <= 0)
                continue;
            System.out.print(this.mat[i][0]);
            for (int j = 1; j < this.getColums(); j++) {
                System.out.print(" " + this.mat[i][j]);
            }
        }
        System.out.println();
    }

    public static void writeMatrixToConsole(Mat m) {
        m.writeMatrixToConsole();
    }

}
