package com.fonsview.soapserver.config;

import com.fonsview.soapserver.service.MiguService;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class MiguCxfConfig {

    @Autowired
    private MiguService miguService;

    @Bean
    public ServletRegistrationBean dispatcherServletMigu() {
        CXFServlet cxfServlet = new CXFServlet();
        cxfServlet.setBus(springBusMigu());
        ServletRegistrationBean servletBean = new ServletRegistrationBean(cxfServlet, "/HNOTTCIP/services/*");
        servletBean.setName("migu");
        return servletBean;
    }

    @Bean
    public SpringBus springBusMigu() {
        SpringBus bus = new SpringBus();
        bus.setId("migu");
        return bus;
    }

    @Bean
    public Endpoint endpointMigu() {
        EndpointImpl endpoint = new EndpointImpl(springBusMigu(), miguService);
        endpoint.publish("/ExecCmdforYsgj");
        return endpoint;
    }

}
