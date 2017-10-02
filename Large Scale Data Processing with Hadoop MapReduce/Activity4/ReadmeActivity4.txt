1. 	Put the latin files in the hadoop cluster using the following command:
	hdfs dfs -mkdir -p ~/inputActivity4												-- this will create a directory inputActivity3 at /home/hadoop/
	hdfs dfs -put <SharedFolder>/Activity4/Input/ ~/inputActivity4					-- put the folder Input/ in inputActivity3

2. 	I Wrote a java code for WordCoBigram and transferred the file WordCoBigram.java from local machine to Hadoop VM using shared folder. 
	Please find the WordCoBigram.java file under Bigram folder along with the submission for reference.
	Move the file to /home/hadoop/ folder

3. 	Put the new_lemmatizer.csv file to the hadoop cluster using the following command:
	hdfs dfs -put <SharedFolder>/Activity4/new_lemmatizer.csv ~/newlemmatizer

4. Created the jar file using the commands given in the MR guide provided: (under Bigram folder)
	hadoop com.sun.tools.javac.Main WordCoBigram.java
	jar cf WordCoBigram.jar WordCoBigram*.class
	
5. Run the jar file for wordcount using the following command:
	hadoop jar WordCoBigram.jar WordCoBigram ~/inputActivity4/Input ~/output4Activity
	
6. Get the output from hadoop to the local machine so that it can be used by the R code for tag cloud:
	hdfs dfs -get ~/output4Activity <SharedFolder>/Activity4/Bigram/
	
7. 	Repeat this for multiple documents. I have created a bash script for running the whole procedure in a loop for different nunmber of files. 
	Please find the Activity4.sh along with the submission for reference

8. 	I am not attaching the complete outputs obtained for running multiple documents, as the sizes of the output files are too huge.
	Please find a sample output (with 5000 lines) for verifying the format of the output for Bigram, under Bigram/output4Activity(Sample) folder
	and a sample output for verifying the format of the output for Trigram under Trigram/output4Activity(Sample) folder
	But I am providing the logs of the runs for different number of files.
	Also, I am providing the time variation for different number of files. Please find the same along with the submission with the following names:
	Time log: timeActivity4Bigram.txt
	Log of the runs: OutputActivity4Bigram.stderr
	
9. Using the time logs, I have plotted a graph using Excel for Number of files vS Execution time. Please find the same in the report in the root folder.


10. The above steps are repeated for trigram. Only difference is Trigram is used in place of Bigram everywhere.
	Use the file Trigram.java under the Trigram folder for the creation of jar (Trigram.jar)
	
Note: Bigram folder contains all the code details related to Featured Activity2.a and Trigram folder contains all the code details related to Featured Activity2.b
	  Input folder contains the latin files lucan.bellum_civile.part.1.tess and vergil.aeneid.tess