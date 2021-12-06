import java.io.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MobilePhoneDemo extends JFrame{
	static MobilePhone myPhone;
	static MobilePhoneDemo obj;
	NumPad nObj;

	JPanel addContactPanel, editContactPanel, contactListPanel;
	JPanel addNameInp, addNumInp, addSubmit;
	JTextField addContactName, addContactNumber;
	JLabel addNameAlert, addNumAlert, addSubAlert;
	JButton addContact;
	JPanel editOldNameInp, editOldNumInp, editSubmit;
	JTextField editOldContactName, editOldContactNumber;
	JLabel editOldNameAlert, editOldNumAlert, editSubAlert;
	JPanel editNewNameInp, editNewNumInp;
	JTextField editNewContactName, editNewContactNumber;
	JLabel editNewNameAlert, editNewNumAlert;
	JButton editContact;
	JPanel removeSubmit;
	JButton removeContact;
	JLabel removeSubAlert;
	JTable contactTable;

	boolean isOpen = false;
	final String colHeads[] = {"Name", "Number"};
	String contactList[][] = new String[100][2];
	int contactCount = 0;
	public MobilePhoneDemo(){
		Container contentPane = getContentPane();
		JTabbedPane jtp = new JTabbedPane();
		jtp.add("Add Contact", addContactLayout());
		jtp.add("Edit Contact", editContactLayout());
		jtp.add("Contact List", contactListLayout());

		contentPane.add(jtp);

		setSize(600, 700);
		setVisible(true);

		addWindowListener (new WindowAdapter() {    
            		public void windowClosing (WindowEvent e) {
            			WriteOnExit woe = new WriteOnExit();
            			woe.addToXML(obj);  
		                System.exit(0);    
            		}	
		});
	}							

	public JPanel addContactLayout(){	
		addContactPanel = new JPanel();
			addContactPanel.setLayout(new GridLayout(3,1, 10, 10));

			addNameInp = new JPanel();
				addNameInp.add(new JLabel("Enter Contact Name: 	"));
				addContactName = new JTextField("", 25);
				addNameAlert = new JLabel("");
				addNameInp.add(addContactName);
				addNameInp.add(addNameAlert);
			addContactPanel.add(addNameInp);

			addNumInp = new JPanel();
				addNumInp.add(new JLabel("Enter Contact Number: 	"));
				addContactNumber = new JTextField("", 25);
				addContactNumber.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me){
						if(!isOpen){
						isOpen = true;
						nObj = new NumPad(obj, "NumberPad", obj, 1);}
					}
				});
				addNumAlert = new JLabel("");
				addNumInp.add(addContactNumber);
				addNumInp.add(addNumAlert);
			addContactPanel.add(addNumInp);

			addSubmit = new JPanel();
				addSubmit.setLayout(new GridLayout(2,1,10,10));
				addSubmit.setLayout(new FlowLayout());
				addContact = new JButton("Add Contact!");
				addContact.addActionListener(new AddNewContact(this));
				addSubAlert = new JLabel("");
				addSubmit.add(addContact);
				addSubmit.add(addSubAlert);
			addContactPanel.add(addSubmit);
		return addContactPanel;
	}

	public JPanel editContactLayout(){
		editContactPanel = new JPanel();
			editContactPanel.setLayout(new GridLayout(3, 2, 10, 10));

			editOldNameInp = new JPanel();
				editOldNameInp.add(new JLabel("Enter Old Contact Name: 	"));
				editOldContactName = new JTextField("", 25);
				editOldNameAlert = new JLabel("");
				editOldNameInp.add(editOldContactName);
				editOldNameInp.add(editOldNameAlert);
			editContactPanel.add(editOldNameInp);

			editNewNameInp = new JPanel();
				editNewNameInp.add(new JLabel("Enter New Contact Name: 	"));
				editNewContactName = new JTextField("", 25);
				editNewNameAlert = new JLabel("");
				editNewNameInp.add(editNewContactName);
				editNewNameInp.add(editNewNameAlert);
			editContactPanel.add(editNewNameInp);

			editOldNumInp = new JPanel();
				editOldNumInp.add(new JLabel("Enter Old Contact Number: 	"));
				editOldContactNumber = new JTextField("", 25);
				editOldContactNumber.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me){
						if(!isOpen){
						isOpen = true;
						nObj = new NumPad(obj, "NumberPad", obj, 2);}
					}
				});
				editOldNumAlert = new JLabel("");
				editOldNumInp.add(editOldContactNumber);
				editOldNumInp.add(editOldNumAlert);
			editContactPanel.add(editOldNumInp);


			editNewNumInp = new JPanel();
				editNewNumInp.add(new JLabel("Enter New Contact Number: 	"));
				editNewContactNumber = new JTextField("", 25);
				editNewContactNumber.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me){
						if(!isOpen){
						isOpen = true;
						nObj = new NumPad(obj, "NumberPad", obj, 3);}
					}
				});
				editNewNumAlert = new JLabel("");
				editNewNumInp.add(editNewContactNumber);
				editNewNumInp.add(editNewNumAlert);
			editContactPanel.add(editNewNumInp);

			removeSubmit = new JPanel();
				removeSubmit.setLayout(new GridLayout(2,1,10,10));
				removeSubmit.setLayout(new FlowLayout());
				removeContact = new JButton("remove Contact!");
				removeContact.addActionListener(new removeContact(this));
				removeSubAlert = new JLabel("");
				removeSubmit.add(removeContact);
				removeSubmit.add(removeSubAlert);
			editContactPanel.add(removeSubmit);

			editSubmit = new JPanel();
				editSubmit.setLayout(new GridLayout(2,1,10,10));
				editSubmit.setLayout(new FlowLayout());
				editContact = new JButton("Edit Contact!");
				editContact.addActionListener(new EditOldContact(this));
				editSubAlert = new JLabel("");
				editSubmit.add(editContact);
				editSubmit.add(editSubAlert);
			editContactPanel.add(editSubmit);
		return editContactPanel;
	}

	public JPanel contactListLayout(){
		contactListPanel = new JPanel();
			contactTable = new JTable(contactList, colHeads);
			JScrollPane jsp = new JScrollPane(contactTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			contactListPanel.add(jsp);

		SetTable obj = new SetTable(this);
		return contactListPanel;
	}

	public static void main(String[] args) {
		myPhone = new MobilePhone("9819424096");
		obj = new MobilePhoneDemo();
	}
}

class AddNewContact implements ActionListener{
	MobilePhoneDemo refObj;
	String name;
	String number;

	public AddNewContact(MobilePhoneDemo refObj){
		this.refObj = refObj;
	}
	public void actionPerformed(ActionEvent ae){
		name = refObj.addContactName.getText();
		number = refObj.addContactNumber.getText();
		refObj.addSubAlert.setText("");
		refObj.addNameAlert.setText("");
		refObj.addNumAlert.setText("");
		if(name.equals("")){
			refObj.addNameAlert.setText("Please Enter a Name");
			return;
		}
		if(!(number.length() == 10)){
			refObj.addSubAlert.setText("Please Enter a 10 Digit Number");
			return;
		}
		Contact contact = contactCreation();
		contactAddition(contact);
	}

	private Contact contactCreation(){
		return Contact.createContact(name, number);
	}

	private void contactAddition(Contact contact){
		boolean added = refObj.myPhone.addContact(contact, false);
		if(!added){
			refObj.addSubAlert.setText("Contact already exists, please enter a new Name");
			return;
		}
		refObj.addSubAlert.setText("Contact Has been added!");
			addToTable();
	}

	private void addToTable(){
		refObj.contactList[refObj.contactCount][0] = name;
		refObj.contactList[refObj.contactCount][1] = number;
		refObj.contactCount++;
	}
}

class EditOldContact implements ActionListener{
	MobilePhoneDemo refObj;
	String oldName, newName;
	String oldNum, newNum;

	public EditOldContact(MobilePhoneDemo refObj){
		this.refObj = refObj;
	}

	public void actionPerformed(ActionEvent ae){
		oldName = refObj.editOldContactName.getText();
		oldNum = refObj.editOldContactNumber.getText();
		newName = refObj.editNewContactName.getText();
		newNum = refObj.editNewContactNumber.getText();
		refObj.editOldNameAlert.setText("");
		refObj.editOldNumAlert.setText("");
		refObj.editNewNameAlert.setText("");
		refObj.editNewNumAlert.setText("");
		refObj.removeSubAlert.setText("");
		if(oldName.equals("")){
			refObj.editOldNameAlert.setText("Please Enter Contact Name");
			return;
		}	
		if(newName.equals("")){
			refObj.editNewNameAlert.setText("Please Enter Contact Name");
			return;
		}
		if(!(oldNum.length() == 10 && newNum.length() == 10)){
			refObj.editSubAlert.setText("Please Enter a 10 Digit Number");
			return;
		}
		Contact oldContact = contactCreation(oldName, oldNum);
		Contact newContact = contactCreation(newName, newNum);
		contactEditing(oldContact, newContact);
	}

	private void contactEditing(Contact oldContact, Contact newContact){
		int status = refObj.myPhone.updateContact(oldContact, newContact);
		switch(status){
			case 0:
				refObj.editSubAlert.setText("Contact Does Not Exist");
				return;
			case 1:
				refObj.editSubAlert.setText("Contact Already Exists");
				return;
			case 2:
				refObj.editSubAlert.setText("Contact Edit");
				editOnTable();
				break;
		}
	}

	private void editOnTable(){
		for(int i = 0; i < refObj.contactList.length; i++){
			if(oldName.equals(refObj.contactList[i][0])){
				System.out.println("Contact Found!");
				refObj.contactList[i][0] = newName;
				refObj.contactList[i][1] = newNum;
				return;
			}
		}
	}

	private Contact contactCreation(String name, String num){
		return Contact.createContact(name, num);
	}
}

class removeContact implements ActionListener{
	MobilePhoneDemo refObj;
	public removeContact(MobilePhoneDemo refObj){
		this.refObj = refObj;
	}

	public void actionPerformed(ActionEvent ae){
		String name = refObj.editOldContactName.getText();
		refObj.removeSubAlert.setText("");
		if(name.equals("")){
			refObj.removeSubAlert.setText("Please Enter a Name");
		}
		for(int i = 0; i < refObj.contactList.length; i++){
			if(name.equalsIgnoreCase(refObj.contactList[i][0])){
				shiftContacts(i);
				// refObj.contactList.length -= 1;
				refObj.removeSubAlert.setText("Contact Removed!");
			}
		}
	}

	private void shiftContacts(int i){
		for(; i < refObj.contactList.length-1; i++){
			refObj.contactList[i][0] = refObj.contactList[i+1][0];
			refObj.contactList[i][1] = refObj.contactList[i+1][1];
		}
	}
}

class WriteOnExit {
	MobilePhoneDemo refObj;
	public void addToXML(MobilePhoneDemo refObj){
		this.refObj = refObj;
		clearFile();
		try{
			File xmlFile = new File("C:\\Sublime Text\\Advance Java\\Array-and-list\\Phone Project\\ContactList.xml");
			FileOutputStream fout = new FileOutputStream(xmlFile, true);
			String contactInfo = setContactInfo();
			byte b[] = contactInfo.getBytes();
			fout.write(b);
			fout.close();
		}	catch(IOException ie){}
	}

	private void clearFile(){
		try {
			PrintWriter pw = new PrintWriter("C:\\Sublime Text\\Advance Java\\Array-and-list\\Phone Project\\ContactList.xml");
			pw.print("");
			pw.close();
		} catch(FileNotFoundException fe){}
	}
	private String setContactInfo(){
		String str = "";
		for(int i = 0; i < refObj.contactCount; i++){
			String name = refObj.contactList[i][0];
			String contact = refObj.contactList[i][1];
			str += "<contact>\n\t<name>"+name+"</name>\n\t<number>"+contact+"</number>\n</contact>\n\n";
		}
		return str;
	}
}

class SetTable {
	MobilePhoneDemo refObj;
	String nameRegex = "\\s(<name>)(.*)(</name>)";
	String numRegex = "\\s(<number>)(.*)(</number>)";
	String contactName, contactNumber;
	boolean didFind = false;
	public SetTable(MobilePhoneDemo refObj){
		this.refObj = refObj;
		try{
			File xmlFile = new File("C:\\Sublime Text\\Advance Java\\Array-and-list\\Phone Project\\ContactList.xml");
			FileInputStream fin = new FileInputStream(xmlFile);
			BufferedReader sc = new BufferedReader(new InputStreamReader(fin));
			
			Pattern namePattern = Pattern.compile(nameRegex);
			Pattern numberPattern = Pattern.compile(numRegex);

			String str = sc.readLine();
			while(str != null){	
				didFind = false;
				Matcher nameMatcher = namePattern.matcher(str);
				Matcher numMatcher = numberPattern.matcher(str);
				if(nameMatcher.matches()){
					contactName =  str.substring(nameMatcher.start()+7, nameMatcher.end()-7);
					refObj.contactList[refObj.contactCount][0] = contactName;
				}	else if(numMatcher.matches()){
					contactNumber = str.substring(numMatcher.start()+9, numMatcher.end()-9);
					refObj.contactList[refObj.contactCount][1] = contactNumber;
					refObj.contactCount++;
					didFind = true;
				}
				str = sc.readLine();
				if(didFind){
					Contact contact = Contact.createContact(contactName, contactNumber); 
					boolean status = MobilePhoneDemo.myPhone.addContact(contact, true);
				}
			}
		}	catch(IOException ie){}
	}
}

class NumPad extends JDialog implements ActionListener{
	JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bDel;
	MobilePhoneDemo refObj;
	int fieldNum;
	NumPad(JFrame parents, String title, MobilePhoneDemo refObj, int fieldNum){
		super(parents, title, false);
		this.refObj = refObj;
		this.fieldNum = fieldNum;

		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(4, 3, 10, 10));

		b1 = new JButton("1");
		b1.addActionListener(this);
		contentPane.add(b1);

		b2 = new JButton("2");
		b2.addActionListener(this);
		contentPane.add(b2);

		b3 = new JButton("3");
		b3.addActionListener(this);
		contentPane.add(b3);

		b4 = new JButton("4");
		b4.addActionListener(this);
		contentPane.add(b4);

		b5 = new JButton("5");
		b5.addActionListener(this);
		contentPane.add(b5);

		b6 = new JButton("6");
		b6.addActionListener(this);
		contentPane.add(b6);

		b7 = new JButton("7");
		b7.addActionListener(this);
		contentPane.add(b7);

		b8 = new JButton("8");
		b8.addActionListener(this);
		contentPane.add(b8);

		b9 = new JButton("9");
		b9.addActionListener(this);
		contentPane.add(b9);

		contentPane.add(new JLabel(""));

		b0 = new JButton("0");
		b0.addActionListener(this);
		contentPane.add(b0);

		bDel = new JButton("<-");
		bDel.addActionListener(this);
		contentPane.add(bDel);

		setSize(300, 255);
		setVisible(true);
		
		addWindowListener (new WindowAdapter() {    
            		public void windowClosing (WindowEvent e) {    
            			refObj.isOpen = false;
		                dispose();
            		}
		}); 
	}

	public void actionPerformed(ActionEvent ae){
		String oldText = checkText();
		String newText = "";
		JButton buttonClicked = (JButton) ae.getSource();

		if(buttonClicked == bDel){
			for(int i = 0; i < oldText.length() - 1; i++){
				newText += oldText.charAt(i);
				changeText(newText);
			}
		} else {
			newText = oldText + buttonClicked.getText();
			changeText(newText);
		}
	}

	private String checkText(){
		if(fieldNum == 1){
			return refObj.addContactNumber.getText();
		}	else if(fieldNum == 2){
			return refObj.editOldContactNumber.getText();
		}	else if(fieldNum == 3){
			return refObj.editNewContactNumber.getText();
		}
		return "";
	}

	private void changeText(String newText){
		if(fieldNum == 1){
			refObj.addContactNumber.setText(newText);
		}	else if(fieldNum == 2){
			refObj.editOldContactNumber.setText(newText);
		}	else if(fieldNum == 3){
			refObj.editNewContactNumber.setText(newText);
		}
	}
}