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
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user (user_name,user_passwd)values (?,?)");
            preparedStatement.setString(1, "ppp  ");
            preparedStatement.setString(2, "222");
            preparedStatement.executeUpdate();
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
