package g4n.bole;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PackageReader extends InfoReader{
    private Context context;
    private OutputFormat fmt;
    private String info;
    private String in;
    private int count;
    private String opf;
    public PackageReader(Context context, OutputFormat fmt){
        super(context,fmt);
        this.context = context;
        this.fmt = fmt;
        String _in = "Packages";
        setMembers(_in);
    }
    private void setMembers(String _in){
        this.in = _in;
        this.info = "";
        this.count = 0;
        this.opf = "";
    }
    public void setInfoInJSON(){
        try{
            final PackageManager pm = this.context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            org.json.JSONObject recs = new org.json.JSONObject();
            org.json.JSONObject rec = new org.json.JSONObject();
            org.json.JSONArray kvs = new org.json.JSONArray();
            this.count = 0;
            for(int i=0; i< packages.size(); i++){
                ApplicationInfo ai = packages.get(i);
                org.json.JSONObject kv = new org.json.JSONObject();
                kv.put("packageName", ai.packageName);
                kvs.put(kv);
                this.count += 1;
            }
            rec.put("rec", kvs);
            recs.put("recs", rec);
            this.info = recs.toString();
            this.opf = this.in + ".json";
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setInfoInXML(){

        try{
            final PackageManager pm = this.context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element recs = doc.createElement("recs");
            this.count = 0;
            for(int i=0; i < packages.size(); i++){
                ApplicationInfo ai = packages.get(i);
                Element rec = doc.createElement("rec");
                Element k = doc.createElement("packageName");
                Text v = doc.createTextNode(ai.packageName);
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
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    protected String getInfo(){ return this.info; }
    protected String getOutPutFile(){ return this.opf; }
    protected String getInfoName(){ return this.in; }
    protected int getCount(){ return this.count; }
}
