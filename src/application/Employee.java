package application;

/**
 * Class that represents an Employee.
 *
 * @author Joshua Colicchio
 */
class Employee {

  private final StringBuilder name;
  private final String password;
  private String username;
  private String email;

  /**
   * Constructor for a new Employee object.
   *
   * @param name The Employee's full name.
   * @param password The Employee's password.
   */
  public Employee(String name, String password) {
    this.name = new StringBuilder(name);
    if (checkName(this.name)) {
      setUsername(this.name);
      setEmail(this.name);
    } else {
      username = "default";
      email = "user@oracleacademy.Test";
    }

    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * Method that sets / creates the Employee's username.
   *
   * @param name The Employee's name.
   */
  private void setUsername(StringBuilder name) {
    this.username =
        name.toString().toLowerCase().charAt(0)
            + name.toString().substring(name.indexOf(" ") + 1).toLowerCase();
  }

  /**
   * Method that sets / creates the Employee's email address.
   *
   * @param name The Employee's name.
   */
  private void setEmail(StringBuilder name) {
    this.email = name.toString().replace(' ', '.').toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * Method that checks whether or not the Employee has entered a valid name.
   *
   * @param name The name to check.
   * @return True if the name is valid.
   */
  private boolean checkName(StringBuilder name) {
    return name.indexOf(" ") != -1;
  }

  /**
   * Method that checks whether or not the Employee has entered a valid password.
   *
   * @param password The password that the Employee has entered.
   * @return Returns true if the password is valid.
   */
  private boolean isValidPassword(String password) {
    return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).+$");
  }

  public String toString() {
    return "Employee Details\nName : "
        + name.toString()
        + "\nUsername : "
        + username
        + "\nEmail : "
        + email
        + "\nInitial Password : "
        + password;
  }
}
