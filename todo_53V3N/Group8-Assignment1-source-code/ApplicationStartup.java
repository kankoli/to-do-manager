import view.MainView;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-07
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationStartup {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainView view = new MainView();
            	view.createAndShowGUI();
                //view.MainView.createAndShowGUI();
            }
        });

    }
}
