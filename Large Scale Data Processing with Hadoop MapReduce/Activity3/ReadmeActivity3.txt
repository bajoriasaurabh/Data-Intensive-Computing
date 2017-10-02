1. 	Put the 2 latin files lucan.bellum_civile.part.1.tess and vergil.aeneid.tess in the hadoop cluster using the following command:
	hdfs dfs -mkdir -p ~/inputActivity3												-- this will create a directory inputActivity3 at /home/hadoop/
	hdfs dfs -put <SharedFolder>/Activity3/inputActivity3/ ~/inputActivity3			-- put the folder inputActivity3/ in inputActivity3

2. 	Wrote a java code for WordCount_Latin and transferred the file WordCount_Latin.java from local machine to Hadoop VM using shared folder. 
	Please find the WordCount_Latin.java file along with the submission for reference.
	Move the file to /home/hadoop/ folder

3. 	Put the new_lemmatizer.csv file to the hadoop cluster using the following command:
	hdfs dfs -put <SharedFolder>/Activity3/new_lemmatizer.csv ~/newlemmatizer

4. Created the jar file using the commands given in the MR guide provided:
	hadoop com.sun.tools.javac.Main WordCount_Latin.java
	jar cf WordCount_Latin.jar WordCount_Latin*.class
	
5. Run the jar file for wordcount using the following command:
	hadoop jar WordCount_Latin.jar WordCount_Latin ~/inputActivity3/inputActivity3/ ~/outputActivity3
	
6. Get the output from hadoop to the local machine so that it can be used by the R code for tag cloud:
	hdfs dfs -get ~/outputActivity3 <SharedFolder>/Activity3/
	
7. Repeat this for multiple documents. I have created a bash script for running the whole procedure in a loop for different nunmber of files. 
	Please find the Activity3.sh along with the submission for reference

8.	I am providing the logs of the runs for different number of files.
	Also, I am providing the time variation for different number of files. Please find the same along with the submission with the following names:
	Time log: Activity3time.txt
	Log of the runs: Activity3Output.stderr
	
9. Using the time logs, I have plotted a graph using Excel for Number of files vS Execution time. Please find the same in the report in the root folder.
	Also, please find the Graphs.xlsx in the root folder
	
Note: Please find output for various number of files under the Folder OutputFiles/output3Activity[number of files]
	Also, find the out of the two latin files lucan.bellum_civile.part.1.tess and vergil.aeneid.tess under the folder outputActivity3
	inputActivity3 folder contains the two latin files lucan.bellum_civile.part.1.tess and vergil.aeneid.tess
	InputFiles contains all the 482 latin files which is used by the bash script for this activity as well as the Activity4
