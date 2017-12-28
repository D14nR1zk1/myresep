package com.resep.myresep;

/**
 * Created by Windows on 28/12/2017.
 */
import android.support.v7.app.AppCompatActivity;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Favorit extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    ImageButton []a=new ImageButton[4];
    public static int category_id = 3; // kategori yang akan di tampilkan
    public static String config_path="http://10.0.2.2/mm/";
    public static String config_url = config_path+"mm.php";                // link website
    private int num_record=10;        // default jumlah record yang ditampilkan
    private String prov_id[];
    private ListView lv;
    private Button refresh, more;
    private Context ctx = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton []a=new ImageButton[3];
        setContentView(R.layout.activity_favorit);
        a[0]=(ImageButton)findViewById(R.id.btnhome);
        a[1]=(ImageButton)findViewById(R.id.btnfav);
        a[2]=(ImageButton)findViewById(R.id.btnsc);
        lv = (ListView) findViewById(R.id.listView1);
        refresh = (Button)findViewById(R.id.button1);
        refresh.setOnClickListener(this);
        getData();
        lv.setTextFilterEnabled(true);
        for(int i=0;i<a.length;i++){
            a[i].setOnClickListener(this);
            System.out.println(i);
        }
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if(prov_id != null){
                    Toast.makeText(getApplicationContext(),
                            ((TextView) arg1).getText()+" - "+arg2, Toast.LENGTH_SHORT).show();
                    MenusetActivity.id= prov_id[arg2];
                    startActivity(new Intent(ctx, MenusetActivity.class));
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v==a[0]){
            Intent mm=new Intent().setClass(this, ResepActivity.class);
            startActivity(mm);
        }
        if(v==a[1]){
            Intent mm=new Intent().setClass(this, Favorit.class);
            startActivity(mm);
            //setContentView(R.layout.cari);
        }
        if(v==a[2]){
            Intent mm=new Intent().setClass(this, SearchActivity.class);
            startActivity(mm);
            //setContentView(R.layout.cari);
        }
        if(v==a[3]){
            Intent mm=new Intent().setClass(this, Favorit.class);
            startActivity(mm);
            //setContentView(R.layout.cari);
        }
        if(v==refresh){
            num_record = 10;            // refresh data 10 record pertama
            getData();
            Toast.makeText(getApplicationContext(),"Refresh Data Success!", Toast.LENGTH_SHORT).show();
        }

    }

    public void getData() {
        String data[] = { "Tidak Ada Data" };
        try {
            URL url = new URL(config_url + "?f=12&c=" + category_id+"&m="+num_record);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("item");
            data = new String[nodeList.getLength()];
            prov_id = new String[nodeList.getLength()];
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("title");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                data[i] = ((Node) nameList.item(0)).getNodeValue();

                nameList = fstElmnt.getElementsByTagName("link");
                nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                prov_id[i]= ((Node) nameList.item(0)).getNodeValue();

            }
            if(nodeList.getLength()==0){
                prov_id=null;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);
    }
}