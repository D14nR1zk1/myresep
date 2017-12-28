package com.resep.myresep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MenusetActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    ImageButton []a=new ImageButton[4];
    public static int category_id = 3; // kategori yang akan di tampilkan
    public static String config_path="http://10.0.2.2/mm/";
    public static String config_url = config_path+"mm.php";                // link website
    public static String id;
    private int num_record=10;        // default jumlah record yang ditampilkan
    private String prov_id[];
    private TextView t0,t1,t2;
    private Button refresh, more;
    private Context ctx = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); ImageButton []a=new ImageButton[3];
        setContentView(R.layout.activity_menuset);
        a[0]=(ImageButton)findViewById(R.id.btnhome);
        a[1]=(ImageButton)findViewById(R.id.btnfav);
        a[2]=(ImageButton)findViewById(R.id.btnsc);
        t0 = (TextView) findViewById(R.id.tnama_m);
        t1 = (TextView) findViewById(R.id.tbahan);
        t2 = (TextView) findViewById(R.id.tcara);
        getData();
        //lv.setTextFilterEnabled(true);
        for(int i=0;i<a.length;i++){
            a[i].setOnClickListener(this);
            System.out.println(i);
        }


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
            Intent mm=new Intent().setClass(this, MenusetActivity.class);
            startActivity(mm);
            //setContentView(R.layout.cari);
        }

    }

    public void getData() {
        String data[] = { "Tidak Ada Data" };
        try {
            URL url = new URL(config_url + "?f=10&c=" + id+"&m="+num_record);
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
                t0.setText(data[i]);

                nameList = fstElmnt.getElementsByTagName("bahan");
                nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                String l1= ((Node) nameList.item(0)).getNodeValue();
                t1.setText(l1);

                nameList = fstElmnt.getElementsByTagName("description");
                nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                String l2= ((Node) nameList.item(0)).getNodeValue();
                t2.setText(l2);
            }
            if(nodeList.getLength()==0){
                prov_id=null;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
