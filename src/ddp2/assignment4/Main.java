package ddp2.assignment4;

import javax.swing.JFrame;

public class Main {

    /**
     * AUTHOR : ARYA FAKHRUDDIN CHANDRA
     * NPM : 2006607526
     * @param args
     */

    public static void main(String[] args) {
	// write your code here

        JFrame frame = new JFrame("Infix -> Postfix");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new MainDisplay());

        frame.pack();
        frame.setVisible(true);
}
}
