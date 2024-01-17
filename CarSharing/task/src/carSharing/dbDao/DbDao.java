package carSharing.dbDao;

import carSharing.dao.CarDao;
import carSharing.dao.CompanyDao;
import carSharing.dao.CustomerDao;
import carSharing.db.DbClient;
import carSharing.model.Car;
import carSharing.model.Company;
import carSharing.model.Customer;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

import static carSharing.db.SqlQueryConstants.*;

public class DbDao implements CompanyDao, CarDao, CustomerDao {
    private final DbClient dbClient;

    public DbDao() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(CONNECTION_URL);

        dbClient = new DbClient(dataSource);
        dbClient.run(CREATE_COMPANY_TABLE);
        dbClient.run(CREATE_CAR_TABLE);
        dbClient.run(CREATE_CUSTOMER_TABLE);
    }

//    DbCompanyDao --------------------------------
    @Override
    public List<Company> findAllCompanies() {
        return dbClient.selectCompaniesForList(SELECT_ALL_COMPANIES);
    }

    @Override
    public Company findCompanyById(int id) {
        return dbClient.selectCompany(String.format(SELECT_COMPANY, id));
    }

    @Override
    public void addCompany(Company company) {
        dbClient.run(String.format(INSERT_COMPANY, company.id(), company.name()));
    }

//    DbCarDao ----------------------------------
    @Override
    public List<Car> findCars() {
        return dbClient.selectCarsForList(SELECT_ALL_CARS);
    }

    @Override
    public List<Car> findAllCarsInCompany(Company company) {
        return dbClient.selectCarsForList(String.format(SELECT_ALL_CARS_IN_COMPANY, company.id()));
    }

    @Override
    public List<Car> findFreeCarsInCompany(Company company) {
        return dbClient.selectCarsForList(String.format(SELECT_ALL_FREE_CARS_IN_COMPANY, company.id()));
    }

    @Override
    public Car findRentedCustomerCar(Customer customer) {
        return dbClient.selectCar(String.format(SELECT_RENTED_CUSTOMER_CAR, customer.id()));
    }

    @Override
    public void addCar(Car car) {
        dbClient.run(String.format(INSERT_CAR, car.id(), car.name(), car.companyId()));
    }

//    DbCustomerDao --------------------------------
    @Override
    public List<Customer> findAllCustomers() {
        return dbClient.selectCustomersForList(SELECT_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomerRentedCarIdToNull(Customer customer) {
        dbClient.run(String.format(SET_CUSTOMER_RENTED_CAR_ID_TO_NULL, customer.id()));
    }

    @Override
    public void setCustomerRentedCarId(Customer customer, Car car) {
        dbClient.run(String.format(SET_CUSTOMER_RENTED_CAR_ID, car.id(), customer.id()));
    }

    @Override
    public void addCustomer(Customer customer) {
        dbClient.run(String.format(INSERT_CUSTOMER, customer.id(), customer.name()));
    }
}
