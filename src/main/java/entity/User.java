package entity;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private Integer empId;

    public User() {}

    public User(String username, String password, String role, Integer empId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.empId = empId;
    }

    // getter & setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Integer getEmpId() { return empId; }
    public void setEmpId(Integer empId) { this.empId = empId; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}