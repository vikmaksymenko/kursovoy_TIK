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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OneToOneTest extends JDialog implements ActionListener {

    private ButtonGroup answers;

    private JRadioButton fstButton, scndButton, thrdButton;
    private JButton nextQuestion;
    private JLabel question;
    private String fileName;

    private JFrame parent;

    private int correctAnswer;

    public OneToOneTest(JFrame frame, String fileName) {

        super(frame, "Test", true);

        this.fileName = fileName;                       // сохраняем имя файла с тестом

        question = new JLabel();                        // создаем надпись с вопросом
        fstButton = new JRadioButton();                 // и переключатели
        scndButton = new JRadioButton();
        thrdButton = new JRadioButton();

        if (!drowTest(fileName)) {                      // если тест не прочитан
            dispose();                                  // окно закрывается 
            return;                                     // и прекращаетя вып. констрктора
        }

        JPanel questionPanel = new JPanel();            // создем панель вопросов
        questionPanel.setLayout(new BoxLayout(          // и вертикаль размещаем элементы
                questionPanel, BoxLayout.Y_AXIS));

        questionPanel.add(question);                    // добавляем на панель кнопки и надпись
        questionPanel.add(fstButton);
        questionPanel.add(scndButton);
        questionPanel.add(thrdButton);

        answers = new ButtonGroup();                    // создаем группу для переключателей
        answers.add(fstButton);                         // чтобы можно было нажать только на 1
        answers.add(scndButton);
        answers.add(thrdButton);

        nextQuestion = new JButton("Next >");           // добавляем кнопку далее
        nextQuestion.addActionListener(this);           // устанавливаем обработчиком клика объект этого класса

        JPanel southPanel = new JPanel(new BorderLayout());     // создаем для кнопки панель
        southPanel.add(BorderLayout.EAST, nextQuestion);

        JPanel mainPanel = new JPanel();                // создаем главную панель
        mainPanel.setLayout(new BorderLayout(10, 10));

        mainPanel.add(questionPanel, BorderLayout.CENTER);      // добалвяем в центр вопросы
        mainPanel.add(southPanel, BorderLayout.SOUTH);          // и вниз панель с кнопкой

        add(mainPanel);                                 // добавляем во фрейм главную панель

        setSize(400, 300);                                      // размер
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);     // действие по закрытию
        setLocationRelativeTo(null);                            // позиция на экране
        setVisible(true);                                       // видимость
    }

    private boolean drowTest(String fileName) {
        try {
            BufferedReader reader = new BufferedReader( // открытие потока чтения
                    new FileReader(new File(fileName)));

            question.setText(reader.readLine());        // чтение вопроса теста
            fstButton.setText(reader.readLine());       // и вариантов ответа
            scndButton.setText(reader.readLine());
            thrdButton.setText(reader.readLine());
            
            correctAnswer = Integer.parseInt(reader.readLine());    // прав. ответ
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(                 //если файла нет - 
                    this, "Error openning lesson!",        //выводим сообщение
                    "Error", JOptionPane.ERROR_MESSAGE);   
            return false;
        }

        return true;
    }

    private void checkAnswer(int answer) {
        dispose();

        // если ответ неправильный - выводим сообщение и открываем заново урок
        // иначе - если это первый тест, читаем следующий, 
        // если нет - читаем тест "Один ко многим"
        
        if (answer != correctAnswer) {
            JOptionPane.showMessageDialog(null, "Вы совершили ошибку. Повторите теорию.",
                    "Внимание!", JOptionPane.WARNING_MESSAGE);
            TIK.t1.setVisible(true);
        } else if (fileName.contains("1")) {
            new OneToOneTest(parent, "tests/oneToOne2.txt");
        } else {
            new OneToManyTest(parent, "tests/oneToMan1.txt");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // передаем функции проверки номер выбранной кнопки
        
        if (fstButton.isSelected()) {
            checkAnswer(1);
        }
        if (scndButton.isSelected()) {
            checkAnswer(2);
        }
        if (thrdButton.isSelected()) {
            checkAnswer(3);
        }
    }
}
