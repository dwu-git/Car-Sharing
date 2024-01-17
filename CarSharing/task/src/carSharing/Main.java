package carSharing;

import carSharing.dbDao.DbDao;
import carSharing.menu.Menu;

public class Main {

    public static void main(String[] args) {
        DbDao dbDao = new DbDao();
        new Menu(dbDao).mainMenu();
    }
}