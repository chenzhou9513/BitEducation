package com.bitedu.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class PathUtils {
    private static Properties props=System.getProperties();

    @Value("${file.path.test}")
    private  String testFilePath;

    @Value("${file.path.val}")
    private  String valFilePath;


    public String getFilePath(){
        String os = props.getProperty("os.name");
        if("Mac OS X".equals(os)){
            return testFilePath;
        }else{
            return valFilePath;
        }
    }


}
