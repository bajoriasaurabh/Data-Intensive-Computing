import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PreprocessingTweetsActivity2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File folder = new File("Tweets\\RawTweets\\");
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					FileReader fr = new FileReader("Tweets\\RawTweets\\" + listOfFiles[i].getName());
					BufferedReader br = new BufferedReader(fr);
					FileWriter fw = new FileWriter("Tweets\\ProcessedTweets\\" + listOfFiles[i].getName());
					BufferedWriter bw = new BufferedWriter(fw);
					String s = null;
					while ((s = br.readLine()) != null) {
						if (s.contains("@") || s.contains("#") && !s.contains("<") && !s.contains(">")) {
							s = s.replaceAll("\"", "").replaceAll("\'", "").replaceAll("â€œ", "").replaceAll("â€?", "")
									.replaceAll("}", "").replaceAll("\\)", "").replaceAll("\\(", "")
									.replaceAll("\\[", "").replaceAll("\\]", "")
									.replaceAll("[!\\\"$%&'()*+,;:.<=>?[\\\\]^`{|}~\\u201C\\u201D\\r\\n]", "");
							if (s.equals("@") || s.equals("#")) {
							} else {
								bw.write(s);
								bw.newLine();
							}
						}
					}

					bw.close();
					br.close();
					fw.close();
					fr.close();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
