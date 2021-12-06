import java.util.ArrayList;
class MobilePhone implements ContactConstants {
	private String myNumber;
	public ArrayList<Contact> myContacts;

	public MobilePhone(String myNumber){
		this.myNumber = myNumber;
		myContacts = new ArrayList<>();
	}

	public boolean addContact(Contact contact, boolean fromXML){
		if(!fromXML){
			if(searchByName(contact.getName())){
				return false;
			}
		}

		return this.myContacts.add(contact);
	}

	public int updateContact(Contact oldContact, Contact newContact){
		int contactPosition = findByName(oldContact.getName());

		if(contactPosition < 0){
			return CONTACT_DOES_NOT_EXIST;
		}
		if(searchByName(newContact.getName()) && (! oldContact.getName().equalsIgnoreCase(newContact.getName()))){
			return CONTACT_NAME_ALREADY_EXISTS;
		}

		this.myContacts.set(contactPosition, newContact);
		return CONTACT_UPDATED;
	}

	public int removeContact(Contact contact){
		int contactPosition = findByName(contact.getName());
		if(contactPosition < 0){
			return CONTACT_DOES_NOT_EXIST;
		}

		myContacts.remove(contact);
		return CONTACT_DELETED;
	}

	public ArrayList<Contact> getContacts(){
		return myContacts;
	}

	public void printContacts() {
		System.out.println("Contact List: ");
		for(int i=0; i<myContacts.size(); i++){
			Contact currentContact = myContacts.get(i);
			System.out.println((i+1) + ". " + currentContact);
		}
	}

	public boolean searchByName(String name){
		return findContact(name, true) >= 0;
	}
	public int findByName(String name){
		return findContact(name, true);
	}
	public boolean searchByPhoneNumber(String phoneNumber){
		return findContact(phoneNumber, true) >= 0;
	}
	public int findByPhoneNumber(String phoneNumber){
		return findContact(phoneNumber, true);
	}

	private int findContact(String key, boolean isName){
		if(myContacts.size() == 0){
			return -1;
		}
		for(int i = 0; i <myContacts.size(); i++){
			Contact currentContact = myContacts.get(i);
			System.out.println(i);
			if(isName && currentContact.getName().equalsIgnoreCase(key)){
				return i;
			}
			if(currentContact.getPhoneNumber().equalsIgnoreCase(key)){
				return i;
			}
		}

		return -1;
	}
}