package tik;

import java.awt.event.ActionEvent;

public class ProblemExample extends Browser{

    public ProblemExample(String title, String fileName) {
        super(title, fileName);             // вызов конструктора род.класса
    }

    // обработка действия
    public void actionPerformed(ActionEvent e) {
        setVisible(false);                  //скрытие окна с примером
        new Problem();                      //создание окна с условием задачи
    }
}
