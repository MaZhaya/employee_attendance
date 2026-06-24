import entity.Attendance;
import mapper.AttendanceMapper;
import org.junit.jupiter.api.Test;
import utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class AttendanceMapperTest {

    // 1. 测试上班打卡（添加）
    @Test
    public void testCheckIn() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            AttendanceMapper mapper = session.getMapper(AttendanceMapper.class);

            Attendance att = new Attendance();
            att.setEmpId(1);
            att.setCheckDate(new Date(System.currentTimeMillis()));
            att.setCheckIn(new Time(System.currentTimeMillis()));
            att.setStatus("正常");

            mapper.checkIn(att);
            System.out.println("打卡成功");
        }
    }

    // 2. 测试下班签退（修改）
    @Test
    public void testCheckOut() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            AttendanceMapper mapper = session.getMapper(AttendanceMapper.class);
            mapper.checkOut(1);
            System.out.println("签退成功");
        }
    }

    // 3. 测试查询今日考勤
    @Test
    public void testGetToday() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            AttendanceMapper mapper = session.getMapper(AttendanceMapper.class);
            Attendance att = mapper.getTodayAttendance(1);
            System.out.println(att);
            System.out.println("员工ID=1的今日考勤查询成功!");
        }
    }

    // 4. 测试根据员工ID查考勤
    @Test
    public void testListByEmpId() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            AttendanceMapper mapper = session.getMapper(AttendanceMapper.class);
            List<Attendance> list = mapper.listByEmpId(1);
            list.forEach(System.out::println);
            System.out.println("员工ID=1的考勤查询成功!");
        }
    }

    // 5. 测试查询所有考勤
    @Test
    public void testListAll() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            AttendanceMapper mapper = session.getMapper(AttendanceMapper.class);
            List<Attendance> list = mapper.listAll();
            list.forEach(System.out::println);
            System.out.println("所有考勤查询成功!");
        }
    }

    // 6.测试删除考勤
    @Test
    public void testDelete() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            AttendanceMapper mapper = session.getMapper(AttendanceMapper.class);
            mapper.deleteById(9); // 删除 id=9的考勤
            System.out.println("删除成功");
        }
    }

    // 7.测试查询所有考勤，并带出员工信息
    @Test
    public void testListAllWithEmployee() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            AttendanceMapper mapper = session.getMapper(AttendanceMapper.class);
            List<Attendance> list = mapper.listAllWithEmployee();

            for (Attendance att : list) {
                System.out.println("考勤信息：" + att);
                System.out.println("关联员工：" + att.getEmployee());
                System.out.println("--------------------");
            }
        }
    }
}