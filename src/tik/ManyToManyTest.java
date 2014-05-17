package tik;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ManyToManyTest extends JDialog {

    private JButton nextQuestion;
    private JLabel question;
    private JLabel[] fstCol = new JLabel[4];
    private String[] scndCol = new String[4];
    private JComboBox[] variants = new JComboBox[4];

    private JFrame parent;

    private int[] correctAnswer = new int[4];

    public ManyToManyTest(JFrame frame) {

        super(frame, "Test", true);

        question = new JLabel();

        if (!drowTest()) {
            dispose();
            return;
        }
        
        for (int i = 0; i < variants.length; i++) {
            variants[i] = new JComboBox();
            for (int j = 0; j < scndCol.length; j++) {
                variants[i].addItem(scndCol[j]);
            }
        }

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new GridLayout(4, 3));
        //questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        
//        GridBagConstraints gridBag = new GridBagConstraints();

//        for (int i = 0; i < 4; i++) { //заполнение панели p2
//
//            gridBag.fill = GridBagConstraints.HORIZONTAL;
//            gridBag.gridx = 0;          //позиция ячейки по Х
//            gridBag.gridy = i;          //позиция ячейки по Y
//            questionPanel.add(fstCol[i], gridBag);      //добавление варианта в ячейку (Х,Y)
//            
//            gridBag.gridx++;
//            questionPanel.add(variants[i], gridBag);    //добавление бокса в ячейку
//            
//            gridBag.gridx++;
//            questionPanel.add(scndCol[i], gridBag);
//        }

//        JPanel fstColPanel = new JPanel();
//        JPanel scndColPanel = new JPanel();
//        JPanel variantsPanel = new JPanel();
//        
//        fstColPanel.setLayout(new BoxLayout(fstColPanel, BoxLayout.Y_AXIS));
//        scndColPanel.setLayout(new BoxLayout(scndColPanel, BoxLayout.Y_AXIS));
//        variantsPanel.setLayout(new BoxLayout(variantsPanel, BoxLayout.Y_AXIS));
//        
//        for (int i = 0; i < 4; i++) {
//            fstColPanel.add(fstCol[i]);
//            scndColPanel.add(variants[i]);
//            variantsPanel.add(scndCol[i]);
//        }
//        
//        questionPanel.add(fstColPanel);
//        questionPanel.add(scndColPanel);
//        questionPanel.add(variantsPanel);

        for (int i = 0; i < 4; i++) {
//            JPanel row = new JPanel();
//            row.add(fstCol[i]);
//            row.add(variants[i]);
//            row.add(scndCol[i]);
//            questionPanel.add(row);
            
            questionPanel.add(fstCol[i]);
            questionPanel.add(variants[i]);
        }        
        
        nextQuestion = new JButton("Next >");
        nextQuestion.addActionListener(new ResultListner());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(BorderLayout.EAST, nextQuestion);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        mainPanel.add(question, BorderLayout.NORTH);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(400, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean drowTest() {
        try {
            BufferedReader reader = new BufferedReader( // открытие потока чтения
                    new FileReader(new File("tests/manyToMany.txt")));

            question.setText(reader.readLine());

            for (int i = 0; i < fstCol.length; i++) {
                fstCol[i] = new JLabel(reader.readLine());
            }

            for (int i = 0; i < scndCol.length; i++) {
                scndCol[i] = reader.readLine();
            }

            for (int i = 0; i < correctAnswer.length; i++) {
                correctAnswer[i] = Integer.parseInt(reader.readLine());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Error openning lesson!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public class ResultListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean testPassed = true;

            for (int i = 0; i < variants.length; i++) {
                if ((variants[i].getSelectedIndex() + 1) != correctAnswer[i]) {
                    testPassed = false;
                    break;
                }
            }

            dispose();

            if (testPassed) {
                TIK.p1 = new ProblemExample("Problem", "example.html");
                TIK.t1.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Вы совершили ошибку. Повторите теорию.",
                        "Внимание!", JOptionPane.WARNING_MESSAGE);
                TIK.t1.setVisible(true);
            }

        }
    }
}
