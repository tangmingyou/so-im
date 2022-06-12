package net.sopod.soim.launcher;

import net.sopod.soim.launcher.util.PropsUtil;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SoimApplication
 *
 * @author tmy
 * @date 2022-06-10 19:48
 */
public class SoimApplication {

    public static void run(Class<?> primarySource, String...args) {
        buildApplicationProperties();
        SpringApplication.run(primarySource, args);
    }

    private static void buildApplicationProperties() {
        List<LauncherService> launcherServices = new ArrayList<>();
        ServiceLoader.load(LauncherService.class).forEach(launcherServices::add);
        launcherServices.stream().sorted(Comparator.comparing(LauncherService::getOrder))
                .collect(Collectors.toList())
                .forEach(LauncherService::launcher);
    }

}
