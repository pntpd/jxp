package com.web.jxp.common;
import com.web.jxp.base.*;
import java.util.logging.*;

public class Common extends Base 
{
    private static boolean printFlag = true;
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    public static void print(Object clname, String obj)
    {
        if(printFlag)
        {
            logger.info(clname.getClass() + "::" + obj);
        }
    }

    public static void print(Object clname,double obj)
    {
        if(printFlag)
        {
            logger.info(clname.getClass()+"::"+obj);
        }
    }
}