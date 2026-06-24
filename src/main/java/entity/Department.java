package entity;

public class Department {
    private Integer id;
    private String deptName;
    private String deptDesc;

    public Department() {}

    public Department(String deptName, String deptDesc) {
        this.deptName = deptName;
        this.deptDesc = deptDesc;
    }

    // getter & setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getDeptDesc() { return deptDesc; }
    public void setDeptDesc(String deptDesc) { this.deptDesc = deptDesc; }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", deptDesc='" + deptDesc + '\'' +
                '}';
    }
}