class Record {

    String employeesSector;
    String employeesNumber;
    String employeesSurname;
    int employeesWage;

    public Record(String employeesSector, String employeesNumber, String employeesSurname, int employeesWage) {

        this.employeesSector = employeesSector;
        this.employeesNumber = employeesNumber;
        this.employeesSurname = employeesSurname;
        this.employeesWage = employeesWage;

    }

    public String getEmployeesSector() {

        return employeesSector;

    }

    public int getEmployeesWage() {

        return employeesWage;

    }

    @Override
    public String toString() {

        return employeesSector + "\n" +
                employeesNumber + "\n" +
                employeesSurname + "\n" +
                employeesWage + "\n";

    }

}
