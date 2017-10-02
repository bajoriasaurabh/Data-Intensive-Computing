1. After extracting tweets using R, run the jar file "PreprocessingTweetsActivity2.jar" using the following command:
	java -jar PreprocessingTweetsActivity2.jar
	This will clean the tweets and remove all unwanted characters

2. 	Put these preprocessed tweets to a input folder 'inputActivity2' in the hadoop cluster using the following command:
	hdfs dfs -mkdir -p ~/inputActivity2												-- this will create a directory inputActivity2 at /home/hadoop/
	hdfs dfs -put <SharedFolder>/Activity2/Tweets/ProcessedTweets/ ~/inputActivity2			-- put the folder ProcessedTweets/ in inputActivity2

3. 	Wrote a java code for Pairs and transferred the file Pairs.java from local machine to Hadoop VM using shared folder. Please find the Pairs.java file along with the submission for reference.
	Move the file to /home/hadoop/ folder

4. Created the jar file using the commands given in the MR guide provided:
	hadoop com.sun.tools.javac.Main Pairs.java
	jar cf Pairs.jar Pairs*.class
	
5. Run the jar file for wordcount using the following command:
	hadoop jar Pairs.jar Pairs ~/inputActivity2/ProcessedTweets/ ~/outputActivity2Pairs
	
6. Get the output from hadoop to the local machine:
	hdfs dfs -get ~/outputActivity2Pairs <SharedFolder>/Activity2/

7. Repeat steps 3 to 6 for Stripes.java as following:

8. 	Wrote a java code for Stripes and transferred the file Stripes.java from local machine to Hadoop VM using shared folder. Please find the Stripes.java file along with the submission for reference.
	Move the file to /home/hadoop/ folder

9. Created the jar file using the commands given in the MR guide provided:
	hadoop com.sun.tools.javac.Main Stripes.java
	jar cf Stripes.jar Stripes*.class
	
10. Run the jar file for wordcount using the following command:
	hadoop jar Stripes.jar Stripes ~/inputActivity2/ProcessedTweets/ ~/outputActivity2Stripes
	
11. Get the output from hadoop to the local machine:
	hdfs dfs -get ~/outputActivity2Stripes <SharedFolder>/Activity2/
	
Note: Tweets folder contain two folders: RawTweets --> Raw tweets extracted using R code for 3 topics: Economy, Soccer and Cricket
										 ProcessedTweets --> ProcessedTweets cleaned using Java code
	  Pairs folder contains the source code, class files and the jar file for Pairs
	  Stripes folder contains the source code, class files and the jar file for Stripes
	  outputActivity2Pairs and outputActivity2Stripes contains outputs for both Pairs and Stripes respectively