hadoop com.sun.tools.javac.Main /home/hadoop/WordCoTrigram.java
jar cf WordCoTrigram.jar WordCoTrigram*.class
for (( fileCount=1; fileCount<=1; fileCount=fileCount+2 ))
do
	hdfs dfs -mkdir -p ~/InputFiles4
	mkdir /media/windows-share/Activity4/temp/
	echo $fileCount "Copying files"
	for file in $(ls /media/windows-share/Activity3/InputFiles/ | grep -v / | tail -$fileCount)
	do
		cp /media/windows-share/Activity3/InputFiles/$file /media/windows-share/Activity4/temp/
	done
	echo $fileCount "Putting files"
	hdfs dfs -put /media/windows-share/Activity4/temp/ ~/InputFiles4
	echo $fileCount >> timeActivity4Trigram.txt
	{ time hadoop jar WordCoTrigram.jar WordCoTrigram ~/InputFiles4/temp ~/output4Activity"$fileCount" 2>> OutputActivity4Trigram.stderr ; } 2>> timeActivity4Trigram.txt
	echo $fileCount "Getting output files"
	echo $fileCount "Deleting files"
	rm -r /media/windows-share/Activity4/temp/
	hdfs dfs -rm -r ~/output4Activity"$fileCount"
	hdfs dfs -rm -r ~/InputFiles4
done