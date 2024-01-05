public class Dop {
    public static void main(String[] args) {
        System.out.println(method(19));
        System.out.println(method1(19));
    }

    public static int method (int n) {
        String st = Integer.toBinaryString(n);
        int pos = st.lastIndexOf("0");
        if (pos != -1) {
            st = st.substring(0, pos) + "1" + "0".repeat(st.length() - pos - 1);
        }
        return Integer.parseInt(st, 2);
    }

    public static int method1 (int n) {
        return ++n;
    }
}
