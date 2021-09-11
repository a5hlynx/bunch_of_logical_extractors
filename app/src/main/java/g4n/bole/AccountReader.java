package g4n.bole;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class AccountReader extends InfoReader{
    private Context context;
    private OutputFormat fmt;
    private String info;
    private String in;
    private int count;
    private String opf;
    public AccountReader(Context context, OutputFormat fmt){
        super(context,fmt);
        this.context = context;
        this.fmt = fmt;
        String _in = "Accounts";
        setMembers(_in);
    }
    private void setMembers(String _in){
        this.in = _in;
        this.info = "";
        this.count = 0;
        this.opf = "";
    }
    public void setInfoInJSON(){
        try {
            Account[] accounts = AccountManager.get(this.context).getAccounts();
            org.json.JSONObject recs = new org.json.JSONObject();
            org.json.JSONObject rec = new org.json.JSONObject();
            org.json.JSONArray kvs = new org.json.JSONArray();
            this.count = 0;
            for(int i=0; i< accounts.length; i++) {
                org.json.JSONObject kv = new org.json.JSONObject();
                kv.put("name", accounts[i].name);
                kvs.put(kv);
                this.count +=1;
            }
            rec.put("rec", kvs);
            recs.put("recs", rec);
            this.info = recs.toString();
            this.opf = this.in + ".json";
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setInfoInXML(){
        try {
            Account[] accounts = AccountManager.get(this.context).getAccounts();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element recs = doc.createElement("recs");
            this.count = 0;
            for (int i =0; i < accounts.length; i++){
                Element rec = doc.createElement("rec");
                Element k = doc.createElement("name");
                Text v = doc.createTextNode(accounts[i].name);
                k.appendChild(v);
                rec.appendChild(k);
                recs.appendChild(rec);
                this.count += 1;
            }
            doc.appendChild(recs);
            StringWriter sw = new StringWriter();
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.transform(new DOMSource(doc), new StreamResult(sw));
            this.info = sw.toString();
            this.opf = this.in + ".xml";
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String getInfo(){ return this.info; }
    public String getOutPutFile(){ return this.opf; }
    public String getInfoName(){ return this.in;}
    public int getCount(){ return this.count; }

}
