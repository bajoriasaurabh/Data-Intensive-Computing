hadoop com.sun.tools.javac.Main /home/hadoop/WordCoBigram.java
jar cf WordCoBigram.jar WordCoBigram*.class
for (( fileCount=1; fileCount<=15; fileCount=fileCount+2 ))
do
	hdfs dfs -mkdir -p ~/InputFiles4
	mkdir /media/windows-share/Activity4/temp/																			#Change the Path as per shared folder
	echo $fileCount "Copying files"
	for file in $(ls /media/windows-share/Activity3/InputFiles/ | grep -v / | tail -$fileCount)							#Change the Path as per shared folder
	do
		cp /media/windows-share/Activity3/InputFiles/$file /media/windows-share/Activity4/temp/							#Change the Path as per shared folder
	done
	echo $fileCount "Putting files"
	hdfs dfs -put /media/windows-share/Activity4/temp/ ~/InputFiles4													#Change the Path as per shared folder
	echo $fileCount >> timeActivity4Bigram.txt
	{ time hadoop jar WordCoBigram.jar WordCoBigram ~/InputFiles4/temp ~/output4Activity"$fileCount" 2>> OutputActivity4Bigram.stderr ; } 2>> timeActivity4Bigram.txt
	echo $fileCount "Getting output files"
	hdfs dfs -get ~/output4Activity"$fileCount" /media/windows-share/Activity4/Bigram/Outputs/							#Change the Path as per shared folder
	echo $fileCount "Deleting files"
	rm -r /media/windows-share/Activity4/temp/
	hdfs dfs -rm -r ~/output4Activity"$fileCount"
	hdfs dfs -rm -r ~/InputFiles4
done