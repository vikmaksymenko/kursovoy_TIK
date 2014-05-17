package tik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OneToManyTest extends JDialog {

    private ButtonGroup answers;

    private JCheckBox[] checkBox;
    private JButton nextQuestion;
    private JLabel question;
    private String fileName;

    private JFrame parent;

    private int[] correctAnswer = new int[4];

    public OneToManyTest(JFrame frame, String fileName) {

        super(frame, "Test", true);
        this.fileName = fileName;

        question = new JLabel();
        checkBox = new JCheckBox[4]; //варианты для теста на соответствие

        if (!drowTest()) {
            dispose();
            return;
        }

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        questionPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        questionPanel.add(question);

        for (JCheckBox i : checkBox) {
            questionPanel.add(i);
        }

        nextQuestion = new JButton("Next >");
        nextQuestion.addActionListener(new ResultListner());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(BorderLayout.EAST, nextQuestion);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

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
                    new FileReader(new File(fileName)));

            question.setText(reader.readLine());

            for (int i = 0; i < checkBox.length; i++) {
                checkBox[i] = new JCheckBox(reader.readLine());
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

            for (int i = 0; i < checkBox.length; i++) {
                if (checkBox[i].isSelected() && correctAnswer[i] == 0) {
                    testPassed = false;
                    break;
                }
                if (!checkBox[i].isSelected() && correctAnswer[i] == 1) {
                    testPassed = false;
                    break;
                }
            }

            dispose();

            if (testPassed) {
                if (fileName.contains("1")) {
                    new OneToManyTest(parent, "tests/oneToMan2.txt");
                } else {
                    new ManyToManyTest(parent);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Вы совершили ошибку. Повторите теорию.",
                        "Внимание!", JOptionPane.WARNING_MESSAGE);
                TIK.t1.setVisible(true);
            }

        }
    }
}
