import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestLifeCycleServlet extends HttpServlet
{
    public TestLifeCycleServlet()
    {
        System.out.println("Constructor");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        System.out.println("doGet");
    }

    public void init(ServletConfig servletConfig)
    {
        System.out.println("init");
    }

    @Override
    public void destroy()
    {
        System.out.println("destroy");
    }
}

