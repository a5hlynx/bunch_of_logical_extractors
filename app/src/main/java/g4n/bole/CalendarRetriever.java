package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import androidx.annotation.RequiresApi;

public class CalendarRetriever extends ContentRetriever{
    public CalendarRetriever(Context context, OutputFormat fmt) {
        super(context, fmt);
        Uri[] _uri = new Uri[7];
        String[] _cn = new String[7];
        _uri[0] = CalendarContract.Calendars.CONTENT_URI;
        _uri[1] = CalendarContract.Events.CONTENT_URI;
        _uri[2] = CalendarContract.Attendees.CONTENT_URI;
        _uri[3] = CalendarContract.ExtendedProperties.CONTENT_URI;
        _uri[4] = CalendarContract.Reminders.CONTENT_URI;
        _uri[5] = CalendarContract.CalendarAlerts.CONTENT_URI;
        _uri[6] = CalendarContract.CalendarEntity.CONTENT_URI;
        _cn[0] = "Calendars";
        _cn[1] = "CalendarEvents";
        _cn[2] = "CalendarAttendees";
        _cn[3] = "CalendarExtendedProperties";
        _cn[4] = "CalendarReminders";
        _cn[5] = "CalendarAlerts";
        _cn[6] = "CalendarEntity";
        super.setMembers(_uri, _cn);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setContent() {
        super.setContent();
    }
    public void setContentInJSON(int z) {
        super.setContentInJSON(z);
    }
    public void setContentInXML(int z){
        super.setContentInXML(z);
    }
    public String[] getContents(){
        return super.getContents();
    }
    public String[] getOutPutFiles(){
        return super.getOutPutFiles();
    }
    public Uri[] getCONTENT_URIs(){ return super.getCONTENT_URIs();}
    public int[] getCounts(){
        return super.getCounts();
    }
    public int getNumOfUri(){
        return super.getNumOfUri();
    }
}
