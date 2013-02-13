package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-07
 * Time: 09:58
 */
public class StupidController extends ActionListener {
    private ArrayList<Integer> stupidModel;

    public StupidController() {
        this.stupidModel = new ArrayList<Integer>();
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == add) {
            stupidModel.add(1);
        }
    }
}
