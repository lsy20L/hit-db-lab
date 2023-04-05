import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import Converters.*;
import Validators.*;
import com.jgoodies.forms.factories.*;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
/*
 * Created by JFormDesigner on Fri Mar 24 20:36:19 CST 2023
 */



/**
 * @author lenovo
 */
public class DBFrame extends JFrame {
    public DBFrame() {
        initComponents();
    }

    private void TitleDropDownItemStateChanged() {
        // TODO add your code here
    }

    private void TitleDropDownItemStateChanged(ItemEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("DB");
        SCPanel = new JPanel();
        SCPanel1 = new JPanel();
        checkBox16 = new JCheckBox();
        textField17 = new JTextField();
        checkBox17 = new JCheckBox();
        textField18 = new JTextField();
        checkBox18 = new JCheckBox();
        textField19 = new JTextField();
        StudentPanel = new JPanel();
        StudentPanel1 = new JPanel();
        checkBox19 = new JCheckBox();
        textField20 = new JTextField();
        checkBox20 = new JCheckBox();
        textField21 = new JTextField();
        checkBox21 = new JCheckBox();
        textField22 = new JTextField();
        StudentPanel2 = new JPanel();
        checkBox22 = new JCheckBox();
        textField23 = new JTextField();
        checkBox23 = new JCheckBox();
        textField24 = new JTextField();
        TitlePanel = new JPanel();
        TitleLabel = new JLabel();
        TitleDropDown = new JComboBox();
        SelectButton = new JButton();
        GapPanel1 = new JPanel();
        SelectPanel = new JPanel();
        CoursePanel = new JPanel();
        CoursePanel1 = new JPanel();
        checkBox11 = new JCheckBox();
        textField12 = new JTextField();
        checkBox12 = new JCheckBox();
        textField13 = new JTextField();
        checkBox13 = new JCheckBox();
        textField14 = new JTextField();
        CoursePanel2 = new JPanel();
        checkBox14 = new JCheckBox();
        textField15 = new JTextField();
        checkBox15 = new JCheckBox();
        textField16 = new JTextField();
        SQLPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== SCPanel ========
        {
            SCPanel.setLayout(new BorderLayout());

            //======== SCPanel1 ========
            {
                SCPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- checkBox16 ----
                checkBox16.setText("\u8bfe\u7a0b\u53f7");
                SCPanel1.add(checkBox16);

                //---- textField17 ----
                textField17.setColumns(4);
                SCPanel1.add(textField17);

                //---- checkBox17 ----
                checkBox17.setText("\u5b66\u53f7");
                SCPanel1.add(checkBox17);

                //---- textField18 ----
                textField18.setColumns(8);
                SCPanel1.add(textField18);

                //---- checkBox18 ----
                checkBox18.setText("\u8bfe\u7a0b\u5206\u6570");
                SCPanel1.add(checkBox18);

                //---- textField19 ----
                textField19.setColumns(4);
                SCPanel1.add(textField19);
            }
            SCPanel.add(SCPanel1, BorderLayout.CENTER);
        }

        //======== StudentPanel ========
        {
            StudentPanel.setLayout(new BorderLayout());

            //======== StudentPanel1 ========
            {
                StudentPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- checkBox19 ----
                checkBox19.setText("\u59d3\u540d");
                StudentPanel1.add(checkBox19);

                //---- textField20 ----
                textField20.setColumns(4);
                StudentPanel1.add(textField20);

                //---- checkBox20 ----
                checkBox20.setText("\u5b66\u53f7");
                StudentPanel1.add(checkBox20);

                //---- textField21 ----
                textField21.setColumns(8);
                StudentPanel1.add(textField21);

                //---- checkBox21 ----
                checkBox21.setText("\u73ed\u7ea7");
                StudentPanel1.add(checkBox21);

                //---- textField22 ----
                textField22.setColumns(4);
                StudentPanel1.add(textField22);
            }
            StudentPanel.add(StudentPanel1, BorderLayout.CENTER);

            //======== StudentPanel2 ========
            {
                StudentPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- checkBox22 ----
                checkBox22.setText("\u6027\u522b");
                StudentPanel2.add(checkBox22);

                //---- textField23 ----
                textField23.setColumns(4);
                StudentPanel2.add(textField23);

                //---- checkBox23 ----
                checkBox23.setText("\u5e74\u9f84");
                StudentPanel2.add(checkBox23);

                //---- textField24 ----
                textField24.setColumns(4);
                StudentPanel2.add(textField24);
            }
            StudentPanel.add(StudentPanel2, BorderLayout.NORTH);
        }

        //======== this ========
        setBackground(UIManager.getColor("Button.darkShadow"));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        //======== TitlePanel ========
        {
            TitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- TitleLabel ----
            TitleLabel.setText(bundle.getString("TitleLabel.text"));
            TitlePanel.add(TitleLabel);

            //---- TitleDropDown ----
            TitleDropDown.setMaximumRowCount(3);
            TitleDropDown.setModel(new DefaultComboBoxModel(new String[] {
                "Course",
                "SC",
                "Student"
            }));
            TitleDropDown.setSelectedIndex(0);
            TitleDropDown.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    TitleDropDownItemStateChanged();
                    TitleDropDownItemStateChanged(e);
                }
            });
            TitlePanel.add(TitleDropDown);

            //---- SelectButton ----
            SelectButton.setText("\u5f00\u59cb\u67e5\u8be2");
            TitlePanel.add(SelectButton);
        }
        contentPane.add(TitlePanel);

        //======== GapPanel1 ========
        {
            GapPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 100, 5));
        }
        contentPane.add(GapPanel1);

        //======== SelectPanel ========
        {
            SelectPanel.setBorder(new CompoundBorder(
                new TitledBorder("\u9009\u9879"),
                Borders.DLU2));
            SelectPanel.setLayout(new BorderLayout());

            //======== CoursePanel ========
            {
                CoursePanel.setBackground(new Color(0x666666));
                CoursePanel.setLayout(new BorderLayout());

                //======== CoursePanel1 ========
                {
                    CoursePanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

                    //---- checkBox11 ----
                    checkBox11.setText("\u8bfe\u7a0b\u53f7");
                    CoursePanel1.add(checkBox11);

                    //---- textField12 ----
                    textField12.setColumns(4);
                    CoursePanel1.add(textField12);

                    //---- checkBox12 ----
                    checkBox12.setText("\u8bfe\u7a0b\u540d\u79f0");
                    CoursePanel1.add(checkBox12);

                    //---- textField13 ----
                    textField13.setColumns(8);
                    CoursePanel1.add(textField13);

                    //---- checkBox13 ----
                    checkBox13.setText("\u5b66\u5206");
                    CoursePanel1.add(checkBox13);

                    //---- textField14 ----
                    textField14.setColumns(4);
                    CoursePanel1.add(textField14);
                }
                CoursePanel.add(CoursePanel1, BorderLayout.CENTER);

                //======== CoursePanel2 ========
                {
                    CoursePanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                    //---- checkBox14 ----
                    checkBox14.setText("\u5b66\u65f6");
                    CoursePanel2.add(checkBox14);

                    //---- textField15 ----
                    textField15.setColumns(4);
                    CoursePanel2.add(textField15);

                    //---- checkBox15 ----
                    checkBox15.setText("\u6559\u5e08\u540d\u79f0");
                    CoursePanel2.add(checkBox15);

                    //---- textField16 ----
                    textField16.setColumns(4);
                    CoursePanel2.add(textField16);
                }
                CoursePanel.add(CoursePanel2, BorderLayout.NORTH);
            }
            SelectPanel.add(CoursePanel, BorderLayout.SOUTH);
        }
        contentPane.add(SelectPanel);

        //======== SQLPanel ========
        {
            SQLPanel.setBorder(new TitledBorder(bundle.getString("SQLPanel.border")));
            SQLPanel.setLayout(new BorderLayout());

            //======== scrollPane1 ========
            {

                //---- textArea1 ----
                textArea1.setTabSize(4);
                textArea1.setEditable(false);
                textArea1.setRows(5);
                textArea1.setColumns(69);
                scrollPane1.setViewportView(textArea1);
            }
            SQLPanel.add(scrollPane1, BorderLayout.NORTH);
        }
        contentPane.add(SQLPanel);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel SCPanel;
    private JPanel SCPanel1;
    private JCheckBox checkBox16;
    private JTextField textField17;
    private JCheckBox checkBox17;
    private JTextField textField18;
    private JCheckBox checkBox18;
    private JTextField textField19;
    private JPanel StudentPanel;
    private JPanel StudentPanel1;
    private JCheckBox checkBox19;
    private JTextField textField20;
    private JCheckBox checkBox20;
    private JTextField textField21;
    private JCheckBox checkBox21;
    private JTextField textField22;
    private JPanel StudentPanel2;
    private JCheckBox checkBox22;
    private JTextField textField23;
    private JCheckBox checkBox23;
    private JTextField textField24;
    private JPanel TitlePanel;
    private JLabel TitleLabel;
    private JComboBox TitleDropDown;
    private JButton SelectButton;
    private JPanel GapPanel1;
    private JPanel SelectPanel;
    private JPanel CoursePanel;
    private JPanel CoursePanel1;
    private JCheckBox checkBox11;
    private JTextField textField12;
    private JCheckBox checkBox12;
    private JTextField textField13;
    private JCheckBox checkBox13;
    private JTextField textField14;
    private JPanel CoursePanel2;
    private JCheckBox checkBox14;
    private JTextField textField15;
    private JCheckBox checkBox15;
    private JTextField textField16;
    private JPanel SQLPanel;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
