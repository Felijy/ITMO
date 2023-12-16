import classes.Human;

public class Test {
    public static String getClassName(Object obj) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            return stackTrace[2].getClassName();
        } else {
            return "Could not determine class name";
        }
    }

    public static void main(String[] args) {
        Human r1 = new Human("12");
        String className = Test.getClassName(r1);
        System.out.println("Class name: " + className);
    }
}