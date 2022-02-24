package g4n.bole;

import android.content.Context;

public abstract class InfoReader{
    private Context context;
    private OutputFormat fmt;
    private String info;
    private String in;
    private int count;
    private String opf;
    protected InfoReader(Context context, OutputFormat fmt){
        this.context = context;
        this.fmt = fmt;
    }
    protected void setInfo(){
        switch(this.fmt){
            case JSON:
                setInfoInJSON();
                break;
            case XML:
            default:
                setInfoInXML();
                break;
        }
    }
    abstract void setInfoInJSON();
    abstract void setInfoInXML();
    protected String getInfo(){ return this.info; }
    protected String getOutPutFile(){ return this.opf; }
    protected String getInfoName(){ return this.in; }
    protected int getCount(){ return this.count; }
}
