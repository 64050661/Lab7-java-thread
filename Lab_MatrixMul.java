import java.util.Arrays;

//64050661 วฤษณิ์ ทับทิม
public class Lab_MatrixMul {
    public static void main(String[] args) {
        int[][] inputA = { { 5, 6, 7 }, { 4, 8, 9 } };
        int[][] inputB = { { 6, 4 }, { 5, 7 }, { 1, 1 } };
        MyData matA = new MyData(inputA);
        MyData matB = new MyData(inputB);
        int matC_r = matA.data.length;
        int matC_c = matB.data[0].length; // ขนาด Matrix
        MyData matC = new MyData(matC_r, matC_c);

        // Q4 construct 2D array for each "thread"
        // with respected to each cell in matC,
        // then start each thread
        Thread p00 = new Thread(new MatrixMulThread(0, 0, matA, matB, matC));
        p00.start();
        Thread p01 = new Thread(new MatrixMulThread(0, 1, matA, matB, matC));
        p01.start();
        Thread p10 = new Thread(new MatrixMulThread(1, 0, matA, matB, matC));
        p10.start();
        Thread p11 = new Thread(new MatrixMulThread(1, 1, matA, matB, matC));
        p11.start();

        // Q5 join each thread
        try {
            p00.join();
            p01.join();
            p10.join();
            p11.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        matC.show();
    }
}

class MatrixMulThread implements Runnable {
    int processing_row;
    int processing_col;
    MyData datA;
    MyData datB;
    MyData datC;

    MatrixMulThread(int tRow, int tCol, MyData a, MyData b, MyData c) {
        // Q1 code here
        processing_row = tRow;
        processing_col = tCol;
        datA = a;
        datB = b;
        datC = c;
    }

    // Q2
    public void run() {
        // Q3
        int tmp = 0;
        for (int i = 0; i < 3; i++) {
            tmp += datA.data[processing_col][i] * datB.data[i][processing_row];
        }
        datC.data[processing_col][processing_row] = tmp;
        System.out.println("perform sum ofmultiplication of assigned row and col" + " "
                + datC.data[processing_col][processing_row]);
    }
}

class MyData {
    int[][] data;

    MyData(int[][] m) {
        data = m;
    }

    MyData(int r, int c) {
        data = new int[r][c];
        for (int[] aRow : data) {
            Arrays.fill(aRow, 9);
            // 9 will be overwritten anyway
        }
    }

    void show() {
        System.out.println(Arrays.deepToString(data)); // print Array 2 มิติ
    }
}
