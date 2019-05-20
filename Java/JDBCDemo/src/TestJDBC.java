import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class TestJDBCj
{
    public static void main(String[] args)
    {
        Connection connection = null;
        Statement statement = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://47.100.195.173:3306/UserInfo", "root", "admin");
            statement = connection.createStatement();
            int m=statement.executeUpdate("update user set user_sex =" + 0+" "
                    + "where user_name=" + "'"
                    + 123 + "'");
            System.out.println(m);
            System.out.println("success");


        } catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }
}
