package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

public class MediaRetriever extends ContentRetriever{

    public MediaRetriever(Context context, OutputFormat fmt){
        super(context, fmt);
        Uri[] _uri = new Uri[6];
        String[] _cn = new String[6];
        _uri[0] = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        _uri[1] = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        _uri[2] = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        _uri[3] = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        _uri[4] = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
        _uri[5] = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        _cn[0] = "ExternalAudioMedia";
        _cn[1] = "ExternalVideoMedia";
        _cn[2] = "ExternalImageMedia";
        _cn[3] = "InternalAudioMedia";
        _cn[4] = "InternalVideoMedia";
        _cn[5] = "InternalImageMedia";

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
