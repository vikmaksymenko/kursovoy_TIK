package tik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ManyToManyTest extends JDialog implements ActionListener {

    private JButton nextQuestion;                           // кнопка "Далее" 
    private JLabel question;                                // вопрос
    private JLabel[] fstCol = new JLabel[4];                // первая колонка вариантов
    private String[] scndCol = new String[4];               // вторая колонка вариантов
    private JComboBox[] variants = new JComboBox[4];        // массив выпадающих списков

    private int[] correctAnswer = new int[4];               // список правильных ответов

    public ManyToManyTest(JFrame frame) {

        super(frame, "Test", true);                         // вызываем конструктор род.класса

        question = new JLabel();                            // надпись для вопроса

        if (!drowTest()) {                                  // если тест не прочитан
            dispose();                                      // окно закрывается 
            return;                                         // и прекращаетя вып. констрктора
        }

        // проходим по массиву выпадающих списков
        // и присваиваем им новые объекты с данными второго столбца
        
        for (int i = 0; i < variants.length; i++) {
            variants[i] = new JComboBox();                  
            for (int j = 0; j < scndCol.length; j++) {
                variants[i].addItem(scndCol[j]);
            }
        }

        JPanel questionPanel = new JPanel();                // создаем паель вариантов
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));      // устанавливаем табличный компоновщик
        
        for (int i = 0; i < 4; i++) {
            JPanel tmp = new JPanel();
            tmp.setLayout(new BorderLayout());
            tmp.add(fstCol[i], BorderLayout.CENTER);                   // добавляем в нее варианты
            tmp.add(variants[i], BorderLayout.EAST);                 // и списки
            questionPanel.add(tmp);
        }

        nextQuestion = new JButton("Next >");               // добавляем кнопку далее       
        nextQuestion.addActionListener(this);               // устанавливаем обработчиком клика объект этого класса

        JPanel southPanel = new JPanel(new BorderLayout()); // создаем для кнопки панель
        southPanel.add(BorderLayout.EAST, nextQuestion);    // добавляем кнопку "Далее"

        JPanel mainPanel = new JPanel();                    // создаем главную панель
        mainPanel.setLayout(new BorderLayout(10, 10));

        mainPanel.add(question, BorderLayout.NORTH);        // добавляем наверх вопрос
        mainPanel.add(questionPanel, BorderLayout.CENTER);  // и в центр варианты ответов
        mainPanel.add(southPanel, BorderLayout.SOUTH);      // и вниз панель с кнопкой

        add(mainPanel);                                     // добавляем во фрейм главную панель

       // setSize(400, 300);                                  // размер
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // действие по закрытию
        setLocationRelativeTo(null);                        // позиция на экране
        pack();                                             // подгон диалога к нужному размеру 
        setVisible(true);                                   // видимость
    }

    private boolean drowTest() {
        try {
            BufferedReader reader = new BufferedReader(     // открытие потока чтения
                    new FileReader(new File("tests/manyToMany.txt")));

            question.setText(reader.readLine());            // чтение вопроса теста  

            for (int i = 0; i < fstCol.length; i++) {
                fstCol[i] = new JLabel(reader.readLine());  // первой колонки
            }

            for (int i = 0; i < scndCol.length; i++) {
                scndCol[i] = reader.readLine();             // второй колонки
            }

            for (int i = 0; i < correctAnswer.length; i++) {
                correctAnswer[i] =                          // правильных вариантов
                        Integer.parseInt(reader.readLine());
            }
        } catch (Exception ex) {                            // если файла нет
            JOptionPane.showMessageDialog(                  // выводим сообщение
                    this, "Error openning lesson!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;                                   // возвращаем false
        }

        return true;                                        // иначе - успех
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean testPassed = true;

        // проверяем от противного
        // если состояние переключателя и данные из файла не совпадают - 
        // прерываем цикл, устанавливаем флаг, что тест не пройден
        
        for (int i = 0; i < variants.length; i++) {
            if ((variants[i].getSelectedIndex() + 1) != correctAnswer[i]) {
                testPassed = false;
                break;
            }
        }

        dispose();                                      // закрываем диалог

        // если ответ неправильный - выводим сообщение и открываем заново урок
        // иначе - переходим к примеру задачи и закрываем окно вывода лекции 
        
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
