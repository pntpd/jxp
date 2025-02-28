package com.web.jxp.base;
public class Validate 
{
    public String replacename(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z0-9,\\-'\\s\\.]+", "").trim();
        }
        return str;
    }
    
    public String replaceint(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^0-9\\-]+", "").trim();
        }
        return str;
    }
    
    public String replacedouble(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^0-9\\.]+", "").trim();
        }
        return str;
    }
    
    public String replacenamenum(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z0-9]+", "").trim();
        }
        return str;
    }
    
    public String replacedesc(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z0-9,;:=$\\^\\-_\\.\\+\\'\\\"/\\\\!@`#%&()#\\s\\?]+", "");
        }
        return str;
    }
    
    public String replacedeschtml(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z0-9,;=$\\-_\\.\\+\\'\\\"/\\\\!@`#%&()#\\s\\?<>:\n\r]+", "").trim();
        }
        return str;
    }
    
    public String replacereg(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z0-9\\s\\-/\\\\]+", "").trim();
        }
        return str;
    }
    
    public String replacenamewithand(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z-'\\s\\.&]+", "").trim();
        }
        return str;
    }
    
    public String replacedate(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z0-9\\-\\s:]+", "").trim();
        }
        return str;
    }
    
    public String replacetime(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^0-9AMP\\-:\\s]+", "").trim();
        }
        return str;
    }
    
    public static String replacechar(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^a-zA-Z]+", "").trim();
        }
        return str;
    }
    
    public String replacealphacomma(String input)
    {
        String str = "";        
        if(input != null && !input.equals(""))
        {
            input = input.replaceAll("(?i)iframe", "");
            input = input.replaceAll("(?i)hyperlink", "");
            input = input.replaceAll("(?i)script", "");
            input = input.replaceAll("(?i)style", "");
            str = input.replaceAll("[^0-9,\\s]+", "").trim();
        }
        return str;
    }
}