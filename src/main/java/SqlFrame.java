import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SqlFrame extends JFrame{
    private JComboBox<String> tableBox;
    private JPanel totalPane;
    private JPanel upperPane;
    private JPanel selectPane;
    private JLabel tableLable ;

    public static void main(String[] args) {
        SqlFrame frame = new SqlFrame();
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - WIDTH) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - HEIGHT) / 2;
        frame.setLocation(w, h);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public SqlFrame() {

        setTitle("SQL生成器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, -57, 561, 738);
        totalPane = new JPanel();
        upperPane = new JPanel();
        selectPane = new JPanel();
        totalPane.setBackground(new Color(242, 242, 242));
        setContentPane(totalPane);
        totalPane.setLayout(null);



        tableLable=new JLabel("表");
        tableLable.setBounds(10,-38,100,100);
        tableLable.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        totalPane.add(tableLable);


        tableBox = new JComboBox<>();
        tableBox.addItem("course");
        tableBox.addItem("student");
        tableBox.addItem("sc");
        tableBox.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        tableBox.setBounds(35,3,100,20);
        totalPane.add(tableBox);

        selectPane.setBackground(new Color(198, 195, 198));
        selectPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        totalPane.add(selectPane);
        selectPane.setBounds(10,28,525,130);

        upperPane.setBackground(new Color(198, 195, 198));
        totalPane.add(upperPane);
        upperPane.setBounds(5,-37,535,200);




        registerListeners();

    }


    public void registerListeners() {
        SqlFrame.BtnListener listener = new SqlFrame.BtnListener();
    }

    class BtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }

    }
}
