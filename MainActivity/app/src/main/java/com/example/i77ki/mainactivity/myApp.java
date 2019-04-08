protected void onCreate(Bundle savedInstanceState) {
		// acceptButtton, inputTextBox associated with elements of text_input.xml
		// When button clicked, as in appendToFile() then as in readFromFile

		super.onCreate(savedInstanceState);
		setContentView(R.layout.input);

		this.inputTextBox = (EditText) findViewById(R.id.text_input);

		Button acceptButton = (Button) findViewById(R.id.accept_button);
		acceptButton.setOnClickListener(new View.OnClickListener() {
public void onClick(final View v) {
		inputString = inputTextBox.getText().toString();
		appendToFile();
		readFromFile();
		}});
		}
		The method appendToFile() is based on the file appendage operation as follows:

		FileOutputStream fos;
		fos = openFileOutput("appendageFile.txt", Context.MODE_APPEND);
		fos.write(inputString.getBytes());
		In full:

public void appendToFile(){
// Postcondition: inputString is at the end of file appendageFile.txt
// and this action has been Toasted
		Context context = getApplicationContext();  // for Toasts
		try {
		FileOutputStream fos;
		fos = openFileOutput("appendageFile.txt", Context.MODE_APPEND);
		fos.write(inputString.getBytes());
		Toast toast = Toast.makeText(context, inputString, Toast.LENGTH_LONG);
		toast.show();
		}
		catch(Exception e){
		// Toast exception
		CharSequence text = "Could not open appendageFile.txt.";
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		toast.show();
		}
		}
		The key parts of reading from a file are based on the FileInputStream fis, the byte array reader, and the read operation of FileInputStream that takes a byte array as input and output argument as below:

		FileInputStream fis;
		fis = openFileInput("appendageFile.txt");
		byte[] reader = new byte[fis.available()];  // create blank
		… fis.read(reader) …
		If there is nothing to read, read() returns -1.In full, readFromFile() is as follows:

public void readFromFile(){
// Postcondition: Contents of file appendageFile.txt is on a Toast
		Context context = getApplicationContext();  // for Toast
		try {
		FileInputStream fis;
		fis = openFileInput("appendageFile.txt");
		byte[] reader = new byte[fis.available()];  // create blank
		while (fis.read(reader) != -1) {}
		CharSequence text1 = new String(reader);
		Toast toast = Toast.makeText(context, "FOLLOWING READ: " + text1, Toast.LENGTH_LONG);
		toast.show();
		}
		catch(Exception e){
		// Toast exception
		CharSequence text = "Could not open appendageFile.txt.";
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		toast.show();
		}