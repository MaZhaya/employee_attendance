package entity;
import java.sql.Date;

public class LeaveApply {
    private Integer id;
    private Integer empId;
    private String leaveType;
    private Date startTime;
    private Date endTime;
    private String reason;
    private String approveStatus;

    public LeaveApply() {}

    // getter & setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getEmpId() { return empId; }
    public void setEmpId(Integer empId) { this.empId = empId; }
    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getApproveStatus() { return approveStatus; }
    public void setApproveStatus(String approveStatus) { this.approveStatus = approveStatus; }

    @Override
    public String toString() {
        return "LeaveApply{" +
                "id=" + id +
                ", empId=" + empId +
                ", leaveType='" + leaveType + '\'' +
                ", approveStatus='" + approveStatus + '\'' +
                '}';
    }
}