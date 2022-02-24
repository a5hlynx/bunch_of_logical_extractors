package g4n.bole;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

import static android.database.Cursor.FIELD_TYPE_BLOB;

public abstract class ContentRetriever{
    private Context context;
    private String selection = null;
    private OutputFormat fmt = OutputFormat.XML;
    private Uri[] CONTENT_URIs;
    private String[] contents;
    private String[] cns;
    private int[] counts;
    private String[] opfs;
    protected ContentRetriever(Context context, OutputFormat fmt){
        this.context = context;
        this.fmt = fmt;
    }
    protected void setContent(){
        switch (this.fmt){
            case JSON:
                for(int z = 0; z < this.CONTENT_URIs.length; z++){
                    ContentProviderClient cli = null;
                    try{
                        cli = this.context.getContentResolver().acquireContentProviderClient(CONTENT_URIs[z]);
                    }catch (Exception e){
                        cli = null;
                        e.printStackTrace();
                    }
                    if (cli == null)
                        continue;
                    setContentInJSON(z);
                }
                break;
            case XML:
            default:
                for(int z = 0; z < this.CONTENT_URIs.length; z++){
                    ContentProviderClient cli = null;
                    try{
                        cli = this.context.getContentResolver().acquireContentProviderClient(CONTENT_URIs[z]);
                    }catch (Exception e){
                        cli = null;
                        e.printStackTrace();
                    }
                    if (cli == null)
                        continue;
                    setContentInXML(z);
                }
                break;
        }
    }
    protected void setMembers(Uri[] _uri, String[] _cns){
        this.CONTENT_URIs = new Uri[_uri.length];
        this.CONTENT_URIs = _uri;
        this.cns = new String[_cns.length];
        this.cns = _cns;
        this.contents = new String[this.CONTENT_URIs.length];
        this.counts = new int[this.CONTENT_URIs.length];
        this.opfs = new String[this.CONTENT_URIs.length];
        for(int i =0;  i < this.CONTENT_URIs.length; i++){
            this.contents[i] = "";
            this.counts[i] = 0;
            this.opfs[i] = "";
        }
    }
    private void setContentInJSON(int z){
        try{
            Cursor c = this.context.getContentResolver().query(CONTENT_URIs[z], null, selection, null, null);
            org.json.JSONObject recs = new org.json.JSONObject();
            org.json.JSONObject rec = new org.json.JSONObject();
            org.json.JSONArray kvs = new org.json.JSONArray();
            this.counts[z] = 0;
            while (c.moveToNext()){
                org.json.JSONObject kv = new org.json.JSONObject();
                for (int i = 0; i < c.getColumnCount(); i++){
                    if (c.getType(i) == FIELD_TYPE_BLOB){
                        kv.put(c.getColumnName(i), "");
                    }else if (c.getString(i) != null){
                        kv.put(c.getColumnName(i), c.getString(i));
                    }else{
                        kv.put(c.getColumnName(i), "");
                    }
                }
                kvs.put(kv);
                this.counts[z] += 1;
            }
            rec.put("rec", kvs);
            recs.put("recs", rec);
            this.contents[z] = recs.toString();
            this.opfs[z] = this.cns[z] + ".json";
            c.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setContentInXML(int z){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Cursor c = this.context.getContentResolver().query(CONTENT_URIs[z], null, selection, null, null);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element recs = doc.createElement("recs");
            this.counts[z] = 0;
            while (c.moveToNext()){
                Element rec = doc.createElement("rec");
                for (int i = 0; i < c.getColumnCount(); i++){
                    Element k = doc.createElement(c.getColumnName(i));
                    Text v;
                    if (c.getType(i) == FIELD_TYPE_BLOB){
                        v = doc.createTextNode("");
                    }else if (c.getString(i) != null){
                        v = doc.createTextNode(c.getString(i));
                    }else{
                        v = doc.createTextNode("");
                    }
                    k.appendChild(v);
                    rec.appendChild(k);
                }
                recs.appendChild(rec);
                this.counts[z] += 1;
            }
            doc.appendChild(recs);
            c.close();
            StringWriter sw = new StringWriter();
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.transform(new DOMSource(doc), new StreamResult(sw));
            this.contents[z] = sw.toString();
            this.opfs[z] = this.cns[z] + ".xml";
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected String[] getContents(){ return this.contents; }
    protected String[] getOutPutFiles(){ return this.opfs; }
    protected int[] getCounts(){ return this.counts; }
    protected int getNumOfUri(){ return this.CONTENT_URIs.length; }
    protected Uri[] getCONTENT_URIs(){ return this.CONTENT_URIs; }
}
