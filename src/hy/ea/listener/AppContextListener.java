package hy.ea.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 获取 Spring 的 ApplicationContext
        WebApplicationContext ctx = WebApplicationContextUtils
                .getWebApplicationContext(sce.getServletContext());

        // 从 Spring 中拿到 dataSource bean
        ComboPooledDataSource dataSource = (ComboPooledDataSource) ctx.getBean("dataSource", ComboPooledDataSource.class);
        if (dataSource != null) {
            try {
                dataSource.close();
                System.out.println("✅ C3P0 数据源关闭成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🌱 Web 应用已启动");
    }
}
