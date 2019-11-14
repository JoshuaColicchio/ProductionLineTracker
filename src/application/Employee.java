package application;

public class Employee {

  StringBuilder name;
  String username;
  String password;
  String email;

  public Employee(String name, String password) {
    this.name = new StringBuilder(name);
    checkName(this.name);
    isValidPassword(password);
  }

  private void setUsername(StringBuilder name) {
    this.username =
        name.toString().toLowerCase().charAt(0)
            + name.toString().substring(name.indexOf(" ") + 1).toLowerCase();
  }

  private void setEmail(StringBuilder name) {
    this.email = name.toString().replace(' ', '.').toLowerCase() + "@oracleacademy.Test";
  }

  private void createUsername(StringBuilder name) {}

  private boolean checkName(StringBuilder name) {
    if (name.indexOf(" ") != -1) {
      // contains a space
      setUsername(name);
      setEmail(name);
    } else {
      // does not contain a space
      username = "default";
      email = "user@oracleacademy.Test";
    }
    return true;
  }

  private void isValidPassword(String password) {
    /*The constructor will call isValidPassword. If the password is valid (containing a lowercase letter, uppercase letter,
    and a special character) the password field gets set to the supplied password. If the password is invalid,
    the password field gets set to "pw".*/

    if (password.matches("/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]")) {
      this.password = password;
    } else {
      this.password = "pw";
    }
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
