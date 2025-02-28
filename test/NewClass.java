import com.web.jxp.base.Base;
import com.web.jxp.cassessment.Cassessment;
import com.web.jxp.cassessment.CassessmentInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;



public class NewClass {
    
    public static ArrayList getlist(ArrayList list, String parameter)
    {
        ArrayList l = new ArrayList();
        for(int i=0;i<list.size();i++)
        {
            CassessmentInfo info = (CassessmentInfo)list.get(i);
            if(info.getAssparameter().equals(parameter))
                l.add(list.get(i));
        }
    return l;
    }
    public static String getInfoValue(CassessmentInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("2")) {
            infoval = info.getAssparameter()!= null ? info.getAssparameter() : "";
        }
        return infoval;
    }
        public static void main(String args[])
        {
            ArrayList alist = new ArrayList();
            
            
            
          Cassessment cass = new Cassessment(); 
          ArrayList list = cass.getAssessmentquestionlist(7);
          SortedSet sortedset = new TreeSet();
          if (list.size() > 0) {
                sortedset  = (TreeSet) list.get(list.size() - 1);
                list.remove(list.size() - 1);
            }
          
          Iterator sortit = sortedset.iterator();
          
          while(sortit.hasNext())
          {
              ArrayList list1;
              String value = (String)sortit.next();
              list1 = getlist(list,value);
             
              for(int i = 0;i<list1.size();i++)
              {
                    CassessmentInfo sinfo = (CassessmentInfo)list1.get(i);
                   
              }
          }  
}

public ArrayList getListFromList(ArrayList mainlist, String parameterName)
    {
        ArrayList list = new ArrayList();
        int size = mainlist.size();
        if(size > 0)
        {
            for(int i = 0; i < size; i++)
            {
                CassessmentInfo info = (CassessmentInfo) mainlist.get(i);
                if(info != null && info.getAssparameter() != null && info.getAssparameter().equals(parameterName))
                    list.add(info);
            }
        }
        return list;
    
    }
}
