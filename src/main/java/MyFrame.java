import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame {

    private static String strOne = "";
    private static String strTwo = "";
    private static double result;

    private String operator = "";
    private static double numOne;
    private static double numTwo;


    private JPanel contentPane;
    private JTextField textLED;

    private List<JButton> buttonList=new ArrayList<>();

    private boolean showHistory =false;
    private JLabel history=new JLabel();
    private String historyStr="";
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - WIDTH) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - HEIGHT) / 2;
        frame.setLocation(w, h);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    public MyFrame() {

        setTitle("仿真计算器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, -57, 461, 738);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(242, 242, 242));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textLED = new JTextField();
        textLED.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        textLED.setBounds(14, 13, 420, 70);
        contentPane.add(textLED);
        textLED.setColumns(10);
        textLED.setHorizontalAlignment(JTextField.RIGHT);

        String[] labelList={"C","0","=","÷","1","2","3","×","4","5","6","-","7","8","9","+","sin","cos","tan","^"};
        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++){
                JButton temp = new JButton();
                temp.setBounds(14+105*j,477-98*i,103,96);
                temp.setText(labelList[i*4+j]);
                if(temp.getText().matches("\\d")){
                    temp.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
                }else{
                    temp.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
                }
                temp.setBackground(new Color(252, 252, 252));
                buttonList.add(temp);
                contentPane.add(temp);
            }
        }
        JButton temp =new JButton();
        temp.setBounds(14,575,412,96);
        temp.setText("历史记录");
        temp.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
        temp.setBackground(new Color(252, 252, 252));
        buttonList.add(temp);
        contentPane.add(temp);

        history.setFont(new Font(Font.DIALOG, Font.BOLD, 24));

        registerListeners();

    }


    public void registerListeners() {
        BtnListener listener = new BtnListener();
        for (JButton jButton : buttonList) {
            jButton.addActionListener(listener);
        }
    }

    class BtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                String actionCommand = e.getActionCommand();
                boolean matches = actionCommand.matches("\\d");
                if (matches) {
                    if (operator == "") {
                        strOne += ((JButton) e.getSource()).getText();
                        textLED.setText(strOne);
                        numOne = Double.parseDouble(strOne);
                    } else {
                        strTwo += ((JButton) e.getSource()).getText();
                        textLED.setText(strTwo);
                        numTwo = Double.parseDouble(strTwo);
                    }
                } else  {
                    switch (actionCommand){
                        case "=":
                            doOperation(operator);
                            strOne = ""+result;
                            numOne = result;
                            strTwo="";
                            numTwo=0;
                            operator = "";
                            break;
                        case "历史记录":
                            showHistory=!showHistory;
                            if(showHistory){
                                setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - WIDTH) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - HEIGHT) / 2, 461, 1000);
                                history.setBounds(14,650,412,320);
                                contentPane.add(history);
                            }else{
                                setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - WIDTH) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - HEIGHT) / 2, 461, 738);
                            }
                            break;
                        case "sin":
                            if(!"".equals(strTwo)){
                                numTwo=Math.sin(numTwo*Math.PI/180);
                                strTwo=numTwo+"";
                                textLED.setText(strTwo);
                            }else if(!"".equals(strOne)){
                                numOne=Math.sin(numOne*Math.PI/180);
                                result=numOne;
                                strOne=numOne+"";
                                textLED.setText(strOne);
                            }
                            break;
                        case "cos":
                            if(!"".equals(strTwo)){
                                numTwo=Math.cos(numTwo*Math.PI/180);
                                strTwo=numTwo+"";
                                textLED.setText(strTwo);
                            }else if(!"".equals(strOne)){
                                numOne=Math.cos(numOne*Math.PI/180);
                                result=numOne;
                                strOne=numOne+"";
                                textLED.setText(strOne);
                            }
                            break;
                        case "tan":
                            if(!"".equals(strTwo)){
                                numTwo=Math.tan(numTwo*Math.PI/180);
                                strTwo=numTwo+"";
                                textLED.setText(strTwo);
                            }else if(!"".equals(strOne)){
                                numOne=Math.tan(numOne*Math.PI/180);
                                result=numOne;
                                strOne=numOne+"";
                                textLED.setText(strOne);
                            }
                            break;

                        case "C":
                            strOne = "";
                            strTwo = "";
                            numOne = 0;
                            numTwo = 0;
                            result = 0;
                            operator = "";
                            historyStr = "";
                            history.setText("<html>"+historyStr+"</html>");
                            textLED.setText("");
                            break;
                        default:
                            if(!"".equals(strTwo)){
                                if(!"".equals(textLED.getText())){
                                    doOperation(operator);
                                }else{
                                    doOperation(actionCommand);
                                }
                                numOne = result;
                                strOne = result + "";
                                strTwo = "";
                            }
                            operator=actionCommand;
                    }
                }
            }
        }

    }
    public void doOperation(String operation){
        switch (operation){
            case "+":result=numOne+numTwo;historyStr=historyStr+"<br/>"+numOne+"+"+numTwo+"="+result;break;
            case "-":result=numOne-numTwo;historyStr=historyStr+"<br/>"+numOne+"-"+numTwo+"="+result;break;
            case "×":result=numOne*numTwo;historyStr=historyStr+"<br/>"+numOne+"×"+numTwo+"="+result;break;
            case "÷":result=numOne/numTwo;historyStr=historyStr+"<br/>"+numOne+"÷"+numTwo+"="+result;break;
            case "^":result=Math.pow(numOne,numTwo);historyStr=historyStr+"<br/>"+numOne+"^"+numTwo+"="+result;break;
            default:
        }
        textLED.setText("" + result);
        history.setText("<html>"+historyStr+"</html>");
    }

}