public class UTF16 {
    public static void main(String[] args) {
        System.out.println("UTF-16编码");

        System.out.println("1.单码的编码.(例:单)");
        String a = "单";
        System.out.println("unicode:" + Integer.toBinaryString(a.codePointAt(0)));
        System.out.print("UTF-16 :");
        for (char c : a.toCharArray()){
            System.out.print(Integer.toBinaryString(c));
        }

        System.out.println("\n\n2.双码的编码.(例:😀)");
        String s = "😀";
        int code = s.codePointAt(0);
        System.out.println("unicode:" + Integer.toBinaryString(code));
        System.out.println("unicode - 0x10000 :" + Integer.toBinaryString(code - 0x10000));
        System.out.print("UTF-16 :");
        for (char c : s.toCharArray()){
            System.out.print(Integer.toBinaryString(c)+" ");
        }
        System.out.print("\nsplit:");
        for (char c : s.toCharArray()){
            String split6 = Integer.toBinaryString(c);
            System.out.print(split6.substring(0,6)+"|" + split6.substring(6,16)+" ");
        }

    }
}
