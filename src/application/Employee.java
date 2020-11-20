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

  private void setUsername(StringBuilder name) {
    this.username =
        name.toString().toLowerCase().charAt(0)
            + name.substring(name.indexOf(" ") + 1).toLowerCase();
  }

  private void setEmail(StringBuilder name) {
    this.email = name.toString().replace(' ', '.').toLowerCase() + "@oracleacademy.Test";
  }

  private boolean checkName(StringBuilder name) {
    return name.indexOf(" ") != -1;
  }

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
