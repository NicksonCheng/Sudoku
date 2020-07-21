import java.util.ArrayList;

public class SolveSudoku {
    public SolveSudoku() {

    }

    public boolean solveSudoku(int n, ArrayList<Integer> leaveIndex, int board[]) {

        // teminate����摨��
        if (n >= leaveIndex.size())
            return true;

        int pos = leaveIndex.get(n);
        boolean correct = true;
        ArrayList<Integer> exist = new ArrayList<Integer>();
        for (int i = 0; i < 9; ++i)
            exist.add(i);
        // ���row鋆⊿��蝑�
        int rowPos = pos / 9;
        // 瘥�olumn瑼Ｘ
        for (int j = rowPos * 9; j < rowPos * 9 + 9; ++j) {
            // System.out.print(board[j]+" ");
            if (board[j] >= 0 && exist.indexOf(board[j]) >= 0) {
                int index = exist.indexOf(board[j]);
                exist.remove(index);
            }

        }
        // System.out.println();

        // ���column��蝑�
        int columnPos = pos % 9;
        // System.out.println("column=" + columnPos);

        for (int i = 0; i <= 72; i += 9) {
            // System.out.println(board[columnPos + i]);
            if (board[columnPos + i] >= 0 && exist.indexOf(board[columnPos + i]) >= 0) {
                int index = exist.indexOf(board[columnPos + i]);
                exist.remove(index);
            }

        }
        int boxPos = 0;
        // ���銋悅���蝑�
        for (int k = 0; k <= 6; k += 3) {
            for (int p = 0; p <= 54; p += 27) {
                if (pos == k + p)
                    boxPos = k + p;
                else if (pos == k + p + 1)
                    boxPos = k + p;
                else if (pos == k + p + 2)
                    boxPos = k + p;
                else if (pos == k + p + 9)
                    boxPos = k + p;
                else if (pos == k + p + 10)
                    boxPos = k + p;
                else if (pos == k + p + 11)
                    boxPos = k + p;
                else if (pos == k + p + 18)
                    boxPos = k + p;
                else if (pos == k + p + 19)
                    boxPos = k + p;
                else if (pos == k + p + 20)
                    boxPos = k + p;

            }
        }
        // System.out.println("boxPos=" + boxPos);
        for (int i = 0; i <= 18; i += 9) {
            for (int j = 0; j < 3; ++j) {
                // System.out.println(board[i + j + boxPos]);
                if (board[i + j + boxPos] >= 0 && exist.indexOf(board[i + j + boxPos]) >= 0) {
                    // System.out.println("successful");
                    int index = exist.indexOf(board[i + j + boxPos]);
                    exist.remove(index);
                }
            }
        }

        // ��xist
        /*
         * System.out.print("exist="); for (int i = 0; i < exist.size(); ++i)
         * System.out.print(exist.get(i) + 1); System.out.println();
         */

        // 憛怠
        if (exist.size() > 0) {

            board[pos] = exist.get(0);
            exist.remove(0);

            // ��oard��靘�
            /*
             * for (int i = 0; i < 81; i += 9) { for (int j = 0; j < 9; ++j) { if (i + j ==
             * pos) System.out.print(board[i + j] + 1 + "* "); else System.out.print(board[i
             * + j] + 1 + " "); } System.out.println(); } System.out.println();
             */

            correct = solveSudoku(n + 1, leaveIndex, board);
        } else {
            // System.out.println("empty!!!!!!!!!!!!");
            return false;
        }
        while (!correct) {
            if (exist.size() <= 0) {
                board[pos] = -1;
                // System.out.println("empty!!!!!!!!!!!!");
                return false;

            }

            else {
                board[pos] = exist.get(0);
                exist.remove(0);
                correct = solveSudoku(n + 1, leaveIndex, board);
            }

        }
        return true;

    }

}