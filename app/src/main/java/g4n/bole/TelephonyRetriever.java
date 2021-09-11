package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import androidx.annotation.RequiresApi;

public class TelephonyRetriever extends ContentRetriever{
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public TelephonyRetriever(Context context, OutputFormat fmt) {
        super(context, fmt);
        Uri[] _uri = new Uri[5];
        String[] _cn = new String[5];
        _uri[0] = Telephony.Sms.CONTENT_URI;
        _uri[1] = Telephony.Mms.CONTENT_URI;
        _uri[2] = Telephony.MmsSms.CONTENT_URI;
        _uri[3] = Telephony.Carriers.CONTENT_URI;
        _cn[0] = "Sms";
        _cn[1] = "Mms";
        _cn[2] = "MmsSms";
        _cn[3] = "Carriers";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            _uri[4] = Telephony.Mms.Part.CONTENT_URI;
            _cn[4] = "MmsParts";
        } else{
            _uri[4] = null;
            _cn[4] = null;
        }
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
    public int getNumOfUri() {
        return super.getNumOfUri();
    }
}
