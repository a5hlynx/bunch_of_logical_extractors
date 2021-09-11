package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;

public class SystemRetriever extends ContentRetriever{
    public SystemRetriever(Context context, OutputFormat fmt) {
        super(context, fmt);
        Uri[] _uri = new Uri[2];
        String[] _cn = new String[2];
        _uri[0] = Settings.Secure.CONTENT_URI;
        _uri[1] = Settings.System.CONTENT_URI;
        _cn[0] = "Secure";
        _cn[1] = "System";
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
    public int[] getCounts(){
        return super.getCounts();
    }
    public int getNumOfUri(){
        return super.getNumOfUri();
    }
}