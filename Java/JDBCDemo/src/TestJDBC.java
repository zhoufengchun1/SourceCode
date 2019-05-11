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
        ResultSet resultSet = null;
        String username="12334";
        String password="7893";
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://47.100.195.173:3306/UserInfo", "root", "admin");
            statement = connection.createStatement();
            statement.executeUpdate("insert into user " + "(" + "user_name,user_passwd" + ") " + "values"
                    + "(" + username + "," + password + ")");
            System.out.println("success");


        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                statement.close();
                connection.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }
}
