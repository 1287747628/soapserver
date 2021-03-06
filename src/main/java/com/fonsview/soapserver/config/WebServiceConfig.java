package com.fonsview.soapserver.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "ceReceive")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ceReceivePort");
        wsdl11Definition.setLocationUri("/ceReceive");
        wsdl11Definition.setTargetNamespace("iptv");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/epg_ce.xsd"));
    }

    @Bean(name = "cdnReceive")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCdn(XsdSchema cdnSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("cdnReceivePort");
        wsdl11Definition.setLocationUri("/cdnReceive");
        wsdl11Definition.setTargetNamespace("iptv");
        wsdl11Definition.setSchema(cdnSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema cdnSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/cdn.xsd"));
    }

    @Bean(name = "cmsReceive")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCms(XsdSchema cmsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("cmsReceivePort");
        wsdl11Definition.setLocationUri("/cmsReceive");
        wsdl11Definition.setTargetNamespace("iptv");
        wsdl11Definition.setSchema(cmsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema cmsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/cms.xsd"));
    }

}
