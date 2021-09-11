package g4n.bole;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;

public class ContactRetriever extends ContentRetriever {

    public ContactRetriever(Context context, OutputFormat fmt) {
        super(context, fmt);
        Uri[] _uri = new Uri[14];
        String[] _cn = new String[14];
        _uri[0] = ContactsContract.Contacts.CONTENT_URI;
        _uri[1] = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        _uri[2] = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        _uri[3] = ContactsContract.Groups.CONTENT_URI;
        _uri[4] = ContactsContract.Settings.CONTENT_URI;
        _uri[5] = Uri.parse("content://contacts/people");
        _uri[6] = Uri.parse("content://contacts/contact_methods");
        _uri[7] = Uri.parse("content://contacts/extensions");
        _uri[8] = Uri.parse("content://contacts/groups");
        _uri[9] = Uri.parse("content://contacts/organizations");
        _uri[10] = Uri.parse("content://contacts/phones");
        _uri[11] = Uri.parse("content://contacts/settings");
        _uri[12] =  Uri.parse("content://contacts/deleted_people");
        _cn[0] = "ContactsContractContacts";
        _cn[1] = "ContactsContractPhone";
        _cn[2] = "ContactsContractEmail";
        _cn[3] = "ContactsContractGroups";
        _cn[4] = "ContactsContractSettings";
        _cn[5] = "ContactsPeople";
        _cn[6] = "ContactsContactMethods";
        _cn[7] = "ContactsExtensions";
        _cn[8] = "ContactsGroups";
        _cn[9] = "ContactsOrganizations";
        _cn[10] = "ContactsPhones";
        _cn[11] = "ContactsSettings";
        _cn[12] = "ContactsDeletedPeople";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            _uri[13] = ContactsContract.DeletedContacts.CONTENT_URI;
            _cn[13] = "ContactsContractDeletedContacts";
        } else {
            _uri[13] = null;
            _cn[13] = null;
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
    public int getNumOfUri(){
        return super.getNumOfUri();
    }
}


