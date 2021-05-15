package com.csheros.packman.MazeGenerator;


import java.io.*;
import java.util.StringTokenizer;

public class Testing_DSU {


    public static void main(String[] args) throws IOException{

        File file = new File("/media/abdelrahmankhaled/New Volume/Projects/Ac/backman/back/out/production/back/com/company/in.txt");
        if (file.exists()) {
            InputStream in = new FileInputStream(file);
            int n, m;
            Scanner sc = new Scanner(in);

            n = sc.nextInt(); m = sc.nextInt();
            System.out.println(n +" "+m);

            DSU dsu = new DSU(n);
            for (int i = 0; i < m; i++) {
                int u = sc.nextInt(), v = sc.nextInt();
                System.out.println(u +" "+v);
                System.out.println(dsu.isSame(u-1, v-1 ));
                dsu.merge(u-1 , v-1 );
            }
            int arr[] = dsu.getParent();
            int sz[] = dsu.getSize();
            for (int i = 0; i < n; i += 1) {
                System.out.print(arr[i] + " ");
            }


        }else {
            System.out.println("No file");
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

