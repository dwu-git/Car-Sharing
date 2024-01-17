package carSharing.db;

public class SqlQueryConstants {
    public static final String CONNECTION_URL = "jdbc:h2:./CarSharing/task/src/carSharing/db/carSharingDatabase";

    public static final String CREATE_COMPANY_TABLE = "CREATE TABLE IF NOT EXISTS COMPANY " +
            "(id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL UNIQUE)";

    public static final String CREATE_CAR_TABLE = "CREATE TABLE IF NOT EXISTS CAR " +
            "(id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL UNIQUE, " +
            "company_id INT NOT NULL, " +
            "CONSTRAINT fk_company_id FOREIGN KEY (company_id) " +
            "REFERENCES company (id) " +
            "ON UPDATE CASCADE " +
            "ON DELETE CASCADE)";

    public static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
            "(id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL UNIQUE, " +
            "rented_car_id INT DEFAULT NULL, " +
            "CONSTRAINT fk_rented_car_id FOREIGN KEY (rented_car_id) " +
            "REFERENCES car (id) " +
            "ON UPDATE CASCADE " +
            "ON DELETE CASCADE)";

    public static final String SELECT_ALL_COMPANIES = "SELECT * FROM COMPANY ORDER BY id";

    public static final String SELECT_COMPANY = "SELECT * FROM COMPANY WHERE id = %d";

    public static final String SELECT_ALL_CARS = "SELECT * FROM CAR ORDER BY id";

    public static final String SELECT_ALL_CARS_IN_COMPANY = "SELECT * FROM CAR WHERE company_id = %d";

    public static final String SELECT_ALL_FREE_CARS_IN_COMPANY = "SELECT * FROM CAR WHERE company_id = %d AND id NOT IN (SELECT rented_car_id FROM CUSTOMER WHERE rented_car_id IS NOT NULL)";

    public static final String SELECT_RENTED_CUSTOMER_CAR = "SELECT * FROM CAR WHERE id = (SELECT rented_car_id FROM CUSTOMER WHERE id = %d)";

    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER ORDER BY id";

    public static final String SET_CUSTOMER_RENTED_CAR_ID = "UPDATE CUSTOMER SET rented_car_id = %d WHERE id = %d";

    public static final String SET_CUSTOMER_RENTED_CAR_ID_TO_NULL = "UPDATE CUSTOMER SET rented_car_id = NULL WHERE id = %d";

    public static final String INSERT_COMPANY = "INSERT INTO COMPANY VALUES (%d, '%s')";

    public static final String INSERT_CAR = "INSERT INTO CAR VALUES (%d, '%s', %d)";

    public static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMER VALUES (%d, '%s', NULL)";

}
