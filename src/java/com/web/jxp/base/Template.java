package com.web.jxp.base;

import com.oroinc.text.perl.Perl5Util;
import java.io.*;
import java.util.*;

public class Template {

    public Template() {
    }

    public Template(String s) {
        loaded_at = new Date();
        loaded_at.setTime(loaded_at.getTime());
        StringBuffer stringbuffer = new StringBuffer();
        filename = s;
        try {
            FileReader filereader = new FileReader(filename);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            while ((template = bufferedreader.readLine()) != null) {
                stringbuffer = stringbuffer.append(template + "\n");
            }
            filereader.close();
        } catch (FileNotFoundException filenotfoundexception) {
            filenotfoundexception.printStackTrace();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        template = stringbuffer.toString();
    }

    public String getTemplate() {
        return template;
    }

    public String patch(HashMap hashmap) {
        Date date = new Date();
        File file = new File(filename);
        date.setTime(file.lastModified());
        if (loaded_at.compareTo(date) < 0) {
            StringBuffer stringbuffer = new StringBuffer();
            try {
                FileReader filereader = new FileReader(filename);
                BufferedReader bufferedreader = new BufferedReader(filereader);
                while ((template = bufferedreader.readLine()) != null) {
                    stringbuffer = stringbuffer.append(template + "\n");
                }
                filereader.close();
            } catch (FileNotFoundException filenotfoundexception) {
                filenotfoundexception.printStackTrace();
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
            template = stringbuffer.toString();
        }
        template = patchMessage(template, hashmap);
        return template;
    }

    public String patchMessage(String s, HashMap hashmap) {
        if (s.equals("")) {
            return "";
        }
        template = s;
        Perl5Util perl5util = new Perl5Util();
        Set set = hashmap.entrySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            String s2 = "s!__" + entry.getKey() + "__!" + perl5util.substitute("s/!/\\!/sg", "" + entry.getValue()) + "!isg";
            template = perl5util.substitute(s2, template);
        }

        String s1 = "s!(__[^\\s].*?__)!!isg";
        template = perl5util.substitute(s1, template);
        return template;
    }

    public HashMap getAttribute() {
        Perl5Util perl5util = new Perl5Util();
        HashMap hashmap = new HashMap();
        try {
            FileReader filereader = new FileReader(filename);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            for (String s = new String(); (s = bufferedreader.readLine()) != null;) {
                if (perl5util.match("m/__([^\\s].*?)__/", s)) {
                    Vector vector = perl5util.split(s);
                    String as[] = (String[]) vector.toArray(new String[0]);
                    for (int i = 0; i < as.length; i++) {
                        if (perl5util.match("m!__([^\\s].*?)__!", as[i])) {
                            as[i] = perl5util.substitute("s!__([^\\s].*?)__!$1!", as[i]);
                            hashmap.put(as[i], "");
                        }
                    }
                }
            }

            filereader.close();
        } catch (FileNotFoundException filenotfoundexception) {
            filenotfoundexception.printStackTrace();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        return hashmap;
    }
    private String template;
    private String filename;
    private Date loaded_at;
}
