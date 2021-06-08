import java.io.*;

public class FileReadWrite {

    private static final String I = File.separator;

    public static void main(String[] args) throws IOException {
        FileReadWrite frw = new FileReadWrite();
        File readFile = new File(frw.workDir() + I + "file" + I + "r.txt");
        File writeFile = new File(frw.workDir() + I + "file" + I + "w.txt");

        frw.byLine(readFile, writeFile);
    }

    private void byByte(File inFile, File outFile) throws IOException {
        InputStream in = new FileInputStream(inFile);
        OutputStream out = new FileOutputStream(outFile);

        byte[] byteArr = new byte[8];
        int n;
        while ((n = in.read(byteArr, 0, byteArr.length)) != -1) {
            String str = new String(byteArr, 0, n, "UTF-8");
            System.out.println(str);
            out.write(byteArr, 0, n);
        }
        in.close();
        out.close();
    }

    private void byChar(File inFile, File outFile) throws IOException {
        FileReader in = new FileReader(inFile);
        FileWriter out = new FileWriter(outFile);

        char[] charArr = new char[4];
        while (in.read(charArr) != -1) {
            String str = new String(charArr);
            System.out.println(str);
            out.write(charArr);
        }
        in.close();
        out.close();
    }

    private void byLine(File inFile, File outFile) throws IOException {
        FileReader in = new FileReader(inFile);
        FileWriter out = new FileWriter(outFile);
        BufferedReader bufferedReader = new BufferedReader(in);
        BufferedWriter bufferedWriter = new BufferedWriter(out);

        String line;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
            bufferedWriter.write(line);
        }
        bufferedWriter.flush();

        bufferedReader.close();
        bufferedWriter.close();
    }


    private String workDir() {
        return System.getProperty("user.dir");
    }

}
