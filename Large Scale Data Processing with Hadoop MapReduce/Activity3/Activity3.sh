hadoop com.sun.tools.javac.Main /home/hadoop/WordCount_Latin.java
jar cf WordCount_Latin.jar WordCount_Latin*.class
for (( fileCount=1; fileCount<=10; fileCount=fileCount+2 ))
do
	hdfs dfs -mkdir -p ~/InputFiles3
	mkdir /media/windows-share/Activity3/temp/				#Change the Path as per shared folder
	echo $fileCount "Copying files"
	for file in $(ls /media/windows-share/Activity3/InputFiles/ | grep -v / | tail -$fileCount)			#Change the path as per shared folder
	do
		cp /media/windows-share/Activity3/InputFiles/$file /media/windows-share/Activity3/temp/			#Change the path as per shared folder
	done
	echo $fileCount "Putting files"
	hdfs dfs -put /media/windows-share/Activity3/temp/ ~/InputFiles3									#Change the path as per shared folder
	echo $fileCount >> Activity3time.txt
	{ time hadoop jar WordCount_Latin.jar WordCount_Latin ~/InputFiles3/temp ~/output3Activity"$fileCount" 2>> Activity3Output.stderr ; } 2>> Activity3time.txt
	echo $fileCount "Getting output files"
	hdfs dfs -get ~/output3Activity"$fileCount" /media/windows-share/Activity3/OutputFiles/				#Change the path as per shared folder
	echo $fileCount "Deleting files"
	rm -r /media/windows-share/Activity3/temp/
	hdfs dfs -rm -r ~/output3Activity"$fileCount"
	hdfs dfs -rm -r ~/InputFiles3
done

