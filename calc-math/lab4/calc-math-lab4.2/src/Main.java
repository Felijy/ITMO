import ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(()->{
            MainFrame mf=new MainFrame();
            mf.setVisible(true);
        });
    }
}
