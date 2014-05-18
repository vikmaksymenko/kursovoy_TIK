package tik;

public class TIK {

    static Theory t1;                                       //окно теории
    static ProblemExample p1;                               //окно примера задачи

    public static void main(String[] args) {
        t1 = new Theory("Метод Хаффмена", "content.html");    //вывод теории
    }
}
