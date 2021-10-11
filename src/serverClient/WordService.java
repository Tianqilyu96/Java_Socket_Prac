package serverClient;

import java.io.*;





public  class WordService implements WordMethod {

	@Override
	//search word method
	public String searchword(String word, String filepath) throws Exception{
		String result = "";
		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("New file created...");
			}
			
		
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        while (line != null) {
			line = br.readLine();
			if (line != null) {
				String [] elements = line.split(":");
				if (elements[0].equalsIgnoreCase(word)) {
					result = "The meaning is:" + elements[1];
					System.out.println(result);
					break;
				}
			}
			
		}
        br.close();
		} catch (Exception e) {
			return "IO excepion...";
		}
		if (result == "") {
			result = "Word is not found";
		}
		return result;
	}
    @Override
    //add word method
	public String addword(String word, String meaning, String filepath) throws Exception{
		String result = "";
		//try to get the file
		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("New file created...");
			}
			
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
		String addline = word + ":" + meaning;
		String readline = "init:init";
		//add the word and meaning if the word is new
		while (readline != null) {
			readline = br.readLine();
			if (readline != null) {
				String [] elements = readline.split(":");
				if (elements[0].equalsIgnoreCase(word)) {
					result = "Duplicate word...";
					
					break;
				}
			}
				else {
					bw.write(addline + '\n');
					result = "Word added...";
					bw.flush();
					bw.close();
					br.close();
					break;
				}
			}
		
		} catch (Exception e) {
			return "IO exception...";
		}
        
		return result;
	}

	@Override
	// delete word method 
	public String deleteword(String word, String filepath) throws Exception {
		
        String result = "";
		//try to get the file
		try {
			String line = "init";
			int count = 0;
			
			File file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("New file created...");
			}

			
			
	        FileReader fr = new FileReader(file);
	        LineNumberReader lnr = new LineNumberReader(fr);
	        lnr.skip(Long.MAX_VALUE);
	        int lines = lnr.getLineNumber();
	        lnr.close();
	        String[] filecontant = new String[lines];
	        FileReader fr2 = new FileReader(file);
	        BufferedReader br = new BufferedReader(fr2);
	        
	        while (line != null) {
	        	
	        	line = br.readLine();
	        	
	        	if (line != null) {
	        		String [] elements = line.split(":");
	        		if (elements[0].equalsIgnoreCase(word)) {
	        			result = "Word is deleted...";
	        			continue;
	        		}
	        		else {
	        			filecontant[count++] = line;
	        		}
	        		
	        	}
	        }
	        if (result == "") {
	        	result = "Word is not found...";
	        	System.out.println(result);
	        }
	        FileWriter fw = new FileWriter(file);
	        BufferedWriter bw = new BufferedWriter(fw);
	        for (int i = 0;i < count; i++) {
	        	bw.write(filecontant[i] + '\n');
	        	bw.flush();
	        }
	        br.close();
            bw.close();
	       
	       System.out.println(result); 
		} catch (Exception e) {
			return "IO exception";
		}
		return result;
	}

}
