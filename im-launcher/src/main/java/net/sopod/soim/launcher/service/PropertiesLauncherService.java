package net.sopod.soim.launcher.service;

import net.sopod.soim.launcher.LauncherService;
import net.sopod.soim.launcher.util.PropsUtil;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * PropertiesLauncherService
 *
 * @author tmy
 * @date 2022-06-10 21:34
 */
public class PropertiesLauncherService implements LauncherService {

    @Override
    public void launcher() {
        System.out.println("启动参数初始化...");
        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourceResolver.getResource("classpath:launcher.properties");
        try {
            InputStream launcherPropsIn = resource.getInputStream();
            // InputStream launcherPropsIn = this.getClass().getResourceAsStream("launcher.properties");
            InputStreamReader launcherPropsReader = new InputStreamReader(launcherPropsIn, StandardCharsets.UTF_8);
            Properties properties = new Properties();
            Properties sysProps = System.getProperties();
            properties.load(launcherPropsReader);
            // 设置 properties
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                PropsUtil.setProperty(sysProps, key, value);
            }
        } catch (IOException e) {
            throw new IllegalStateException("启动参数初始化失败", e);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
