import forcemodel.CEnvironment;
import forcemodel.COutput;
import javax.swing.*;
import java.util.ArrayList;

/**
 * main class
 * Created by Fatema on 10/20/2016.
 */
public class CMain extends JFrame {

    public CMain()
    {
    }

    public static void main( String []args ) throws InterruptedException
    {
        JFrame frame = new JFrame("Social Force Model");
        CEnvironment l_env = new CEnvironment();

        frame.add( l_env );

        frame.setSize( 800, 600 );
        frame.setVisible( true );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        int i =0;
        long startTime = System.currentTimeMillis();
        while ( i < 6550 ) {

            l_env.update();
            l_env.repaint();
            i++;
            Thread.sleep( 10 );
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);

    }
}
