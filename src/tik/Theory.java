package tik;

import java.awt.event.ActionEvent;

//окно вывода теории

public class Theory extends Browser {

    public Theory(String title, String fileName) {
        super(title, fileName);
    }

    // обработка действия
    public void actionPerformed(ActionEvent e) {
        setVisible(false);                               //скрытие теории
        new OneToOneTest(this, "tests/oneToOne1.txt");   //открытие окна с тестами
    }
}
