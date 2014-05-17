package tik;

import java.awt.BorderLayout;
import javax.swing.*;
import java.io.*;
import java.awt.event.ActionListener;

public abstract class Browser extends JFrame implements ActionListener {

    private JTextPane textArea;

    // создание и заполнение окна
    public Browser(String title, String fileName) {
        setTitle(title);                                //установка заголовка окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выход по закрытии окна
        setSize(800, 600);                              //размер окна

        textArea = new JTextPane();                    //текстовое поле
        textArea.setEditable(false);                   //нельзя менять
        textArea.setContentType("text/html");          //отображение html

        JScrollPane scroller = new JScrollPane(textArea);   //полоса прокрутки урока

        scroller.setVerticalScrollBarPolicy(
                ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(
                ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, scroller);             //добавление текстового поля

        JButton testButton = new JButton("Далее");
        testButton.addActionListener(this);             //добавление события для кнопки
        add(BorderLayout.SOUTH, testButton);            //добавление кнопки
        
        try {
            textArea.setText(readTheory(fileName));     //открываем урок
            setVisible(true);                           //отрисовываем окно
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(              //если файла нет - 
                    this, "Error openning lesson!",     //выводим сообщение
                    "Error", JOptionPane.ERROR_MESSAGE);
            dispose();                                  //и закрываем окно
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
}
