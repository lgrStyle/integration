package com.demo.integration.comom.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.webjars.WebJarAssetLocator;

@Controller
public class WebJarController {
    
    private final WebJarAssetLocator assetLocator = new WebJarAssetLocator();
    
    @RequestMapping("/webjars/{webjar}/**")
    @ResponseBody
    public ResponseEntity<Object> locateWebjarAsset(@PathVariable String webjar , HttpServletRequest request) {
        try {
            String prefix = "/webjars/" + webjar + "/";
            String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            String fullPath = assetLocator.getFullPath(webjar, path.substring(prefix.length()));
            return new ResponseEntity<Object>(new ClassPathResource(fullPath), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
        
    }
    
}
