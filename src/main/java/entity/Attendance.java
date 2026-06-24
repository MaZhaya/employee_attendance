package entity;
import java.sql.Date;
import java.sql.Time;

public class Attendance {
    private Integer id;
    private Integer empId;
    private Date checkDate;
    private Time checkIn;
    private Time checkOut;
    private String status;
    private String remark;

    // ===================== 关键：多对一关联 =====================
    private Employee employee;  // 多对一：一条考勤对应一个员工

    public Attendance() {}

    // getter & setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getEmpId() { return empId; }
    public void setEmpId(Integer empId) { this.empId = empId; }
    public Date getCheckDate() { return checkDate; }
    public void setCheckDate(Date checkDate) { this.checkDate = checkDate; }
    public Time getCheckIn() { return checkIn; }
    public void setCheckIn(Time checkIn) { this.checkIn = checkIn; }
    public Time getCheckOut() { return checkOut; }
    public void setCheckOut(Time checkOut) { this.checkOut = checkOut; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    // ===================== 关联对象的 getter/setter =====================
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", empId=" + empId +
                ", checkDate=" + checkDate +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", status='" + status + '\'' +
                ", remark='" + remark +
                '}';
    }
}