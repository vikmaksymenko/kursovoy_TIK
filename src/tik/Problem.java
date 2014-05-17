package tik;

import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

public class Problem extends JFrame implements ActionListener {

    private JTextField answer;                          // поле ответа
    private JTextPane textArea;                         // панель вывода условия

    public Problem() {
        setTitle("Решение задачи");                     // заголовок
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // действие по закрытии
        setSize(800, 600);                              // размер

        textArea = new JTextPane();                    //текстовое поле
        textArea.setEditable(false);                   //нельзя менять
        textArea.setContentType("text/html");          //отображение html

        JScrollPane scroller = new JScrollPane(textArea);// скролл 

        scroller.setVerticalScrollBarPolicy(            // только вертикальный
                ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(
                ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, scroller);             //добавление окна с текстом

        JPanel southPanel = new JPanel();               // созаем панель для кнопки и текстового поля

        answer = new JTextField(20);                    // добаляем в нее текстовое поле
        southPanel.add(answer);

        JButton testButton = new JButton("Далее");
        testButton.addActionListener(this);             //добавление события для кнопки
        southPanel.add(testButton);                     //добавление кнопки в панель

        add(BorderLayout.SOUTH, southPanel);            // добалляем фрейму вниз панель ответа и кнопки

        try {
            textArea.setText(readTheory("problem.html"));     // открываем урок
            setVisible(true);                                 // отрисовываем условие
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Error openning lesson!",         // если файла нет - выводим сообщение
                    "Error", JOptionPane.ERROR_MESSAGE);
            dispose();                                      // и закрываем окно
        }
    }

    // чтение теории
    private String readTheory(String fileName) throws Exception {
        String text = "", buffer;

        BufferedReader reader = new BufferedReader( // открытие потока чтения
                new FileReader(new File(fileName)));

        while ((buffer = reader.readLine()) != null) {  // построчное чтение файла
            text += buffer;
        }
        return text;
    }

    // обработка действия
    public void actionPerformed(ActionEvent e) {
        try {
            BufferedReader reader = new BufferedReader( // открытие потока чтения
                    new FileReader(new File("tests/problem.txt")));

            // если ответ верный - выводим сообщение об успехе
            // иначе - вывод сообщения и возврат к примеру задачи
            
            if (answer.getText().equals(reader.readLine())) {
                JOptionPane.showMessageDialog(          
                        this, "Тест пройден",
                        "Поздравляем!", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        this, "Тест не пройден. Просмотрите пример",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                TIK.p1.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Error openning problem!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        }
        dispose();
    }
}
