package lab1;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;


import com.jgoodies.forms.factories.*;
import lab1.Mappers.NoMapper;
import lab1.utils.MybatisUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

/*
 * Created by JFormDesigner on Fri Mar 24 19:21:15 CST 2023
 */



/**
 * @author lenovo
 */
public class DBFrame extends JFrame {
    public List<JCheckBox> listenBox = new ArrayList<>();
    public List<JTextField> listenField = new ArrayList<>();
    static Map<String,String> kvMap = new HashMap<>();
    public SqlSession sqlSession = MybatisUtil.getSqlSession();
    public NoMapper noMapper= sqlSession.getMapper(NoMapper.class);
    public ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            3,
            5,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            new ThreadPoolExecutor.DiscardPolicy()
    );
    static{
        kvMap.put("Student学号","Student.Snumber");
        kvMap.put("SC课程号","SC.Cnumber");
        kvMap.put("Course学时","Chours");
        kvMap.put("Course教师号","Tnumber");
        kvMap.put("课程号","Cnumber");
        kvMap.put("Course课程名称","Cname");
        kvMap.put("Course学分","Credit");
        kvMap.put("学号","Snumber");
        kvMap.put("SC课程分数","Score");
        kvMap.put("Student姓名","Sname");
        kvMap.put("Student班级","SClass");
        kvMap.put("Student性别","Ssex");
        kvMap.put("Student年龄","Sage");
    }
    public String table="Course";
    public DBFrame() {
        setTitle("战神数据库实验一");
        initComponents();
        initTables();
        TitleDropDownItemStateChanged();
    }
    private void initTables(){
        threadPool.execute(()->{initModel(CourseTable,"Course",noMapper);});
        threadPool.execute(()->{initModel(SCTable,"SC",noMapper);});
        threadPool.execute(()->{initModel(StudentTable,"Student",noMapper);});
    }
    private  void initModel(JTable table,String tableName,NoMapper noMapper) {
        List<Map<String, Object>> maps = noMapper.optionalSelect("select * from "+tableName);
        DefaultTableModel tableModel= new DefaultTableModel();
        ArrayList<String> columns = new ArrayList<>(maps.get(0).keySet());
        ArrayList<Object> data = new ArrayList<>();
        for(Map<String,Object> map:maps){
            for(String key:columns){
                if(tableModel.getColumnCount()!=map.size()){
                    tableModel.addColumn(key);
                }
                data.add(map.get(key));
            }
            tableModel.addRow(data.toArray());
            data.clear();
        }
        table.setModel(tableModel);
    }
    public static void main(String[] args) {
        DBFrame frame = new DBFrame();
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - WIDTH) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - HEIGHT) / 2;
        frame.setLocation(w, h);
        frame.setVisible(true);
        frame.setResizable(true);
    }
    private void TitleDropDownItemStateChanged(ItemEvent e) {
        table=(String)e.getItem();
        TitleDropDownItemStateChanged();

    }
    private void TitleDropDownItemStateChanged() {
        SelectPanel.removeAll();
        listenBox.clear();
        listenField.clear();
        if("Course".equals(table)){
            SelectPanel.add(CoursePanel);
            listenBox.add(CourseBox1);
            listenBox.add(CourseBox2);
            listenBox.add(CourseBox3);
            listenBox.add(CourseBox4);
            listenBox.add(CourseBox5);
            listenField.add(CourseField1);
            listenField.add(CourseField2);
            listenField.add(CourseField3);
            listenField.add(CourseField4);
            listenField.add(CourseField5);
        }else if("SC".equals(table)){
            SelectPanel.add(SCPanel);
            listenBox.add(SCBox1);
            listenBox.add(SCBox2);
            listenBox.add(SCBox3);
            listenField.add(SCField1);
            listenField.add(SCField2);
            listenField.add(SCField3);
        }else if("Student".equals(table)){
            SelectPanel.add(StudentPanel);
            listenBox.add(StudentBox1);
            listenBox.add(StudentBox2);
            listenBox.add(StudentBox3);
            listenBox.add(StudentBox4);
            listenBox.add(StudentBox5);
            listenField.add(StudentField1);
            listenField.add(StudentField2);
            listenField.add(StudentField3);
            listenField.add(StudentField4);
            listenField.add(StudentField5);
        }else if("多表".equals(table)){
            SelectPanel.add(JoinPanel);
            listenBox.add(CourseBox7);
            listenBox.add(CourseBox8);
            listenBox.add(CourseBox9);
            listenBox.add(CourseBox10);
            listenBox.add(SCBox4);
            listenBox.add(SCBox6);
            listenBox.add(StudentBox6);
            listenBox.add(StudentBox7);
            listenBox.add(StudentBox8);
            listenBox.add(StudentBox9);
            listenBox.add(StudentBox10);
            listenField.add(CourseField7);
            listenField.add(CourseField8);
            listenField.add(CourseField9);
            listenField.add(CourseField10);
            listenField.add(SCField4);
            listenField.add(SCField6);
            listenField.add(StudentField6);
            listenField.add(StudentField7);
            listenField.add(StudentField8);
            listenField.add(StudentField9);
            listenField.add(StudentField10);

        }
        SelectPanel.validate();
        SelectPanel.repaint();
    }


    private void Generate(){
        StringBuffer select = new StringBuffer();
        Map<String,String> mss = new HashMap<>();
        HashSet<String> tables = new HashSet<>();
        for(JCheckBox jCheckBox:listenBox){
            if(jCheckBox.isSelected()){
                select.append(kvMap.get((jCheckBox.getToolTipText()==null?"":jCheckBox.getToolTipText())+jCheckBox.getText())).append(", ");
                if(TitleDropDown.getSelectedIndex()==3){
                    tables.add(jCheckBox.getToolTipText());
                }
            }
        }
        for(JTextField jTextField:listenField){
            if(!jTextField.getText().matches(" *")){
                mss.put(jTextField.getToolTipText(),jTextField.getText());
            }
        }
        StringBuffer where = new StringBuffer();
        if(tables.contains("Course")&&tables.contains("Student")){
            table = "Course, Student, SC";
            where.append("Course.Cnumber=SC.Cnumber AND SC.Snumber=Student.Snumber AND ");
        }else if(tables.contains("Course")&&tables.contains("SC")){
            table = "Course, SC";
            where.append("Course.Cnumber=SC.Cnumber AND ");
        }else if(tables.contains("Student")&&tables.contains("SC")){
            table = "Student, SC";
            where.append("SC.Snumber=Student.Snumber AND ");
        }else{
            for(String s:tables){
                table=s;
            }
        }
        for(Map.Entry<String,String> e:mss.entrySet()){
            where.append("( ");
            String value = e.getValue();
            String[] ss = value.split("; ");
            for(int i = 0;i<ss.length;i++){
                String s = ss[i];
                where.append("( ");
                where.append(e.getKey()).append(" ");
                if(s.contains(", ")){
                    if(s.split(", ").length!=2||s.contains("%")||s.contains("_")){
                        JOptionPane.showMessageDialog(null, "在填写筛选条件时，如果想要表示从A到B，则需要填写为A, B，其中A和B为某个具体值", "筛选条件错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    where.append("BETWEEN ");
                    where.append(s.split(", ")[0]).append(" ");
                    where.append("AND ");
                    where.append(s.split(", ")[1]).append(" ");
                }else if(s.contains(";")||s.contains(",")){
                    JOptionPane.showMessageDialog(null, "在填写筛选条件时，如果想要表示从A到B，则需要填写为A, B；如果想要表示A或者B，则需要填写为A; B。\n提示：逗号和分号均为英文，并且后面都带有一个空格。", "筛选条件错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if(s.contains("%")||s.contains("_")){
                    where.append("LIKE ");
                    where.append("\"").append(s).append("\"").append(" ");
                }else if(s.matches("[\u4e00-\u9fa5A-Za-z0-9]+|([0-9]+\\.[0-9]+)")){
                    where.append("= ");
                    where.append("\"").append(s).append("\"").append(" ");
                }else{
                    JOptionPane.showMessageDialog(null, "在填写筛选条件时，如果想要表示从A到B，则需要填写为A, B；如果想要表示A或者B，则需要填写为A; B。\n提示：请检查在填写时是否出现了非法字符。", "筛选条件错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                where.append(") ");
                if(i!=ss.length-1){
                    where.append("OR ");
                }
            }
            where.append(") ");
            where.append("AND ");
        }
        if(select.length()==0){
            JOptionPane.showMessageDialog(null, "请选择至少一项内容进行查询", "操作错误", JOptionPane.ERROR_MESSAGE);
        }else{
            String sqlString ="SELECT "+select.substring(0,select.length()-2)+" FROM "+table+(where.length()>0?" WHERE "+where.substring(0,where.length()-4):"");
            SQLArea.setText(sqlString);
        }


    }

    private void Select() {
        if(!SQLArea.getText().toLowerCase().matches("select +(.*) +from +([^;]+) *(| where +[^;]+)")){
            JOptionPane.showMessageDialog(null, "SQL语句不符合程序要求，请修改为select...from...where...的格式。", "SQL语句格式错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String select = SQLArea.getText().substring(SQLArea.getText().toUpperCase().indexOf("SELECT")+6,SQLArea.getText().toUpperCase().indexOf("FROM"));
        String from = SQLArea.getText().substring(SQLArea.getText().toUpperCase().indexOf("FROM")+4,SQLArea.getText().toUpperCase().contains("WHERE")?SQLArea.getText().toUpperCase().indexOf("WHERE"):SQLArea.getText().length());
        String where = SQLArea.getText().toUpperCase().contains("WHERE")?SQLArea.getText().substring(SQLArea.getText().toUpperCase().indexOf("WHERE")+5):"true";
        List<Object> ls=new ArrayList<>();
        DefaultTableModel tableModel = new DefaultTableModel();
        List<String> columns ;
        String sqlString = "";
        try {
            if("true".equals(where)){
                sqlString = "SELECT "+select+" FROM " +from;
            }else{
                sqlString = "SELECT "+select+" FROM " +from+" WHERE "+where;
            }
            List<Map<String, Object>> maps = noMapper.optionalSelect(sqlString);
            columns=new ArrayList<>(noMapper.optionalSelect("SELECT "+select+" FROM "+from+" limit 0,1").get(0).keySet());
                for(String c:columns){
                    tableModel.addColumn(c);
                }
                for(Map<String,Object> map:maps){
                    for(String c:columns){
                        ls.add(map.get(c));
                    }
                    tableModel.addRow(ls.toArray());
                    ls.clear();
                }
        } catch (PersistenceException e) {
            JOptionPane.showMessageDialog(null, "SQL语句语法错误，请检查SQL语句中出现的所有列名是否为所选表的列名，以及筛选条件的关键字是否错误", "SQL语句执行错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ResultTable.setModel(tableModel);
    }






    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("DB");
        SCPanel = new JPanel();
        SCPanel1 = new JPanel();
        SCBox1 = new JCheckBox();
        SCField1 = new JTextField();
        SCBox2 = new JCheckBox();
        SCField2 = new JTextField();
        SCBox3 = new JCheckBox();
        SCField3 = new JTextField();
        StudentPanel = new JPanel();
        StudentPanel1 = new JPanel();
        StudentBox1 = new JCheckBox();
        StudentField1 = new JTextField();
        StudentBox2 = new JCheckBox();
        StudentField2 = new JTextField();
        StudentBox3 = new JCheckBox();
        StudentField3 = new JTextField();
        StudentPanel2 = new JPanel();
        StudentBox4 = new JCheckBox();
        StudentField4 = new JTextField();
        StudentBox5 = new JCheckBox();
        StudentField5 = new JTextField();
        CoursePanel = new JPanel();
        CoursePanel1 = new JPanel();
        CourseBox1 = new JCheckBox();
        CourseField1 = new JTextField();
        CourseBox2 = new JCheckBox();
        CourseField2 = new JTextField();
        CourseBox3 = new JCheckBox();
        CourseField3 = new JTextField();
        CoursePanel2 = new JPanel();
        CourseBox4 = new JCheckBox();
        CourseField4 = new JTextField();
        CourseBox5 = new JCheckBox();
        CourseField5 = new JTextField();
        JoinPanel = new JPanel();
        JoinPanel1 = new JPanel();
        CoursePanel4 = new JPanel();
        CourseBox7 = new JCheckBox();
        CourseField7 = new JTextField();
        CourseBox8 = new JCheckBox();
        CourseField8 = new JTextField();
        CoursePanel5 = new JPanel();
        CourseBox9 = new JCheckBox();
        CourseField9 = new JTextField();
        CourseBox10 = new JCheckBox();
        CourseField10 = new JTextField();
        JoinPanel2 = new JPanel();
        SCPanel3 = new JPanel();
        SCBox4 = new JCheckBox();
        SCField4 = new JTextField();
        SCBox6 = new JCheckBox();
        SCField6 = new JTextField();
        JoinPanel3 = new JPanel();
        StudentPanel4 = new JPanel();
        StudentBox6 = new JCheckBox();
        StudentField6 = new JTextField();
        StudentBox7 = new JCheckBox();
        StudentField7 = new JTextField();
        StudentBox8 = new JCheckBox();
        StudentField8 = new JTextField();
        StudentPanel5 = new JPanel();
        StudentBox9 = new JCheckBox();
        StudentField9 = new JTextField();
        StudentBox10 = new JCheckBox();
        StudentField10 = new JTextField();
        panel1 = new JPanel();
        TitlePanel = new JPanel();
        TitleLabel = new JLabel();
        TitleDropDown = new JComboBox<>();
        SelectButton = new JButton();
        SelectButton2 = new JButton();
        SelectPanel = new JPanel();
        panel2 = new JPanel();
        ResultPanel = new JPanel();
        scrollPane2 = new JScrollPane();
        ResultTable = new JTable();
        SQLPanel = new JPanel();
        SQLArea = new JTextArea();
        panel3 = new JPanel();
        panel4 = new JPanel();
        scrollPane1 = new JScrollPane();
        CourseTable = new JTable();
        panel5 = new JPanel();
        scrollPane3 = new JScrollPane();
        SCTable = new JTable();
        panel6 = new JPanel();
        scrollPane4 = new JScrollPane();
        StudentTable = new JTable();

        //======== SCPanel ========
        {
            SCPanel.setLayout(new BorderLayout());

            //======== SCPanel1 ========
            {
                SCPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- SCBox1 ----
                SCBox1.setText("\u8bfe\u7a0b\u53f7");
                SCPanel1.add(SCBox1);

                //---- SCField1 ----
                SCField1.setColumns(4);
                SCField1.setToolTipText("Cnumber");
                SCPanel1.add(SCField1);

                //---- SCBox2 ----
                SCBox2.setText("\u5b66\u53f7");
                SCPanel1.add(SCBox2);

                //---- SCField2 ----
                SCField2.setColumns(8);
                SCField2.setToolTipText("Snumber");
                SCPanel1.add(SCField2);

                //---- SCBox3 ----
                SCBox3.setText("\u8bfe\u7a0b\u5206\u6570");
                SCBox3.setToolTipText("SC");
                SCPanel1.add(SCBox3);

                //---- SCField3 ----
                SCField3.setColumns(4);
                SCField3.setToolTipText("Score");
                SCPanel1.add(SCField3);
            }
            SCPanel.add(SCPanel1, BorderLayout.CENTER);
        }

        //======== StudentPanel ========
        {
            StudentPanel.setLayout(new BorderLayout());

            //======== StudentPanel1 ========
            {
                StudentPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- StudentBox1 ----
                StudentBox1.setText("\u59d3\u540d");
                StudentBox1.setToolTipText("Student");
                StudentPanel1.add(StudentBox1);

                //---- StudentField1 ----
                StudentField1.setColumns(4);
                StudentField1.setToolTipText("Sname");
                StudentPanel1.add(StudentField1);

                //---- StudentBox2 ----
                StudentBox2.setText("\u5b66\u53f7");
                StudentPanel1.add(StudentBox2);

                //---- StudentField2 ----
                StudentField2.setColumns(8);
                StudentField2.setToolTipText("Snumber");
                StudentPanel1.add(StudentField2);

                //---- StudentBox3 ----
                StudentBox3.setText("\u73ed\u7ea7");
                StudentBox3.setToolTipText("Student");
                StudentPanel1.add(StudentBox3);

                //---- StudentField3 ----
                StudentField3.setColumns(4);
                StudentField3.setToolTipText("SClass");
                StudentPanel1.add(StudentField3);
            }
            StudentPanel.add(StudentPanel1, BorderLayout.CENTER);

            //======== StudentPanel2 ========
            {
                StudentPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- StudentBox4 ----
                StudentBox4.setText("\u6027\u522b");
                StudentBox4.setToolTipText("Student");
                StudentPanel2.add(StudentBox4);

                //---- StudentField4 ----
                StudentField4.setColumns(4);
                StudentField4.setToolTipText("Ssex");
                StudentPanel2.add(StudentField4);

                //---- StudentBox5 ----
                StudentBox5.setText("\u5e74\u9f84");
                StudentBox5.setToolTipText("Student");
                StudentPanel2.add(StudentBox5);

                //---- StudentField5 ----
                StudentField5.setColumns(4);
                StudentField5.setToolTipText("Sage");
                StudentPanel2.add(StudentField5);
            }
            StudentPanel.add(StudentPanel2, BorderLayout.NORTH);
        }

        //======== CoursePanel ========
        {
            CoursePanel.setBackground(new Color(0x666666));
            CoursePanel.setLayout(new BorderLayout());

            //======== CoursePanel1 ========
            {
                CoursePanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- CourseBox1 ----
                CourseBox1.setText("\u8bfe\u7a0b\u53f7");
                CoursePanel1.add(CourseBox1);

                //---- CourseField1 ----
                CourseField1.setColumns(4);
                CourseField1.setToolTipText("Cnumber");
                CoursePanel1.add(CourseField1);

                //---- CourseBox2 ----
                CourseBox2.setText("\u8bfe\u7a0b\u540d\u79f0");
                CourseBox2.setToolTipText("Course");
                CoursePanel1.add(CourseBox2);

                //---- CourseField2 ----
                CourseField2.setColumns(8);
                CourseField2.setToolTipText("Cname");
                CoursePanel1.add(CourseField2);

                //---- CourseBox3 ----
                CourseBox3.setText("\u5b66\u5206");
                CourseBox3.setToolTipText("Course");
                CoursePanel1.add(CourseBox3);

                //---- CourseField3 ----
                CourseField3.setColumns(4);
                CourseField3.setToolTipText("Credit");
                CoursePanel1.add(CourseField3);
            }
            CoursePanel.add(CoursePanel1, BorderLayout.CENTER);

            //======== CoursePanel2 ========
            {
                CoursePanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- CourseBox4 ----
                CourseBox4.setText("\u5b66\u65f6");
                CourseBox4.setToolTipText("Course");
                CoursePanel2.add(CourseBox4);

                //---- CourseField4 ----
                CourseField4.setColumns(4);
                CourseField4.setToolTipText("Chours");
                CoursePanel2.add(CourseField4);

                //---- CourseBox5 ----
                CourseBox5.setText("\u6559\u5e08\u53f7");
                CourseBox5.setToolTipText("Course");
                CoursePanel2.add(CourseBox5);

                //---- CourseField5 ----
                CourseField5.setColumns(4);
                CourseField5.setToolTipText("Tnumber");
                CoursePanel2.add(CourseField5);
            }
            CoursePanel.add(CoursePanel2, BorderLayout.NORTH);
        }

        //======== JoinPanel ========
        {
            JoinPanel.setLayout(new BorderLayout());

            //======== JoinPanel1 ========
            {
                JoinPanel1.setBorder(new CompoundBorder(
                    new TitledBorder(bundle.getString("JoinPanel1.border")),
                    Borders.DLU2));
                JoinPanel1.setLayout(new BorderLayout());

                //======== CoursePanel4 ========
                {
                    CoursePanel4.setLayout(new FlowLayout(FlowLayout.LEFT));

                    //---- CourseBox7 ----
                    CourseBox7.setText("\u8bfe\u7a0b\u540d\u79f0");
                    CourseBox7.setToolTipText("Course");
                    CoursePanel4.add(CourseBox7);

                    //---- CourseField7 ----
                    CourseField7.setColumns(8);
                    CourseField7.setToolTipText("Cname");
                    CoursePanel4.add(CourseField7);

                    //---- CourseBox8 ----
                    CourseBox8.setText("\u5b66\u5206");
                    CourseBox8.setToolTipText("Course");
                    CoursePanel4.add(CourseBox8);

                    //---- CourseField8 ----
                    CourseField8.setColumns(4);
                    CourseField8.setToolTipText("Credit");
                    CoursePanel4.add(CourseField8);
                }
                JoinPanel1.add(CoursePanel4, BorderLayout.CENTER);

                //======== CoursePanel5 ========
                {
                    CoursePanel5.setLayout(new FlowLayout(FlowLayout.LEFT));

                    //---- CourseBox9 ----
                    CourseBox9.setText("\u5b66\u65f6");
                    CourseBox9.setToolTipText("Course");
                    CoursePanel5.add(CourseBox9);

                    //---- CourseField9 ----
                    CourseField9.setColumns(4);
                    CourseField9.setToolTipText("Chours");
                    CoursePanel5.add(CourseField9);

                    //---- CourseBox10 ----
                    CourseBox10.setText("\u6559\u5e08\u53f7");
                    CourseBox10.setToolTipText("Course");
                    CoursePanel5.add(CourseBox10);

                    //---- CourseField10 ----
                    CourseField10.setColumns(4);
                    CourseField10.setToolTipText("Tnumber");
                    CoursePanel5.add(CourseField10);
                }
                JoinPanel1.add(CoursePanel5, BorderLayout.NORTH);
            }
            JoinPanel.add(JoinPanel1, BorderLayout.CENTER);

            //======== JoinPanel2 ========
            {
                JoinPanel2.setBorder(new CompoundBorder(
                    new TitledBorder(bundle.getString("JoinPanel2.border")),
                    Borders.DLU2));
                JoinPanel2.setLayout(new BorderLayout());

                //======== SCPanel3 ========
                {
                    SCPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));

                    //---- SCBox4 ----
                    SCBox4.setText("\u8bfe\u7a0b\u53f7");
                    SCBox4.setToolTipText("SC");
                    SCPanel3.add(SCBox4);

                    //---- SCField4 ----
                    SCField4.setColumns(4);
                    SCField4.setToolTipText("Cnumber");
                    SCPanel3.add(SCField4);

                    //---- SCBox6 ----
                    SCBox6.setText("\u8bfe\u7a0b\u5206\u6570");
                    SCBox6.setToolTipText("SC");
                    SCPanel3.add(SCBox6);

                    //---- SCField6 ----
                    SCField6.setColumns(4);
                    SCField6.setToolTipText("Score");
                    SCPanel3.add(SCField6);
                }
                JoinPanel2.add(SCPanel3, BorderLayout.CENTER);
            }
            JoinPanel.add(JoinPanel2, BorderLayout.NORTH);

            //======== JoinPanel3 ========
            {
                JoinPanel3.setBorder(new CompoundBorder(
                    new TitledBorder(bundle.getString("JoinPanel3.border")),
                    Borders.DLU2));
                JoinPanel3.setLayout(new BorderLayout());

                //======== StudentPanel4 ========
                {
                    StudentPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));

                    //---- StudentBox6 ----
                    StudentBox6.setText("\u59d3\u540d");
                    StudentBox6.setToolTipText("Student");
                    StudentPanel4.add(StudentBox6);

                    //---- StudentField6 ----
                    StudentField6.setColumns(4);
                    StudentField6.setToolTipText("Sname");
                    StudentPanel4.add(StudentField6);

                    //---- StudentBox7 ----
                    StudentBox7.setText("\u5b66\u53f7");
                    StudentBox7.setToolTipText("Student");
                    StudentPanel4.add(StudentBox7);

                    //---- StudentField7 ----
                    StudentField7.setColumns(8);
                    StudentField7.setToolTipText("Snumber");
                    StudentPanel4.add(StudentField7);

                    //---- StudentBox8 ----
                    StudentBox8.setText("\u73ed\u7ea7");
                    StudentBox8.setToolTipText("Student");
                    StudentPanel4.add(StudentBox8);

                    //---- StudentField8 ----
                    StudentField8.setColumns(4);
                    StudentField8.setToolTipText("SClass");
                    StudentPanel4.add(StudentField8);
                }
                JoinPanel3.add(StudentPanel4, BorderLayout.CENTER);

                //======== StudentPanel5 ========
                {
                    StudentPanel5.setLayout(new FlowLayout(FlowLayout.LEFT));

                    //---- StudentBox9 ----
                    StudentBox9.setText("\u6027\u522b");
                    StudentBox9.setToolTipText("Student");
                    StudentPanel5.add(StudentBox9);

                    //---- StudentField9 ----
                    StudentField9.setColumns(4);
                    StudentField9.setToolTipText("Ssex");
                    StudentPanel5.add(StudentField9);

                    //---- StudentBox10 ----
                    StudentBox10.setText("\u5e74\u9f84");
                    StudentBox10.setToolTipText("Student");
                    StudentPanel5.add(StudentBox10);

                    //---- StudentField10 ----
                    StudentField10.setColumns(4);
                    StudentField10.setToolTipText("Sage");
                    StudentPanel5.add(StudentField10);
                }
                JoinPanel3.add(StudentPanel5, BorderLayout.NORTH);
            }
            JoinPanel.add(JoinPanel3, BorderLayout.SOUTH);
        }

        //======== this ========
        setBackground(UIManager.getColor("Button.darkShadow"));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //======== TitlePanel ========
            {
                TitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- TitleLabel ----
                TitleLabel.setText(bundle.getString("TitleLabel.text"));
                TitlePanel.add(TitleLabel);

                //---- TitleDropDown ----
                TitleDropDown.setMaximumRowCount(4);
                TitleDropDown.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Course",
                    "SC",
                    "Student",
                    "\u591a\u8868"
                }));
                TitleDropDown.setSelectedIndex(0);
                TitleDropDown.addItemListener(e -> {
			TitleDropDownItemStateChanged();
			TitleDropDownItemStateChanged(e);
		});
                TitlePanel.add(TitleDropDown);

                //---- SelectButton ----
                SelectButton.setText("\u6784\u9020SQL\u8bed\u53e5");
                SelectButton.addActionListener(e -> Generate());
                TitlePanel.add(SelectButton);

                //---- SelectButton2 ----
                SelectButton2.setText("\u8fd0\u884cSQL\u8bed\u53e5");
                SelectButton2.addActionListener(e -> Select());
                TitlePanel.add(SelectButton2);
            }
            panel1.add(TitlePanel, BorderLayout.NORTH);

            //======== SelectPanel ========
            {
                SelectPanel.setBorder(new CompoundBorder(
                    new TitledBorder("\u9009\u9879"),
                    Borders.DLU2));
                SelectPanel.setLayout(new BorderLayout(5, 0));
            }
            panel1.add(SelectPanel, BorderLayout.CENTER);

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout());

                //======== ResultPanel ========
                {
                    ResultPanel.setBorder(new TitledBorder(bundle.getString("ResultPanel.border")));
                    ResultPanel.setLayout(new BorderLayout());

                    //======== scrollPane2 ========
                    {

                        //---- ResultTable ----
                        ResultTable.setPreferredScrollableViewportSize(new Dimension(430, 400));
                        scrollPane2.setViewportView(ResultTable);
                    }
                    ResultPanel.add(scrollPane2, BorderLayout.CENTER);
                }
                panel2.add(ResultPanel, BorderLayout.SOUTH);

                //======== SQLPanel ========
                {
                    SQLPanel.setBorder(new TitledBorder(bundle.getString("SQLPanel.border")));
                    SQLPanel.setLayout(new BorderLayout());

                    //---- SQLArea ----
                    SQLArea.setTabSize(4);
                    SQLArea.setRows(5);
                    SQLArea.setColumns(39);
                    SQLArea.setLineWrap(true);
                    SQLPanel.add(SQLArea, BorderLayout.CENTER);
                }
                panel2.add(SQLPanel, BorderLayout.CENTER);
            }
            panel1.add(panel2, BorderLayout.SOUTH);
        }
        contentPane.add(panel1);

        //======== panel3 ========
        {
            panel3.setLayout(new BorderLayout());

            //======== panel4 ========
            {
                panel4.setBorder(new TitledBorder("Course\u8868"));
                panel4.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {

                    //---- CourseTable ----
                    CourseTable.setPreferredScrollableViewportSize(new Dimension(450, 200));
                    scrollPane1.setViewportView(CourseTable);
                }
                panel4.add(scrollPane1, BorderLayout.CENTER);
            }
            panel3.add(panel4, BorderLayout.NORTH);

            //======== panel5 ========
            {
                panel5.setBorder(new TitledBorder(bundle.getString("panel5.border")));
                panel5.setLayout(new BorderLayout());

                //======== scrollPane3 ========
                {

                    //---- SCTable ----
                    SCTable.setPreferredScrollableViewportSize(new Dimension(450, 200));
                    scrollPane3.setViewportView(SCTable);
                }
                panel5.add(scrollPane3, BorderLayout.CENTER);
            }
            panel3.add(panel5, BorderLayout.CENTER);

            //======== panel6 ========
            {
                panel6.setBorder(new TitledBorder(bundle.getString("panel6.border")));
                panel6.setLayout(new BorderLayout());

                //======== scrollPane4 ========
                {

                    //---- StudentTable ----
                    StudentTable.setPreferredScrollableViewportSize(new Dimension(450, 200));
                    scrollPane4.setViewportView(StudentTable);
                }
                panel6.add(scrollPane4, BorderLayout.CENTER);
            }
            panel3.add(panel6, BorderLayout.SOUTH);
        }
        contentPane.add(panel3);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel SCPanel;
    private JPanel SCPanel1;
    private JCheckBox SCBox1;
    private JTextField SCField1;
    private JCheckBox SCBox2;
    private JTextField SCField2;
    private JCheckBox SCBox3;
    private JTextField SCField3;
    private JPanel StudentPanel;
    private JPanel StudentPanel1;
    private JCheckBox StudentBox1;
    private JTextField StudentField1;
    private JCheckBox StudentBox2;
    private JTextField StudentField2;
    private JCheckBox StudentBox3;
    private JTextField StudentField3;
    private JPanel StudentPanel2;
    private JCheckBox StudentBox4;
    private JTextField StudentField4;
    private JCheckBox StudentBox5;
    private JTextField StudentField5;
    private JPanel CoursePanel;
    private JPanel CoursePanel1;
    private JCheckBox CourseBox1;
    private JTextField CourseField1;
    private JCheckBox CourseBox2;
    private JTextField CourseField2;
    private JCheckBox CourseBox3;
    private JTextField CourseField3;
    private JPanel CoursePanel2;
    private JCheckBox CourseBox4;
    private JTextField CourseField4;
    private JCheckBox CourseBox5;
    private JTextField CourseField5;
    private JPanel JoinPanel;
    private JPanel JoinPanel1;
    private JPanel CoursePanel4;
    private JCheckBox CourseBox7;
    private JTextField CourseField7;
    private JCheckBox CourseBox8;
    private JTextField CourseField8;
    private JPanel CoursePanel5;
    private JCheckBox CourseBox9;
    private JTextField CourseField9;
    private JCheckBox CourseBox10;
    private JTextField CourseField10;
    private JPanel JoinPanel2;
    private JPanel SCPanel3;
    private JCheckBox SCBox4;
    private JTextField SCField4;
    private JCheckBox SCBox6;
    private JTextField SCField6;
    private JPanel JoinPanel3;
    private JPanel StudentPanel4;
    private JCheckBox StudentBox6;
    private JTextField StudentField6;
    private JCheckBox StudentBox7;
    private JTextField StudentField7;
    private JCheckBox StudentBox8;
    private JTextField StudentField8;
    private JPanel StudentPanel5;
    private JCheckBox StudentBox9;
    private JTextField StudentField9;
    private JCheckBox StudentBox10;
    private JTextField StudentField10;
    private JPanel panel1;
    private JPanel TitlePanel;
    private JLabel TitleLabel;
    private JComboBox<String> TitleDropDown;
    private JButton SelectButton;
    private JButton SelectButton2;
    private JPanel SelectPanel;
    private JPanel panel2;
    private JPanel ResultPanel;
    private JScrollPane scrollPane2;
    private JTable ResultTable;
    private JPanel SQLPanel;
    private JTextArea SQLArea;
    private JPanel panel3;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JTable CourseTable;
    private JPanel panel5;
    private JScrollPane scrollPane3;
    private JTable SCTable;
    private JPanel panel6;
    private JScrollPane scrollPane4;
    private JTable StudentTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
