package carSharing.db;

import carSharing.model.Car;
import carSharing.model.Company;
import carSharing.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbClient {
    private final DataSource dataSource;

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run(String str) {
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement();
        ) {
            con.setAutoCommit(true);
            stmt.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Company selectCompany(String query) {
        List<Company> companies = selectCompaniesForList(query);
        if (companies.size() == 1)
            return companies.get(0);
        else if (companies.isEmpty())
            return null;
        else
            throw new IllegalStateException("Query returned more than one object");
        }

    public List<Company> selectCompaniesForList(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Company company = new Company(id, name);
                companies.add(company);
            }

            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }

    public Car selectCar(String query) {
        List<Car> cars = selectCarsForList(query);
        if (cars.size() == 1)
            return cars.get(0);
        else if (cars.isEmpty())
            return null;
        else
            throw new IllegalStateException("Query returned more than one object");
    }

    public List<Car> selectCarsForList(String query) {
        List<Car> cars = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                int companyId = resultSetItem.getInt("company_id");
                Car car = new Car(id, name, companyId);
                cars.add(car);
            }

            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public List<Customer> selectCustomersForList(String query) {
        List<Customer> customers = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Customer customer = new Customer(id, name);
                customers.add(customer);
            }

            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}