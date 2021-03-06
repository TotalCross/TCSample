package totalcross.sample.components.ui;

import java.sql.SQLException;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.io.IOException;
import totalcross.sample.components.BaseScreen;
import totalcross.sample.util.Colors;
import totalcross.sql.PreparedStatement;
import totalcross.sql.Statement;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Check;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.ScrollContainer;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;
import totalcross.util.InvalidDateException;
import totalcross.util.UnitsConverter;

public class Login extends BaseScreen {
	private Edit edPass, edLogin;
	private Check ch;
	private Button btLogin, btRegister;
	private ImageControl ic;
    private SQLiteUtil util;
	
	public void onContent(ScrollContainer content){
		try {
			content.setBackForeColors(Colors.BACKGROUND, Colors.ON_BACKGROUND);
			ic = new ImageControl(new Image("images/logo.png"));
			ic.scaleToFit = true;
			ic.centerImage = true;
			content.add(ic, LEFT, TOP + UnitsConverter.toPixels(DP + 15), FILL, PARENTSIZE + 30);
			
			edLogin = new Edit();
			edLogin.caption = "Login";
			//edLogin.setBackColor(Color.RED);
			content.add(edLogin, CENTER, AFTER + UnitsConverter.toPixels(DP + 9), PARENTSIZE + 90, PREFERRED + UnitsConverter.toPixels(DP + 5));
			
			edPass = new Edit();
			edPass.caption = "Password";
			//edPass.setBackColor(Color.RED);
			edPass.setMode(Edit.PASSWORD_ALL);
			content.add(edPass, SAME, AFTER + UnitsConverter.toPixels(DP + 11), PARENTSIZE + 90, PREFERRED + UnitsConverter.toPixels(DP + 5));
			
			ch = new Check("Remember Me");
			content.add(ch, LEFT + UnitsConverter.toPixels(DP + 13), AFTER + UnitsConverter.toPixels(DP + 15), PARENTSIZE, PREFERRED + UnitsConverter.toPixels(DP + 5));
			
			btLogin = new Button("Login");
			btLogin.setBackColor(Color.WHITE);
			content.add(btLogin, CENTER, AFTER + UnitsConverter.toPixels(DP + 21), PARENTSIZE + 80, PREFERRED + UnitsConverter.toPixels(DP + 9));
			btLogin.addPressListener(e -> {
				try{
					if(e.target == btLogin){
						doInsert();
					}
				}catch(Exception ee){
					MessageBox.showException(ee, true);
				}
			});
			btRegister = new Button("Register Now");
			btRegister.transparentBackground = true;
			btRegister.setBorder(BORDER_NONE);
			content.add(btRegister, CENTER, AFTER, PARENTSIZE + 30, PREFERRED + UnitsConverter.toPixels(DP + 3));
			btRegister.addPressListener(e -> {Vm.exec("webview", "http://www.totalcross.com", 0, true);});
			
			//Creating Database
			util = new SQLiteUtil(Settings.appPath,"database.db");
	        Vm.debug(util.fullPath);
			    
	        Statement st = util.con().createStatement();
			st.execute("create table if not exists person (login varchar(20), password varchar(20))");
			st.close();
		
		} catch (IOException | ImageException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void doInsert() throws SQLException, InvalidDateException, ImageException {
		if (edLogin.getLength() == 0 || edPass.getLength() == 0){
			MessageBox mb = new MessageBox("Message","Please fill all fields!",new String[]{"Close"});
			mb.setBackForeColors(Color.WHITE, Color.BLACK);
			mb.popup();
		}else {
		// simple example of how you can insert data into SQLite..
			String sql = "insert into person values(?,?)";
			PreparedStatement st = util.con().prepareStatement(sql);
			st.setString(1, edLogin.getText());
			st.setString(2, edPass.getText());
			st.executeUpdate();
			st.close();		
			
			MessageBox mbox = new MessageBox(" ","Data inserted successfully!");
			mbox.setBackForeColors(Color.WHITE, Color.BLACK);
			mbox.popup();
			
		}
	}
}
