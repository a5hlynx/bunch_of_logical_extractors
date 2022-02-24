package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class IMRetriever extends ContentRetriever{
    public IMRetriever(Context context, OutputFormat fmt){
        super(context, fmt);
        Uri[] _uri = new Uri[10];
        String[] _cn = new String[10];
        _uri[0] = Uri.parse("content://im/contacts");
        _uri[1] = Uri.parse("content://im/accounts");
        _uri[2] = Uri.parse("content://im/providers/account");
        _uri[3] = Uri.parse("content://im/contacts/chatting");
        _uri[4] = Uri.parse("content://im/contactLists");
        _uri[5] = Uri.parse("content://im/messages");
        _uri[6] = Uri.parse("content://im/invitations");
        _uri[7] = Uri.parse("content://im/chats");
        _uri[8] = Uri.parse("content://im/providerSettings");
        _uri[9] = Uri.parse("content://im/providers");
        _cn[0] = "ImContacts";
        _cn[1] = "ImAccounts";
        _cn[2] = "ImProvidersAccount";
        _cn[3] = "ImContactsChatting";
        _cn[4] = "ImContactLists";
        _cn[5] = "ImMessages";
        _cn[6] = "ImInvitations";
        _cn[7] = "ImChats";
        _cn[8] = "ImProviderSettings";
        _cn[9] = "ImProviders";

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
