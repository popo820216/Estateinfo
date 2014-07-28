package cn.com.example.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.example.activity.R;

public class MyDialog extends Dialog  implements OnClickListener {

    Context context;
    ImageView backdialog;
    TextView consulting;
    private LeaveMeetingDialogListener listener;  
    public interface LeaveMeetingDialogListener{  	
    	  public void onClick(View view);  
   }  

    public MyDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public MyDialog(Context context, int theme,LeaveMeetingDialogListener listener){
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);
        initViews();
    }
    private void initViews(){  
    	 LinearLayout tel = (LinearLayout)this.findViewById(R.id.tel);
    	 tel.setOnClickListener(this);
    	 backdialog = (ImageView)this.findViewById(R.id.backdialog);
    	 consulting = (TextView)this.findViewById(R.id.consulting);
    	 backdialog.setOnClickListener(this);  
    	 consulting.setOnClickListener(this); 
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 listener.onClick(v);  
	}

}