package com.csheros.packman.MazeGenerator ;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Testing {

    private static int  xx[]={1 , -1 ,0 ,0 };
    private static int yy[]={0 , 0 ,1 ,-1 };

    public static boolean Go(char arr [][] , int x ,int y ,int tar_x ,int tar_y){
        if(x>=arr.length || x <0 ||y>=arr.length || y <0 )
            return false;
        if(arr[x][y]!='.') return false;
        if (x==tar_x && y==tar_y)
           return true;
        boolean res =false;
        for(int i =0 ;i  <4 ;i++){
            res = res || Go(arr , x+xx[i] ,y+yy[i] , tar_x ,tar_y);
        }
        return res;

    }



    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Randomization rand = new Randomization(n);
        char res[][] = rand.GetMap();
        for(int i =0 ;i  < res.length ;i++){
            for(int j =0 ;j < res[0].length ;j++){
                System.out.print(res[i][j]);
            }
            System.out.println();
        }
        ArrayList<Pair>arr= new ArrayList<Pair>();
        for(int i =0 ;i < n ;i+=1)
                for(int j =0 ;j < n ;j++)
                    if(res[i][j]=='.')
                        arr.add(new Pair(i , j ));
        for(Pair cur : arr){
            for(Pair tar : arr)
                System.out.println(Go(res ,cur.ff,cur.ss , tar.ff ,tar.ss ));
        }


    }
    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream system) {
            br = new BufferedReader(new InputStreamReader(system));
        }


        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public char nextChar() throws IOException {
            return next().charAt(0);
        }

        public Long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public boolean ready() throws IOException {
            return br.ready();
        }


        public int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public long[] nextLongArray(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }


        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public double[] nextDoubleArray(int n) throws IOException {
            double[] ans = new double[n];
            for (int i = 0; i < n; i++)
                ans[i] = nextDouble();
            return ans;
        }

        public short nextShort() throws IOException {
            return Short.parseShort(next());
        }

    }


}
