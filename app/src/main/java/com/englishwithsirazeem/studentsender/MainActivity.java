package com.englishwithsirazeem.studentsender;
import android.app.*;import android.os.*;import android.content.*;import android.net.Uri;import android.text.*;import android.widget.*;import java.net.URLEncoder;import java.util.*;
public class MainActivity extends Activity { EditText message,numbers; TextView count,progress; ArrayList<String> list=new ArrayList<>(); int index=0; SharedPreferences prefs;
public void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);prefs=getSharedPreferences("campaign",0);bind();load();}
void bind(){message=findViewById(R.id.message);numbers=findViewById(R.id.numbers);count=findViewById(R.id.count);progress=findViewById(R.id.progress); numbers.addTextChangedListener(new TextWatcher(){public void beforeTextChanged(CharSequence s,int a,int c,int d){}public void onTextChanged(CharSequence s,int a,int b,int c){refresh();}public void afterTextChanged(Editable e){}}); findViewById(R.id.whatsapp).setOnClickListener(v->openWhatsApp());findViewById(R.id.sms).setOnClickListener(v->openSms());findViewById(R.id.next).setOnClickListener(v->move(1));findViewById(R.id.previous).setOnClickListener(v->move(-1));findViewById(R.id.skip).setOnClickListener(v->move(1));findViewById(R.id.clear).setOnClickListener(v->{message.setText("");numbers.setText("");index=0;save();});}
void move(int d){refresh();if(list.isEmpty())return;index=Math.max(0,Math.min(list.size()-1,index+d));save();update();}
void refresh(){list=parse(numbers.getText().toString());if(index>=list.size())index=Math.max(0,list.size()-1);count.setText(list.size()+" valid number"+(list.size()==1?"":"s"));update();}
ArrayList<String> parse(String raw){LinkedHashSet<String> s=new LinkedHashSet<>();for(String p:raw.split("[\\s,;]+")){String x=p.replaceAll("[^0-9+]","");if(x.startsWith("0092"))x="0"+x.substring(4);else if(x.startsWith("+92"))x="0"+x.substring(3);else if(x.startsWith("92")&&x.length()>=11)x="0"+x.substring(2);if(x.matches("03\\d{9}"))s.add(x);}return new ArrayList<>(s);}
void update(){progress.setText(list.isEmpty()?"Ready — add student numbers":"Student "+(index+1)+" / "+list.size()+"\n"+list.get(index));}
void openWhatsApp(){
    if(!check())return;
    try{
        String phone="92"+list.get(index).substring(1);
        String q=URLEncoder.encode(message.getText().toString(),"UTF-8");
        Uri uri=Uri.parse("https://wa.me/"+phone+"?text="+q);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        // Force the regular WhatsApp app (com.whatsapp), not WhatsApp Business.
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }catch(Exception e){
        toast("Regular WhatsApp is not installed");
    }
}
void openSms(){if(!check())return;try{Intent i=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+list.get(index)));i.putExtra("sms_body",message.getText().toString());startActivity(i);}catch(Exception e){toast("No SMS app is available");}}
boolean check(){refresh();if(list.isEmpty()){toast("Add student numbers first");return false;}if(message.getText().toString().trim().isEmpty()){toast("Write a message first");return false;}return true;}
void save(){prefs.edit().putString("message",message.getText().toString()).putString("numbers",numbers.getText().toString()).putInt("index",index).apply();}void load(){message.setText(prefs.getString("message",""));numbers.setText(prefs.getString("numbers",""));index=prefs.getInt("index",0);refresh();}@Override protected void onPause(){save();super.onPause();}void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}}