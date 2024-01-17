package carSharing.dao;

import carSharing.model.Company;

import java.util.List;

public interface CompanyDao {
    List<Company> findAllCompanies();
    Company findCompanyById(int id);
    void addCompany(Company company);
}
