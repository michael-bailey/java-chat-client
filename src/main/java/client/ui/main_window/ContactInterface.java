package client.ui.main_window;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class ContactInterface{
	private ArrayList<Contact> contactArr;
	private int ids = 0;
	public ContactInterface(){
		contactArr = new ArrayList<Contact>();
	}
	public void createContact(String name){
		Contact contact = new Contact(name,this.ids);
		contactArr.add(contact);
		this.ids++;
	}
	public Contact getContact(int id){return contactArr.get(id);}
	public Contact getContact(String name){
		for(int i=0;i<contactArr.size();i++){
			if(contactArr.get(i).getName().equals(name)){return contactArr.get(i);}
		}
		return null;
	}
	public int getContactArrSize(){return contactArr.size();}
	/*public void SerializeArr(){
		try{
			FileOutputStream file = new FileOutputStream(this.filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(contactArr);
			out.close();
			file.close();
			System.out.println("Object Successfully Serialized");
		}catch(IOException e){
			System.out.println("Error occured:\n"+e);
		}
	}
	public Contact DeserializeObj(String filename){
		try{
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			A newContact = (Contact)in.readObject();
			in.close();
			file.close();
			System.out.println("Object Successfully Deserialized");
		}catch(IOException e){System.out.println("Error occured:\n"+e);}
		catch(ClassNotFoundException e){System.out.println("Class could not be found:\n"+e);}
	}*/
}

class Contact implements Serializable{
	private String name;
	private int id;
	private VBox contactVB = new VBox();
	public Contact(String name,int id){
		this.name=name;
		this.id=id;
		Image img = new Image(getClass().getResourceAsStream(""));
		Button contactBtn = new Button();
		contactBtn.setGraphic(new ImageView(img));
		contactBtn.setId("BtnDesign");
		contactBtn.getStylesheets().add("contactCss.css");
		contactVB.getChildren().add(contactBtn);
	}
	public VBox getContactVB(){return contactVB;}
	public String getName(){return name;}
}
