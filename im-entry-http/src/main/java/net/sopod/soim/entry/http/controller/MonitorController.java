package net.sopod.soim.entry.http.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import lombok.AllArgsConstructor;
import net.sopod.soim.entry.http.client.EntryClient;
import net.sopod.soim.entry.http.config.ImEntryMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

/**
 * MonitorController
 *
 * @author tmy
 * @date 2022-06-11 16:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/monitor")
public class MonitorController {

    private static final Logger logger = LoggerFactory.getLogger(EntryClient.class);

    private ImEntryMonitor imEntryMonitor;

    @GetMapping("/entryHost")
    public String getEntryHost() {
        return imEntryMonitor.getOneActiveImEntryAddress();
    }

}
