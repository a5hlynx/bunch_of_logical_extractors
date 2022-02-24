package g4n.bole;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.File;

public class MainActivity extends AppCompatActivity{

    private final int SCROLL_DURATION = 0;
    private final int NUM_OF_AGENTS = 12;
    private final int NUM_OF_BUTTONS = 3;
    private final int NUM_OF_RADIO_BUTTONS= 2;
    private final int REQUEST_CODE = 1;
    private CheckBox[] cb = new CheckBox[NUM_OF_AGENTS];
    private Button[] btn = new Button[NUM_OF_BUTTONS];
    private RadioButton[] rb = new RadioButton[NUM_OF_RADIO_BUTTONS];

    private final String[] perms ={
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCheckBoxes(false);
        RadioGroup rg = findViewById(R.id.rg_format);
        rg.check(R.id.rb_xml);
        Button btn = findViewById(R.id.bt_extract);
        setButtons(false);
        setCheckBoxes(false);
        setRadioButtons(false);
        checkPermissions(perms);
        btn.setOnClickListener(v -> {
            setButtons(false);
            setRadioButtons(false);
            new Thread(() -> runAgents(v)).start();
        });

    }

    private void checkPermissions(String[] request_permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermissions(request_permission) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(request_permission, REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private int checkSelfPermissions(String[] request_permission){
        int ret = PackageManager.PERMISSION_GRANTED;
        for(int i=0; i < request_permission.length; i++){
            if((ret = checkSelfPermission(request_permission[i])) != PackageManager.PERMISSION_GRANTED){
                return ret;
            }
        }
        return ret;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    checkPermissions(permissions);
                }else{
                    this.finish();
                    System.exit(1);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        boolean all_granted = true;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(int i=0; i < perms.length; i++){
                if((checkSelfPermission(perms[i])) != PackageManager.PERMISSION_GRANTED){
                    all_granted = false;
                    break;
                }
            }
        }
        if(!all_granted){
            this.finish();
            System.exit(1);
        }else{
            setButtons(true);
            setRadioButtons(true);
        }
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics m = getResources().getDisplayMetrics();
        /* whole */
        ConstraintLayout clo = findViewById(R.id.activityMain);
        int clo_h = clo.getHeight();
        /* toolbar */
        Toolbar tb = findViewById(R.id.toolbar);
        int tb_h = tb.getHeight();
        /* menu */
        ScrollView svll = findViewById(R.id.scrollViewLL);
        ViewGroup.LayoutParams svll_lp = svll.getLayoutParams();
        ViewGroup.MarginLayoutParams svll_mlp = (ViewGroup.MarginLayoutParams) svll_lp;
        int svll_h = svll.getHeight() + (int)(m.density * (svll_mlp.topMargin + svll_mlp.bottomMargin));
        /* radio */
        RadioGroup rg = findViewById(R.id.rg_format);
        ViewGroup.LayoutParams rg_lp = rg.getLayoutParams();
        ViewGroup.MarginLayoutParams rg_mlp = (ViewGroup.MarginLayoutParams) rg_lp;
        int rg_h = rg.getHeight() + (int)(m.density * (rg_mlp.topMargin + rg_mlp.bottomMargin));
        /* button */
        Button bt = findViewById(R.id.bt_all);
        ViewGroup.LayoutParams bt_lp = bt.getLayoutParams();
        ViewGroup.MarginLayoutParams bt_mlp = (ViewGroup.MarginLayoutParams) bt_lp;
        int bt_h = bt.getHeight() + (int)(m.density * (bt_mlp.topMargin + bt_mlp.bottomMargin));
        /* scroll */
        ScrollView svtv = findViewById(R.id.scrollViewTV);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)svtv.getLayoutParams();
        marginLayoutParams.height = clo_h - tb_h - svll_h - rg_h - bt_h;
        svtv.setLayoutParams(marginLayoutParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void runAgents(View view){

        boolean no_chk = true;
        for(int i = 0; i<cb.length;i++){
            if(cb[i].isChecked()){
                no_chk = false;
                break;
            }
        }
        if(no_chk){
            runOnUiThread(() -> {
                setButtons(true);
                setRadioButtons(true);
            });
            return;
        }

        TextView textView = findViewById(R.id.textView);

        String txt = "";
        String dir = Util.getDirForEvidence(this);
        File nd = new File(dir);
        nd.mkdirs();
        final String _msg = "Start Extraction...\n\n";
        runOnUiThread(() -> setTextInView(textView, _msg));

        txt = _msg;
        /* format */
        RadioGroup rg = findViewById(R.id.rg_format);
        OutputFormat fmt = OutputFormat.XML;
        if(rg.getCheckedRadioButtonId() == R.id.rb_json){
            fmt = OutputFormat.JSON;
        }
        /* browser */
        if(cb[0].isChecked()){
            BrowserRetriever r = new BrowserRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* calender */
        if(cb[1].isChecked()){
            CalendarRetriever r = new CalendarRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* call log */
        if(cb[2].isChecked()){
            CallLogRetriever r = new CallLogRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* contact */
        if(cb[3].isChecked()){
            ContactRetriever r = new ContactRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* media */
        if(cb[4].isChecked()){
            MediaRetriever r = new MediaRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* im */
        if(cb[5].isChecked()){
            IMRetriever r = new IMRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* sms */
        if(cb[6].isChecked()){
            TelephonyRetriever r = new TelephonyRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* system */
        if(cb[7].isChecked()){
            SystemRetriever r = new SystemRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* user dictionary */
        if(cb[8].isChecked()){
            DictionaryRetriever r = new DictionaryRetriever(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* accounts */
        if(cb[9].isChecked()){
            AccountReader r = new AccountReader(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* packages */
        if(cb[10].isChecked()){
            PackageReader r = new PackageReader(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        /* providers */
        if(cb[11].isChecked()){
            ProviderReader r = new ProviderReader(this, fmt);
            txt = updateView(r, textView, dir, txt);
        }
        final String _txt = txt + "\n\nDone...\n\n" + "Files are saved under " + dir + "\n\n";
        runOnUiThread(() -> setTextInView(textView, _txt));

        runOnUiThread(() -> {
            setButtons(true);
            setRadioButtons(true);
        });
    }

    private String updateView(InfoReader r, TextView textView, String dir, String info){
        r.setInfo();
        if(r.getOutPutFile().length() == 0)
            return info;
        Util.saveEvidence(dir + "/" + r.getOutPutFile(), r.getInfo());
        info = info + r.getInfoName() + "\n\t\t"
                        + r.getCount() + " recs Read & Saved into " + r.getOutPutFile() + "\n";
        final String _info= info;
        setTextInView(textView, _info);
        return info;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String updateView(ContentRetriever r, TextView textView, String dir, String content){
        r.setContent();
        for(int i=0; i < r.getNumOfUri(); i++){
            if(r.getOutPutFiles()[i].length() == 0){
                continue;
            }
            Util.saveEvidence(dir + "/" + r.getOutPutFiles()[i], r.getContents()[i]);
            content = content + r.getCONTENT_URIs()[i] + "\n\t\t"
                                + r.getCounts()[i] + " recs Retrieved & Saved into " + r.getOutPutFiles()[i] + "\n";
            final String _content = content;
            setTextInView(textView, _content);
        }
        return content;
    }

    public void checkAll(View view){
        setCheckBoxes(true);
    }

    public void clearAll(View view){
        setCheckBoxes(false);
        TextView textView = findViewById(R.id.textView);
        textView.setText(null);
    }

    private void setButtons(boolean b){
        btn[0] = findViewById(R.id.bt_all);
        btn[1] = findViewById(R.id.bt_clear);
        btn[2] = findViewById(R.id.bt_extract);

        for(int i = 0; i < btn.length; i++){
            btn[i].setClickable(b);
            if(b){
                btn[i].setTextColor(ContextCompat.getColor(this, R.color.white));
            }else{
                btn[i].setTextColor(ContextCompat.getColor(this, R.color.pale_blue));
            }
        }

    }
    private void setRadioButtons(boolean b){
        rb[0] = findViewById(R.id.rb_xml);
        rb[1] = findViewById(R.id.rb_json);
        for(int i = 0; i < rb.length; i++){
            rb[i].setClickable(b);
        }
    }
    private void setCheckBoxes(boolean b){
        cb[0] = findViewById(R.id.cb_browser);
        cb[1] = findViewById(R.id.cb_calendar);
        cb[2] = findViewById(R.id.cb_call_log);
        cb[3] = findViewById(R.id.cb_contacts);
        cb[4] = findViewById(R.id.cb_media);
        cb[5] = findViewById(R.id.cb_im);
        cb[6] = findViewById(R.id.cb_sms);
        cb[7] = findViewById(R.id.cb_system);
        cb[8] = findViewById(R.id.cb_user_dictionary);
        cb[9] = findViewById(R.id.cb_account_info);
        cb[10] = findViewById(R.id.cb_package_info);
        cb[11] = findViewById(R.id.cb_provider_info);
        for(int i = 0; i < cb.length; i++){
            cb[i].setChecked(b);
        }
    }

    private void setTextInView(TextView textView, String content){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                textView.setText(content);
                ScrollView svtv = (ScrollView) findViewById(R.id.scrollViewTV);
                svtv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
                    @Override
                    public void onGlobalLayout(){
                        svtv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(svtv, "scrollY", svtv.getChildAt(0).getHeight() - svtv.getHeight());
                        objectAnimator.setDuration(SCROLL_DURATION);
                        objectAnimator.setInterpolator(new LinearInterpolator());
                        objectAnimator.start();
                    }
                });
            }
        });
    }
}