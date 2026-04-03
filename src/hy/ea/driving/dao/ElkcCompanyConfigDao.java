package hy.ea.driving.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * mz
 */
public interface ElkcCompanyConfigDao {

    public  void  createTeacherTime(String companyId,String staffID,Date date);


    public void updateTeacherLeave(String companyId,Date date);

}
