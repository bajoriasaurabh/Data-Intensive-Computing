1. After extracting tweets using R, run the jar file "PreprocessingTweets.jar" using the following command:
	java -jar PreprocessingTweets.jar
This will clean the tweets and extract only the words with '@' and '#' in it.

2. Put these preprocessed tweets to a input folder 'inputActivity1' in the hadoop cluster using the following command:
	hdfs dfs -mkdir -p ~/inputActivity1												-- this will create a directory inputActivity1 at /home/hadoop/
	hdfs dfs -put <SharedFolder>/Activity1/Tweets/ProcessedTweets/ ~/inputActivity1			-- put the folder ProcessedTweets/ in inputActivity1

3. Run the jar file for wordcount using the following command:
	hadoop jar /home/hadoop/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.6.4.jar wordcount ~/inputActivity1/ProcessedTweets/ ~/outputActivity1
	
4. Get the output from hadoop to the local machine so that it can be used by the R code for tag cloud:
	hdfs dfs -get ~/outputActivity1 <SharedFolder>/Activity1/

5. Continue with the Rcode to visualize the processed tweets using tag cloud

Note: Tweets folder contain two folders: RawTweets --> Raw tweets extracted using R code for 3 topics: Economy, Soccer and Cricket
										 ProcessedTweets --> ProcessedTweets cleaned using Java code
	  outputActivity1 contains the output obtained using WordCount jar