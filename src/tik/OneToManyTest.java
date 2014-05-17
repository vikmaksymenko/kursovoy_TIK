package tik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OneToManyTest extends JDialog implements ActionListener {

    private JCheckBox[] checkBox;                   // массив "галочек"
    private JButton nextQuestion;                   // кнопка "Далее" 
    private JLabel question;                        // вопрос
    private String fileName;                        // имя файла

    private JFrame parent;                          // родительский фрейм

    private int[] correctAnswer = new int[4];       // правильые ответы

    public OneToManyTest(JFrame frame, String fileName) {

        super(frame, "Test", true);                 // вызываем конструктор род.класса

        this.fileName = fileName;

        question = new JLabel();                    // надпись для вопроса
        checkBox = new JCheckBox[4];                //варианты для теста на соответствие

        if (!drowTest()) {                          // если тест не прочитан
            dispose();                              // окно закрывается 
            return;                                 // и прекращаетя вып. констрктора
        }

        JPanel questionPanel = new JPanel();        // создем панель вопросов
        questionPanel.setLayout(new BoxLayout       // и вертикально размещаем элементы
                (questionPanel, BoxLayout.Y_AXIS));

        questionPanel.add(question);                // добавляем на панель надпись

        for (int i = 0; i < checkBox.length; i++) {
            questionPanel.add(checkBox[i]);         // добавляем на панель надпись
        }

        nextQuestion = new JButton("Next >");       // добавляем кнопку далее
        nextQuestion.addActionListener(this);       // устанавливаем обработчиком клика объект этого класса

        JPanel southPanel = new JPanel(new BorderLayout()); // создаем для кнопки панель
        southPanel.add(BorderLayout.EAST, nextQuestion);    // добавляем кнопку "Далее"

        JPanel mainPanel = new JPanel();                    // создаем главную панель
        mainPanel.setLayout(new BorderLayout(10, 10));

        mainPanel.add(questionPanel, BorderLayout.CENTER);  // добалвяем в центр вопросы
        mainPanel.add(southPanel, BorderLayout.SOUTH);      // и вниз панель с кнопкой

        add(mainPanel);                              // добавляем во фрейм главную панель

        setSize(400, 300);                                      // размер
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);     // действие по закрытию
        setLocationRelativeTo(null);                            // позиция на экране
        setVisible(true);                                       // видимость
    }

    private boolean drowTest() {
        try {
            BufferedReader reader = new BufferedReader( // открытие потока чтения
                    new FileReader(new File(fileName)));

            question.setText(reader.readLine());                // чтение вопроса теста  

            for (int i = 0; i < checkBox.length; i++) {
                checkBox[i] = new JCheckBox(reader.readLine()); // и вариантов ответов
            }

            for (int i = 0; i < correctAnswer.length; i++) {
                correctAnswer[i] = 
                        Integer.parseInt(reader.readLine());    // правильные варианты
            }
        } catch (Exception ex) {                                // если файла нет
            JOptionPane.showMessageDialog(                      // выводим сообщение
                    this, "Error openning lesson!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;                                       // возвращаем false
        }

        return true;                                            // иначе - успех
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean testPassed = true;

        // проверяем от противного
        // если состояние переключателя и данные из файла не совпадают - 
        // прерываем цикл, устанавливаем флаг, что тест не пройден
        
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

        dispose();                                      // закрываем диалог

        // если ответ неправильный - выводим сообщение и открываем заново урок
        // иначе - если это первый тест, читаем следующий, 
        // если нет - читаем тест "Один ко многим"
        
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
