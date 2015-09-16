import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.*;
import java.util.List;

public class DisplayFlags extends JFrame
{
    private Map<String, ImageIcon> ausFlags = new HashMap<String, ImageIcon>();

    public static void main(String[] args)
    {
        JFrame frame = new DisplayFlags();
        frame.setVisible(true);
    }

    public DisplayFlags()
    {
        super("Display Flag Layout");

        /*
         * Setup frame
         */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        /*
         * Setup Data
         */
        Vector<String> dataList = new Vector<String>();
        dataList.add("Australia");
        dataList.add("New South Wales");

        final JList list = new JList(dataList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /*
         * Setup Panels
         */

        //JScrollPane scrollPane = new JScrollPane();
        //scrollPane.add(list, BorderLayout.LINE_START);

        JPanel listPanel = new JPanel();
        listPanel.add(list);
        listPanel.setBackground(Color.WHITE);


        /*
         * Hash Map
         */
        ausFlags.put("Australia", new ImageIcon("/Users/james/UOW/UOW/UOW Repositories/CSCI213/Tutorials/Lab 7/Exersize 2/src/images/Flag_of_Australia.png"));
        ausFlags.put("NSW", new ImageIcon("/Users/james/UOW/UOW/UOW Repositories/CSCI213/Tutorials/Lab 7/Exersize 2/src/images/Flag_of_New_South_Wales.png"));

        final JLabel image = new JLabel();
        image.setIcon(ausFlags.get("Australia"));


        JPanel imagePanel = new JPanel();
        imagePanel.add(image);

        add(imagePanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.LINE_START);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = list.getSelectedIndex();

                if (selectedIndex == 0) {
                    image.setIcon(ausFlags.get("Australia"));
                } else if (selectedIndex == 1) {
                    image.setIcon(ausFlags.get("NSW"));
                }

            }
        });
    }
}
