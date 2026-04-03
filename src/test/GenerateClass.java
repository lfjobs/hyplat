package test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;

public class GenerateClass {
    static HashMap<String, String> map = new HashMap<>();

    static {
        map.put("VARCHAR2", "String");
        map.put("TIMESTAMP(6)", "Date");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("开始。。。。。。。。。。。。。。");
        String tableName = "DT_COST_BUDGET_ITEM";
        String className = "CostBudgetItem";
        String packName = "hy.ea.bo.finance";
        String idKey = "BUDGET_ITEM_KEY";
        String tableText = "费用预算条目";

        getEntity(tableName, className, packName, idKey, tableText);
        System.out.println("结束。。。。。。。。。。。。。。");
    }


    public static void getEntity(String tableName, String className, String packName, String idKey, String tableText) throws ClassNotFoundException {
        String url = "jdbc:oracle:thin:@123.57.26.228:1521:ttsw";
        String user = "hyplat";
        String password = "hyplatttsw";

        StringBuffer content = new StringBuffer("public class ").append(className).append(" implements BaseBean,java.io.Serializable {");
        content.append("\r\n");

        StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
        xml.append("<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\r\n");
        xml.append("\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\r\n");
        xml.append("<hibernate-mapping package=\"").append(packName).append("\">\r\n");
        xml.append("<class name=\"").append(className).append("\" table=\"").append(tableName).append("\">\r\n");
        xml.append("<comment>").append(tableText).append("</comment>");


        // 加载Oracle JDBC驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, null);

            String columnName = null;
            String dataType = null;
            int columnSize;
            HashMap<String, String> bzMap = new HashMap<>();

            String sql = "select COLUMN_NAME,COMMENTS from user_col_comments s where TABLE_NAME='" + tableName + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();
            while (rSet.next()) {
                bzMap.put(rSet.getString("COLUMN_NAME"), rSet.getString("COMMENTS"));
            }

            while (rs.next()) {
                columnName = rs.getString("COLUMN_NAME");
                dataType = rs.getString("TYPE_NAME");
                columnSize = rs.getInt("COLUMN_SIZE");

                //po内容
                content.append("private ").append(map.get(dataType)).append(" ").append(camel(columnName)).append(";").append(" //").append(bzMap.get(columnName));
                content.append("\r\n");

                //xml内容
                if (idKey.equals(columnName)) {
                    xml.append("<id name=\"").append(camel(columnName)).append("\">\r\n");
                    xml.append("<column length=\"").append(columnSize).append("\" name=\"").append(columnName).append("\">\r\n");
                    xml.append("<comment>").append(bzMap.get(columnName)).append("</comment>\r\n");
                    xml.append("</column>\r\n");
                    xml.append("</id>\r\n");
                } else {
                    xml.append("<property name=\"").append(camel(columnName)).append("\">\r\n");
                    xml.append("<column length=\"").append(columnSize).append("\" name=\"").append(columnName).append("\">\r\n");
                    xml.append("<comment>").append(bzMap.get(columnName)).append("</comment>\r\n");
                    xml.append("</column>\r\n");
                    xml.append("</property>\r\n");
                }

            }

            content.append("\r\n");
            content.append("}");

            xml.append("</class>\r\n");
            xml.append("</hibernate-mapping>\r\n");
//            System.out.println(content.toString());
//            System.out.println(xml.toString());

            try (BufferedWriter writer =
                         Files.newBufferedWriter(Paths.get("C:\\Users\\Administrator\\Desktop\\po\\" + className + ".java"), StandardCharsets.UTF_8)) {
                writer.write(content.toString());
            }
            try (BufferedWriter writer =
                         Files.newBufferedWriter(Paths.get("C:\\Users\\Administrator\\Desktop\\po\\" + Character.toLowerCase(className.charAt(0)) + className.substring(1) + ".hbm.xml"), StandardCharsets.UTF_8)) {
                writer.write(xml.toString());
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String camel(String text) {
        StringBuilder camelCaseStr = new StringBuilder();
        String[] parts = text.split("_");
        for (String part : parts) {
            camelCaseStr.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1).toLowerCase(Locale.ROOT));
        }
        String str = camelCaseStr.toString();
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}

