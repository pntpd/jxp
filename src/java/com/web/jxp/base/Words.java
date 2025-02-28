package com.web.jxp.base;

import java.text.DecimalFormat;

public class Words 
{
    public static final double UNITS = 1;
    public static final double TENS = 10 * UNITS;
    public static final double HUNDREDS = 10 * TENS;
    public static final double THOUSANDS = 10 * HUNDREDS;
    public static final double TENTHOUSANDS = 10 * THOUSANDS;
    public static final double LAKHS = 10 * TENTHOUSANDS;
    public static final double TENLAKHS = 10 * LAKHS;
    public static final double CRORES = 10 * TENLAKHS;
    public static final double TENCRORES = 10 * CRORES;
    public static final double ARABS = 10 * TENCRORES;
    public static final double TENARABS = 10 * ARABS;
    
    private String cleanNumber(String number)
    {
        String cleanedNumber = "";
        cleanedNumber = number.replace( '.', ' ' ).replaceAll( " ", "" );
        cleanedNumber = cleanedNumber.replace( ',', ' ' ).replaceAll( " ", "" );
        if( cleanedNumber.startsWith("0"))
            cleanedNumber = cleanedNumber.replaceFirst( "0", "" );
        return cleanedNumber;
    }
    
    private double getPlace( String number )
    {
        switch(number.length())
        {
            case 1:
                return UNITS; 
            case 2:
                return TENS;
            case 3:
                return HUNDREDS;
            case 4:
                return THOUSANDS;
            case 5:
                return TENTHOUSANDS;
            case 6:
                return LAKHS;
            case 7:
                return TENLAKHS;
            case 8:
                return CRORES;
            case 9:
                return TENCRORES;
            case 10:
                return ARABS;
            case 11:
                return TENARABS;
        }
        return 0.0;
    }
    
    private String getWord(long number)
    {
        if(number == 1)
            return "One";
        else if(number == 2)
            return "Two";
        else if(number == 3)
            return "Three";
        else if(number == 4)
            return "Four";
        else if(number == 5)
            return "Five";
        else if(number == 6)
            return "Six";
        else if(number == 7)
            return "Seven";
        else if(number == 8)
            return "Eight";
        else if(number == 9)
            return "Nine";
        else if(number == 0)
            return "Zero";
        else if(number == 10)
            return "Ten";
        else if(number == 11)
            return "Eleven";
        else if(number == 12)
            return "Twelve";
        else if(number == 13)
            return "Thirteen";
        else if(number == 14)
            return "Fourteen";
        else if(number == 15)
            return "Fifteen";
        else if(number == 16)
            return "Sixteen";
        else if(number == 17)
            return "Seventeen";
        else if(number == 18)
            return "Eighteen";
        else if(number == 19)
            return "Nineteen";
        else if(number == 20)
            return "Twenty";
        else if(number == 30)
            return "Thirty";
        else if(number == 40)
            return "Fourty";
        else if(number == 50)
            return "Fifty";
        else if(number == 60)
            return "Sixty";
        else if(number == 70)
            return "Seventy";
        else if(number == 80)
            return "Eighty";
        else if(number == 90)
            return "Ninety";
        else if(number == 100)
            return "Hundred";
        else
            return "";
    }

    public String convertNumber(String number)
    {
        number = cleanNumber(number);
        double num = 0.0;
        try
        {
            num = Double.parseDouble( number );
        }
        catch(Exception e)
        {
            return "Invalid Number Sent to Convert";
        }
        String returnValue = "";
        while( num > 0 )
        {
            number = "" + (long)num;
            double place = getPlace(number);
            if( place == TENS || place == TENTHOUSANDS || place == TENLAKHS || place == TENCRORES || place == TENARABS)
            {
                long subNum = Long.parseLong( number.charAt(0) + "" + number.charAt(1) );
                if( subNum >= 21 && (subNum%10) != 0 )
                {
                    returnValue += getWord( Long.parseLong( "" + number.charAt(0) ) * 10 ) + " " + getWord( subNum%10 ) ;
                }
                else
                {
                    returnValue += getWord(subNum);
                }
                if( place == TENS)
                {
                    num = 0;
                }
                else if( place == TENTHOUSANDS)
                {
                    num -= subNum * THOUSANDS;
                    returnValue += " Thousand ";
                }
                else if( place == TENLAKHS)
                {
                    num -= subNum * LAKHS;
                    returnValue += " Lakh ";
                }
                else if(place == TENCRORES)
                {
                    num -= subNum * CRORES;
                    returnValue += " Crore ";
                }
                else if(place == TENARABS)
                {
                    num -= subNum * ARABS;
                    returnValue += " Arab ";
                }
            }
            else
            {
                long subNum = Long.parseLong( "" + number.charAt(0) );
                returnValue += getWord( subNum );
                if( place == UNITS)
                {
                    num = 0;
                }
                else if( place == HUNDREDS)
                {
                    num -= subNum * HUNDREDS;
                    returnValue += " Hundred ";
                }
                else if( place == THOUSANDS)
                {
                    num -= subNum * THOUSANDS;
                    returnValue += " Thousand ";
                }
                else if( place == LAKHS)
                {
                    num -= subNum * LAKHS;
                    returnValue += " Lakh ";
                }
                else if( place == CRORES)
                {
                    num -= subNum * CRORES;
                    returnValue += " Crore ";
                }
                else if( place == ARABS)
                {
                    num -= subNum * ARABS;
                    returnValue += " Arab ";
                }
            }
        }
        return returnValue;
    }
    
    public String changeDecimalNum(double d, int n)
    {
        DecimalFormat nf = new DecimalFormat("##########");
        nf.setMinimumFractionDigits(n);
        nf.setMaximumFractionDigits(n);
        nf.setMinimumIntegerDigits(1);
        return nf.format(d);
    }
    
    public String convertFull(double dnumber)
    {
        if(dnumber > 0)
        {
            double dd = dnumber;
            String ss = changeDecimalNum(dd, 2);
            String integral = ss.replaceAll("\\D\\d++","");
            String fraction = "0";
            if(ss.indexOf(".") >= 0)
            {
               fraction  = ss.replaceAll("\\d++\\D", "");
            }
            String b1 = convertNumber(integral);
            String b2 = convertNumber(fraction);

            if(!b2.equals(""))
                b1 = b1 + " And " + b2 + " Paisa Only.";
            else
                b1 = b1 + " Only.";
            return b1;
        }
        else
        {
            return "Zero Only.";
        }
    }
}
