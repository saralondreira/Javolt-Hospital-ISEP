import Servicos.GestaoHospital;
import UI.Menu;

public class Main {
    public static void main(String[] args) {
        GestaoHospital gestaoHospital = new GestaoHospital();
        Menu menu = new Menu(gestaoHospital);
        menu.start();
    }
}