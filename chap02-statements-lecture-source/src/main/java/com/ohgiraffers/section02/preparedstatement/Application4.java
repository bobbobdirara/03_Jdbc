package com.ohgiraffers.section02.preparedstatement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application4 {
    public static void main(String[] args) {
        /* 조회할 사원의 성씨를 입력 받아 해당 성씨를 가진 사원 정보들을 모두 출력 */


        Connection con = getConnection(); // 통로 열기

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        EmployeeDTO row = null;
        List<EmployeeDTO> empList = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 이름의 성을 입력하세요 : ");
        String empName = sc.nextLine();

        String query = "SELECT * FROM EMPLOYEE WHERE EMP_NAME LIKE CONCAT(?, %)";

        try {           // ┌트럭에 쿼리 넣어줌
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empName);

            System.out.println("query = " + query);

            rset = pstmt.executeQuery();

            // 조회된 여러 행을 담아놓을 ArrayList 객체 생성
            empList = new ArrayList<>();

            while(rset.next()) {
                // 반복을 실행하며 반복이 실행될 때마다 새로운 EmployeeDTO 객체를 생성해서 새로운 값을 받을 준비를 하는 코드

                row = new EmployeeDTO();

                row.setEmpId(rset.getString("EMP_ID"));
                row.setEmpName(rset.getString("EMP_NAME"));
                row.setEmpNo(rset.getString("EMP_NO"));
                row.setEmail(rset.getString("EMAIL"));
                row.setPhone(rset.getString("PHONE"));
                row.setDeptCode(rset.getString("DEPT_CODE"));
                row.setJobCode(rset.getString("JOB_CODE"));
                row.setSalary(rset.getInt("SALARY"));
                row.setSalLevel(rset.getString("SAL_LEVEL"));
                row.setBonus(rset.getDouble("BONUS"));
                row.setManagerId(rset.getString("MANAGER_ID"));
                row.setHireDate(rset.getDate("HIRE_DATE"));
                row.setEntDate(rset.getDate("ENT_DATE"));
                row.setEntYn(rset.getString("ENT_YN"));

                empList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
