package tik;

import java.net.URL;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

public class Problem extends JFrame implements ActionListener {

    private JTextField answer;
    private JTextPane textArea;

    public Problem() {
        setTitle("Решение задачи");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        textArea = new JTextPane();                    //текстовое поле
        textArea.setEditable(false);                   //нельзя менять
        textArea.setContentType("text/html");          //отображение html

        JScrollPane scroller = new JScrollPane(textArea);

        scroller.setVerticalScrollBarPolicy(
                ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(
                ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, scroller);             //добавление окна с текстом

        JPanel southPanel = new JPanel();

        answer = new JTextField(20);
        southPanel.add(answer);

        JButton testButton = new JButton("Далее");
        testButton.addActionListener(this);             //добавление события для кнопки
        southPanel.add(testButton);            //добавление кнопки

        add(BorderLayout.SOUTH, southPanel);

        try {
            textArea.setText(readTheory("problem.html"));     //открываем урок
            setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Error openning lesson!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            dispose();                                  //если файла нет - закрываем окно
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
