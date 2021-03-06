package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import androidx.annotation.RequiresApi;

public class CallLogRetriever extends ContentRetriever{
    public CallLogRetriever(Context context, OutputFormat fmt){
        super(context, fmt);
        Uri[] _uri = new Uri[1];
        String[] _cn = new String[1];
        _uri[0] = CallLog.Calls.CONTENT_URI;
        _cn[0] = "CallLogCalls";
        super.setMembers(_uri, _cn);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setContent(){ super.setContent(); }
    public String[] getContents(){ return super.getContents(); }
    public String[] getOutPutFiles(){ return super.getOutPutFiles(); }
    public Uri[] getCONTENT_URIs(){ return super.getCONTENT_URIs(); }
    public int[] getCounts(){ return super.getCounts(); }
    public int getNumOfUri(){ return super.getNumOfUri(); }
}
