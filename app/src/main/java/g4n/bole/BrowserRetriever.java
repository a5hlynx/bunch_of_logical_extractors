package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class BrowserRetriever extends ContentRetriever{
    public BrowserRetriever(Context context, OutputFormat fmt) {
        super(context, fmt);
        Uri[] _uri = new Uri[3];
        String[] _cn = new String[3];
        _uri[0] = Uri.parse("content://com.android.browser/history");
        _uri[1] = Uri.parse("content://com.android.browser/bookmarks");
        _uri[2] = Uri.parse("content://com.android.browser/searches");
        _cn[0] = "BrowserHistory";
        _cn[1] = "BrowserBookmarks";
        _cn[2] = "BrowserSearches";
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
